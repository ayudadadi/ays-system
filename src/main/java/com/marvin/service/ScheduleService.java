package com.marvin.service;

import com.marvin.bean.Schedule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.bean.vo.ReleaseIndexMovieVo;
import com.marvin.bean.vo.ScheduleQuery;
import com.marvin.bean.vo.ScheduleVo;
import com.marvin.common.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-19
 */
public interface ScheduleService extends IService<Schedule> {

    Result pageScheduleVoList(long current, long limit, ScheduleVo scheduleVo);

    List<ReleaseIndexMovieVo> releaseIndexMovie();

    List<ReleaseIndexMovieVo> releaseMovieList();

    List<ScheduleVo> getScheduleByMovieId(ScheduleQuery scheduleQuery);

    Schedule getScheduleInfoById(String scheduleId);
}
