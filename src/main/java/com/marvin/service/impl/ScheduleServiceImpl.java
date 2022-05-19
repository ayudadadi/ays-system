package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Schedule;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.bean.vo.ReleaseIndexMovieVo;
import com.marvin.bean.vo.ScheduleQuery;
import com.marvin.bean.vo.ScheduleVo;
import com.marvin.common.Result;
import com.marvin.mapper.ScheduleMapper;
import com.marvin.service.ScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-19
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Override
    public Result pageScheduleVoList(long current, long limit, ScheduleVo scheduleVo) {
        // 创建分页对象
        Page<ScheduleVo> scheduleVoPage = new Page<>(current, limit);
        QueryWrapper<ScheduleVo> wrapper = new QueryWrapper<>();

        String movieTitle = scheduleVo.getMovieTitle();
        String hallName = scheduleVo.getHallName();
        Integer scheduleStatus = scheduleVo.getScheduleStatus();
        // Date转String
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 开始时间
        String created = "";
        if(null == scheduleVo.getCreated()){
            created = "";
        }else{
            created = simpleDateFormat.format(scheduleVo.getCreated());
        }
        // 结束时间
        String end = scheduleVo.getEnd();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(movieTitle)){
            // 此处wrapper行 要与sql语句的表名缩写一样
            wrapper.like("m.title",movieTitle);
        }
        if(!StringUtils.isEmpty(hallName)){
            wrapper.like("h.name",hallName);
        }
        if(!StringUtils.isEmpty(String.valueOf(scheduleStatus))){
            wrapper.like("s.schedule_status",scheduleStatus);
        }
        if(!StringUtils.isEmpty(created)){
            wrapper.ge("s.created",created);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("s.created",end);
        }

        // 降序排序
        wrapper.orderByDesc("s.created");

        // 返回带条件的ScheduleVoPage 分页
        IPage<ScheduleVo> page = baseMapper.PageScheduleVo(scheduleVoPage, wrapper);
        long total = page.getTotal();// 总记录数
        List<ScheduleVo> records = page.getRecords();// 数据list集合
        return Result.ok().data("total",total).data("rows",records);


    }


    // 查询首页正在上映电影前8个
    @Override
    public List<ReleaseIndexMovieVo> releaseIndexMovie() {
        QueryWrapper<ReleaseIndexMovieVo> wrapper = new QueryWrapper<>();
        wrapper.eq("s.schedule_status",1);
        wrapper.orderByDesc("s.created");
        wrapper.groupBy("m.id");
        wrapper.last("limit 8");
        List<ReleaseIndexMovieVo> list = baseMapper.releaseIndexMovie(wrapper);
        return list;
    }

    @Override
    public List<ReleaseIndexMovieVo> releaseMovieList() {
        QueryWrapper<ReleaseIndexMovieVo> wrapper = new QueryWrapper<>();
        wrapper.eq("s.schedule_status",1);
        wrapper.orderByDesc("s.created");
        wrapper.groupBy("m.id");
        List<ReleaseIndexMovieVo> list = baseMapper.releaseMovieList(wrapper);
        return list;
    }

    @Override
    public List<ScheduleVo> getScheduleByMovieId(ScheduleQuery scheduleQuery) {
        QueryWrapper<ScheduleVo> wrapper = new QueryWrapper<>();
        wrapper.eq("s.schedule_status",1);
        String movieId = scheduleQuery.getMovieId();
        String firstDate = scheduleQuery.getFirstDate();
        String endTime = scheduleQuery.getEndTime();
        String releaseDate = scheduleQuery.getReleaseDate();
        if (!StringUtils.isEmpty(movieId)){
           wrapper.eq("s.movie_id",movieId);
        }
        // 如果有传入当前详细时间，则只查当前时间分秒至当日24点，反之查当日0点至24点
        if (!StringUtils.isEmpty(firstDate)){
            // >=
            wrapper.ge("s.schedule_start_time",firstDate);
            // <
            wrapper.lt("s.schedule_start_time",endTime);
        }else{
            wrapper.ge("s.schedule_start_time",releaseDate);
            // <
            wrapper.lt("s.schedule_start_time",endTime);
        }
        // 升序排序
        wrapper.orderByAsc("s.schedule_start_time");
        List<ScheduleVo> list = baseMapper.getScheduleByMovieId(wrapper);
        return list;
    }

    @Override
    public Schedule getScheduleInfoById(String scheduleId) {
        Schedule schedule = baseMapper.selectById(scheduleId);
        return schedule;
    }
}
