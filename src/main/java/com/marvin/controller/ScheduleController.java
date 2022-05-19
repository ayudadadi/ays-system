package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Schedule;
import com.marvin.bean.vo.*;
import com.marvin.common.Result;
import com.marvin.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-19
 */
@RestController
@RequestMapping("/schedule")
@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;




    // 根据场次id，修改场次剩余人数
    @PostMapping("updateScheduleRemain")
    public Result updateScheduleRemain(@RequestBody Schedule schedule){
        boolean flag = scheduleService.updateById(schedule);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    // 根据场次id查询场次信息
    @GetMapping("getScheduleInfoById/{scheduleId}")
    public Result getScheduleInfoById(@PathVariable String scheduleId){
        Schedule schedule = scheduleService.getScheduleInfoById(scheduleId);
        return Result.ok().data("schedule",schedule);
    }


    // 根据电影id查询场次列表
    @PostMapping("getScheduleByMovieId")
    public Result getScheduleByMovieId(@RequestBody(required = false) ScheduleQuery scheduleQuery){
        List<ScheduleVo> scheduleList = scheduleService.getScheduleByMovieId(scheduleQuery);
        return Result.ok().data("scheduleList",scheduleList);

    }

    // 购票页的上映电影列表
    @GetMapping("releaseMovieList")
    public Result releaseMovieList(){
        List<ReleaseIndexMovieVo> releaseMovieList = scheduleService.releaseMovieList();
        return Result.ok().data("releaseMovieList",releaseMovieList);
    }


    // 查询首页正在上映电影前8个
    @GetMapping("releaseIndexMovie")
    public Result releaseIndexMovie(){
        List<ReleaseIndexMovieVo> releaseIndexMovie = scheduleService.releaseIndexMovie();
        return Result.ok().data("releaseItems",releaseIndexMovie);
    }

    // 修改场次状态
    @PostMapping("updateStatus")
    public Result updateStatus(@RequestBody Schedule schedule){
        boolean flag = scheduleService.updateById(schedule);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    // 分页多表多条件查询场次表的相关信息
    @PostMapping("pageScheduleVoList/{current}/{limit}")
    public Result pageScheduleVoList(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) ScheduleVo scheduleVo){

        Result result = scheduleService.pageScheduleVoList(current, limit, scheduleVo);
        return result;
    }

    // 添加场次信息
    @PostMapping("addSchedule")
    public Result addSchedule(@RequestBody Schedule schedule){
        boolean save = scheduleService.save(schedule);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

