package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.User;
import com.marvin.bean.vo.RegisterVo;
import com.marvin.bean.vo.UserQuery;
import com.marvin.common.Result;
import com.marvin.handler.AysException;
import com.marvin.service.UserService;
import com.marvin.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // 根据用户id查询相关信息
    @GetMapping("getUserById/{id}")
    public Result getUserById(@PathVariable String id){
        User user = userService.getById(id);
        return Result.ok().data("user",user);
    }

    // 验证手机号是否存在
    @GetMapping("checkNameIsExisted/{nickName}")
    public Result checkNameIsExisted(@PathVariable String nickName){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nick_name",nickName);
        int count = userService.count(wrapper);
        if(count > 0) {
            return Result.error();
        }
        return Result.ok();
    }

    // 验证手机号是否存在
    @GetMapping("checkMobileIsExisted/{mobile}")
    public Result checkMobileIsExisted(@PathVariable String mobile){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        int count = userService.count(wrapper);
        if(count > 0) {
            return Result.error();
        }
        return Result.ok();
    }

    // 根据token获取用户信息
    @GetMapping("getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        // 调用jwt封装的方法，根据request的header信息返回用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        User user = userService.getById(userId);
        return Result.ok().data("user",user);
    }


    // 登录
    @PostMapping("login")
    public Result login(@RequestBody User user){
        // 返回token值，用jwt生成
        String token = userService.login(user);
        return Result.ok().data("token",token);
    }

   // 注册
   @PostMapping("register")
   public Result register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        return Result.ok();
   }

    
    // 根据id删除用户信息
    @DeleteMapping("{id}")
    public Result removeUser(@PathVariable String id){
        boolean flag = userService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     *  分页查询用户
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @return
     */
    @GetMapping("pageUser/{current}/{limit}")
    public Result pageListUser(@PathVariable long current,
                                @PathVariable long limit){
        // 创建page分页对象(当前页，每页显示的记录数)
        Page<User> userPage = new Page<>(current,limit);
        // 调用方法实现分页，把分页所有封装到userPage对象里面
        userService.page(userPage,null);

        long total = userPage.getTotal(); // 总记录数
        List<User> records = userPage.getRecords(); // 数据list集合

        // 另一种返回数据的方式
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("records",records);
//        return Result.ok().data(map);

        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 条件查询用户信息带分页的方法
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @param userQuery 条件查询封装对象
     * @return
     */
    // 使用@RequestBody(required = false) 使用json传输数据，false：允许为空，要用post方法
    @PostMapping("pageUserCondition/{current}/{limit}")
    public Result pageUserCondition(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) UserQuery userQuery){
        Page<User> userPage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql
        String nickName = userQuery.getNickName();
        String mobile = userQuery.getMobile();
        String begin = userQuery.getBegin();
        String end = userQuery.getEnd();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(nickName)){
            // 参数：1.列,2.值
            wrapper.like("nick_name",nickName);
        }
        if(!StringUtils.isEmpty(mobile)){
            // 参数：1.列,2.值
            wrapper.like("mobile",mobile);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("created",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("created",end);
        }
        // 降序排序
        wrapper.orderByDesc("created");

        userService.page(userPage,wrapper);
        long total = userPage.getTotal(); // 总记录数
        List<User> records = userPage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    @PostMapping("addUser")
    public Result addUser(@RequestBody User user){
        boolean save = userService.save(user);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 根据用户id进行查询
    @GetMapping("getUser/{id}")
    public Result getUser(@PathVariable String id){
        User user = userService.getById(id);
        return Result.ok().data("user",user);
    }

    // 用户修改方法
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user){
        boolean flag = userService.updateById(user);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

