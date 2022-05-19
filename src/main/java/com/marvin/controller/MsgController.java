package com.marvin.controller;

import com.marvin.common.Result;
import com.marvin.service.MsgService;
import com.marvin.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Marvin
 * @Description com.marvin.controller
 * @create 2022-03-07 11:21
 */

@RestController
@RequestMapping("msg")
@CrossOrigin
public class MsgController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public Result sendMsg(@PathVariable String phone){
        // 1、从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }

        // 2、如果从redis获取不到，进行腾讯云发送
        // 生成随机四位验证码，传递腾讯云进行发送
        code = RandomUtil.getFourBitRandom();
        boolean flag = msgService.send(phone,code);
        if(flag){
            // 发送成功，把验证码放到redis里面
            // 设置有效时间，五分钟；
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.error().message("短信发送失败");
        }



    }

}
