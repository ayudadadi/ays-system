package com.marvin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.marvin.bean.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marvin.bean.vo.FavoriteVo;
import com.marvin.bean.vo.ReleaseIndexMovieVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-10
 */
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<FavoriteVo> getStarListByUserId(@Param(Constants.WRAPPER) Wrapper<FavoriteVo> wrapper);
}
