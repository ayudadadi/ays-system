package com.marvin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.marvin.bean.MovieFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marvin.bean.vo.FriendVo;
import com.marvin.bean.vo.ReleaseIndexMovieVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-13
 */
public interface MovieFriendMapper extends BaseMapper<MovieFriend> {
    IPage<FriendVo> pageMatchList(IPage<FriendVo> page, @Param(Constants.WRAPPER) Wrapper<FriendVo> wrapper);
    List<FriendVo> getMatchListById(@Param(Constants.WRAPPER) Wrapper<FriendVo> wrapper);
    IPage<FriendVo> getMatchListPage(IPage<FriendVo> page, @Param(Constants.WRAPPER) Wrapper<FriendVo> wrapper);
}
