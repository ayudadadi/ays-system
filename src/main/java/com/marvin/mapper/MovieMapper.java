package com.marvin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.marvin.bean.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.bean.vo.ScheduleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 豆瓣_电影_2021 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-16
 */
public interface MovieMapper extends BaseMapper<Movie> {
    List<IndexMovieVo> IndexMovieVo(@Param(Constants.WRAPPER) Wrapper<IndexMovieVo> wrapper);
}
