package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.MovieFriend;
import com.marvin.bean.vo.FriendQuery;
import com.marvin.bean.vo.FriendVo;
import com.marvin.bean.vo.UserQuery;
import com.marvin.common.Result;
import com.marvin.mapper.MovieFriendMapper;
import com.marvin.service.MovieFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-13
 */
@Service
public class MovieFriendServiceImpl extends ServiceImpl<MovieFriendMapper, MovieFriend> implements MovieFriendService {

    @Override
    public Result getMatchPage(long current, long limit, UserQuery userQuery) {
        Page<FriendVo> friendVoPage = new Page<>(current,limit);
        QueryWrapper<FriendVo> wrapper = new QueryWrapper<>();

        wrapper.eq("f.status",0);

        wrapper.orderByDesc("f.created");

        baseMapper.pageMatchList(friendVoPage, wrapper);
        long total = friendVoPage.getTotal();// 总记录数
        List<FriendVo> records = friendVoPage.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    @Override
    public List<FriendVo> getMatchListById(String userId) {
        QueryWrapper<FriendVo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userId)){
            wrapper.eq("first_user_id",userId).or().eq("second_user_id",userId);
        }

        List<FriendVo> list = baseMapper.getMatchListById(wrapper);
        return list;
    }

    // 后台获取匹配信息列表
    @Override
    public Result getMatchListPage(long current, long limit, FriendQuery friendQuery) {
        Page<FriendVo> friendVoPage = new Page<>(current,limit);
        QueryWrapper<FriendVo> wrapper = new QueryWrapper<>();

        Integer status = friendQuery.getStatus();
        String nickName = friendQuery.getNickName();
        String secondName = friendQuery.getSecondName();
        if(!StringUtils.isEmpty(nickName)){
            wrapper.like("u.nick_name",nickName);
        }
        if(!StringUtils.isEmpty(secondName)){
            wrapper.like("r.nick_name",secondName);
        }
        wrapper.eq("f.status",status);
        wrapper.orderByDesc("f.created");

        baseMapper.getMatchListPage(friendVoPage, wrapper);
        long total = friendVoPage.getTotal();// 总记录数
        List<FriendVo> records = friendVoPage.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }
}
