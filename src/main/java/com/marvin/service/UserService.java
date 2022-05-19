package com.marvin.service;

import com.marvin.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.bean.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-21
 */
public interface UserService extends IService<User> {

    String login(User user);

    void register(RegisterVo registerVo);
}
