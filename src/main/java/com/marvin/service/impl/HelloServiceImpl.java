package com.marvin.service.impl;

import com.marvin.bean.Hello;
import com.marvin.mapper.HelloMapper;
import com.marvin.service.HelloService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-15
 */
@Service
public class HelloServiceImpl extends ServiceImpl<HelloMapper, Hello> implements HelloService {

}
