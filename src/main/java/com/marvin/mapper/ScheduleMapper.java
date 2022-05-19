package com.marvin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.marvin.bean.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.bean.vo.ReleaseIndexMovieVo;
import com.marvin.bean.vo.ScheduleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-02-19
 */
public interface ScheduleMapper extends BaseMapper<Schedule> {
    IPage<ScheduleVo> PageScheduleVo(IPage<ScheduleVo> page, @Param(Constants.WRAPPER) Wrapper<ScheduleVo> wrapper);

    List<ReleaseIndexMovieVo> releaseIndexMovie(@Param(Constants.WRAPPER) Wrapper<ReleaseIndexMovieVo> wrapper);

    List<ReleaseIndexMovieVo> releaseMovieList(@Param(Constants.WRAPPER) Wrapper<ReleaseIndexMovieVo> wrapper);
    List<ScheduleVo> getScheduleByMovieId(@Param(Constants.WRAPPER) Wrapper<ScheduleVo> wrapper);

}
