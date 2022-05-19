package com.marvin.controller;

import com.marvin.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author Marvin
 * @Description com.marvin.controller
 * @create 2021-12-20 20:32
 */

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminLoginController {

    // 登录
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    // info
    @GetMapping("info")
    public Result info(){
        return Result.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://ays-pic.oss-cn-beijing.aliyuncs.com/test/man.jpg");
    }

}
