package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Rank;
import com.marvin.bean.vo.RankQuery;
import com.marvin.common.Result;
import com.marvin.service.RankService;
import com.marvin.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/rank")
@CrossOrigin
public class RankController {

    @Autowired
    private RankService rankService;

    // 分页查询榜单，按照评分降序排序
    @GetMapping("pageRankListByRate/{current}/{limit}")
    public Result pageRankListByRate(@PathVariable long current,
                               @PathVariable long limit){
        // 创建page分页对象(当前页，每页显示的记录数)
        Page<Rank> rankPage = new Page<>(current,limit);

        QueryWrapper<Rank> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("rate");
        // 调用方法实现分页，把分页所有封装到rankPage对象里面
        rankService.page(rankPage,wrapper);

        long total = rankPage.getTotal(); // 总记录数
        List<Rank> records = rankPage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    // 首页前十榜单
    @GetMapping("indexRankList")
    public Result indexRankList(){
        List<Rank> indexRankList = rankService.indexRankList();
        return Result.ok().data("rankItems",indexRankList);
    }

    // 查询榜单信息所有记录
    @GetMapping("findAll")
    public Result findAll(){
        //手动抛出异常
//        try {
//            int num = 1/0;
//        }catch (Exception e){
//            执行自定义异常
//            throw new AysException(20001,"执行了自定义异常...");
//        }

        List<Rank> list = rankService.list(null);
        return Result.ok().data("items",list);
    }

    // 根据id删除榜单信息
    @DeleteMapping("{id}")
    public Result removeRank(@PathVariable String id){
        boolean flag = rankService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     *  分页查询榜单
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @return
     */
    @GetMapping("pageRank/{current}/{limit}")
    public Result pageListRank(@PathVariable long current,
                                @PathVariable long limit){
        // 创建page分页对象(当前页，每页显示的记录数)
        Page<Rank> rankPage = new Page<>(current,limit);
        // 调用方法实现分页，把分页所有封装到rankPage对象里面
        rankService.page(rankPage,null);

        long total = rankPage.getTotal(); // 总记录数
        List<Rank> records = rankPage.getRecords(); // 数据list集合

        // 另一种返回数据的方式
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("records",records);
//        return Result.ok().data(map);

        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 条件查询榜单信息带分页的方法
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @param rankQuery 条件查询封装对象
     * @return
     */
    // 使用@RequestBody(required = false) 使用json传输数据，false：允许为空，要用post方法
    @PostMapping("pageRankCondition/{current}/{limit}")
    public Result pageRankCondition(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) RankQuery rankQuery){
        Page<Rank> rankPage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Rank> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql
        String title = rankQuery.getTitle();
        String type = rankQuery.getType();
        String casts = rankQuery.getCasts();
        String begin = rankQuery.getBegin();
        String end = rankQuery.getEnd();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)){
            // 参数：1.列,2.值
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(type)){
            wrapper.like("type",type);
        }
        if(!StringUtils.isEmpty(casts)){
            wrapper.like("casts",casts);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("created",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("created",end);
        }

        rankService.page(rankPage,wrapper);
        long total = rankPage.getTotal(); // 总记录数
        List<Rank> records = rankPage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加榜单信息
     * @param rank
     * @return
     */
    @PostMapping("addRank")
    public Result addRank(@RequestBody Rank rank){
        boolean save = rankService.save(rank);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 根据榜单id进行查询
    @GetMapping("getRank/{id}")
    public Result getRank(@PathVariable String id){
        Rank rank = rankService.getById(id);
        return Result.ok().data("rank",rank);
    }

    // 榜单修改方法
    @PostMapping("updateRank")
    public Result updateRank(@RequestBody Rank rank){
        boolean flag = rankService.updateById(rank);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

