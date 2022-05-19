package com.marvin.service;

import com.marvin.bean.MovieFriend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.bean.vo.FriendQuery;
import com.marvin.bean.vo.FriendVo;
import com.marvin.bean.vo.UserQuery;
import com.marvin.common.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-13
 */
public interface MovieFriendService extends IService<MovieFriend> {

    Result getMatchPage(long current, long limit, UserQuery userQuery);

    List<FriendVo> getMatchListById(String userId);

    Result getMatchListPage(long current, long limit, FriendQuery friendQuery);
}
