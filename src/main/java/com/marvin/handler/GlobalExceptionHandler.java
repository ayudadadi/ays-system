package com.marvin.handler;

import com.marvin.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Marvin
 * @Description com.marvin.handler
 * @create 2021-12-20 10:40
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理..");
    }

    // 特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理..");
    }

    // 自定义异常
    @ExceptionHandler(AysException.class)
    @ResponseBody //为了返回数据
    public Result error(AysException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMsg());
    }



}
