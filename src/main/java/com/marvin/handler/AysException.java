package com.marvin.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Marvin
 * @Description com.marvin.handler
 * @create 2021-12-20 10:51
 */

@Data
@AllArgsConstructor // 生成有参构造方法，写了这个有参注解，原来data带的无参就不见了，需要写多一个无参注解
@NoArgsConstructor // 生成无参构造方法
// 自定义异常类
public class AysException extends RuntimeException{
    private Integer code; // 状态码
    private String msg; // 异常信息
}
