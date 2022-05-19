package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Hall;
import com.marvin.bean.Movie;
import com.marvin.bean.vo.HallQuery;
import com.marvin.common.Result;
import com.marvin.service.HallService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author marvin
 * @since 2022-02-19
 */
@RestController
@RequestMapping("/hall")
@CrossOrigin
public class HallController {

    @Autowired
    private HallService hallService;

    // 查询影厅信息所有记录
    @GetMapping("findAll")
    public Result findAll(){
        List<Hall> list = hallService.list(null);
        return Result.ok().data("items",list);
    }

    /**
     * 条件查询影厅信息带分页的方法
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @param hallQuery 条件查询封装对象
     * @return
     */
    // 使用@RequestBody(required = false) 使用json传输数据，false：允许为空，要用post方法
    @PostMapping("pageHallCondition/{current}/{limit}")
    public Result pageHallCondition(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) HallQuery hallQuery){
        Page<Hall> hallPage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Hall> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql
        String name = hallQuery.getName();
        String begin = hallQuery.getBegin();
        String end = hallQuery.getEnd();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
            // 参数：1.列,2.值
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("created",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("created",end);
        }

        // 降序排序
        wrapper.orderByDesc("created");

        hallService.page(hallPage,wrapper);
        long total = hallPage.getTotal(); // 总记录数
        List<Hall> records = hallPage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    // 根据id删除影厅信息
    @DeleteMapping("{id}")
    public Result removeHall(@PathVariable String id){
        boolean flag = hallService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     * 添加影厅信息
     * @param hall
     * @return
     */
    @PostMapping("addHall")
    public Result addHall(@RequestBody Hall hall){

        boolean save = hallService.save(hall);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    // 影厅修改方法
    @PostMapping("updateHall")
    public Result updateHall(@RequestBody Hall hall){

        boolean flag = hallService.updateById(hall);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 根据影厅id进行查询
    @GetMapping("getHall/{id}")
    public Result getHall(@PathVariable String id){
        Hall hall = hallService.getById(id);
        return Result.ok().data("hall",hall);
    }
}

