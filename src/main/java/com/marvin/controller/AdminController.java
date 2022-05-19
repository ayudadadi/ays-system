package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Admin;
import com.marvin.bean.vo.AdminQuery;
import com.marvin.common.Result;
import com.marvin.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 根据id删除管理员信息
    @DeleteMapping("{id}")
    public Result removeAdmin(@PathVariable String id){
        boolean flag = adminService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     *  分页查询管理员
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @return
     */
    @GetMapping("pageAdmin/{current}/{limit}")
    public Result pageListAdmin(@PathVariable long current,
                               @PathVariable long limit){
        // 创建page分页对象(当前页，每页显示的记录数)
        Page<Admin> adminPage = new Page<>(current,limit);
        // 调用方法实现分页，把分页所有封装到adminPage对象里面
        adminService.page(adminPage,null);

        long total = adminPage.getTotal(); // 总记录数
        List<Admin> records = adminPage.getRecords(); // 数据list集合

        // 另一种返回数据的方式
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("records",records);
//        return Result.ok().data(map);

        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 条件查询管理员信息带分页的方法
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @param adminQuery 条件查询封装对象
     * @return
     */
    // 使用@RequestBody(required = false) 使用json传输数据，false：允许为空，要用post方法
    @PostMapping("pageAdminCondition/{current}/{limit}")
    public Result pageAdminCondition(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestBody(required = false) AdminQuery adminQuery){
        Page<Admin> adminPage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql
        String name = adminQuery.getName();
        String begin = adminQuery.getBegin();
        String end = adminQuery.getEnd();

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

        adminService.page(adminPage,wrapper);
        long total = adminPage.getTotal(); // 总记录数
        List<Admin> records = adminPage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加管理员信息
     * @param admin
     * @return
     */
    @PostMapping("addAdmin")
    public Result addAdmin(@RequestBody Admin admin){
        boolean save = adminService.save(admin);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 根据管理员id进行查询
    @GetMapping("getAdmin/{id}")
    public Result getAdmin(@PathVariable String id){
        Admin admin = adminService.getById(id);
        return Result.ok().data("admin",admin);
    }

    // 管理员修改方法
    @PostMapping("updateAdmin")
    public Result updateAdmin(@RequestBody Admin admin){
        boolean flag = adminService.updateById(admin);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

