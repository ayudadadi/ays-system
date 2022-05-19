package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marvin.bean.User;
import com.marvin.bean.vo.RegisterVo;
import com.marvin.handler.AysException;
import com.marvin.mapper.UserMapper;
import com.marvin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marvin.utils.JwtUtils;
import com.marvin.utils.MD5;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 登录方法
    @Override
    public String login(User user) {
        String mobile = user.getMobile();
        String password = user.getPassword();

        // 非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new AysException(20001,"登录失败");
        }

        // 判断手机号是否正确
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        User user1 = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(user1 == null){
            throw new AysException(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(user1.getPassword())){
            throw new AysException(20001,"登录失败");
        }

        if(user1.getDisabled() != 0){
            throw new AysException(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(user1.getId(), user1.getNickName());
        return jwtToken;
    }

    // 注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickName = registerVo.getNickName(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName)) {
            throw new AysException(20001,"注册失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new AysException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new AysException(20001,"注册失败");
        }

        //数据添加数据库中
        User user = new User();
        user.setMobile(mobile);
        user.setNickName(nickName);
        user.setPassword(MD5.encrypt(password));//密码需要加密的
        user.setAvatar("https://ays-pic.oss-cn-beijing.aliyuncs.com/user.png"); // 设置默认头像
        user.setDisabled(0);//用户不禁用
        baseMapper.insert(user);
    }
}
