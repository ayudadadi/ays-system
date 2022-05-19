package com.marvin.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.marvin.bean.MovieVideo;
import com.marvin.bean.vo.MovieQuery;
import com.marvin.bean.vo.MovieVideoQuery;
import com.marvin.common.Result;
import com.marvin.handler.AysException;
import com.marvin.service.MovieVideoService;
import com.marvin.service.VodService;
import com.marvin.utils.InitVodClient;
import com.marvin.utils.VodPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电影视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/movie-video")
@CrossOrigin
public class MovieVideoController {

    @Autowired
    private MovieVideoService movieVideoService;

    @Autowired
    private VodService vodService;

    // 获取电影的视频资源id
    @GetMapping("getVideoSourceId/{movieId}")
    public Result getVideoSourceId(@PathVariable String movieId){
        QueryWrapper<MovieVideo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(movieId)){
            wrapper.eq("movie_id",movieId);
        }else {
            return Result.error();
        }
        MovieVideo movieVideo = movieVideoService.getOne(wrapper);
        String videoSourceId = movieVideo.getVideoSourceId();
        return Result.ok().data("videoSourceId",videoSourceId);
    }


    // 获取视频播放地址
    @GetMapping("{videoSourceId}")
    public Result getPlayUrl(@PathVariable String videoSourceId) throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId(videoSourceId);
        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        String playURL = "";
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            playURL = playInfo.getPlayURL();
        }
        //Base信息
//        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        return Result.ok().data("playURL",playURL);
    }


    // 分页带条件查询视频列表
    @PostMapping("pageVideoCondition/{current}/{limit}")
    public Result pageVideoCondition(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) MovieVideoQuery movieVideoQuery){

        Result result = movieVideoService.pageVideoCondition(current, limit, movieVideoQuery);
        return result;
    }

    // 上传视频
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody MovieVideo movieVideo){
        boolean save = movieVideoService.save(movieVideo);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    // 删除视频表的记录，同时删除阿里云视频
    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id){
        Result result = movieVideoService.deleteVideo(id);
        return result;

    }

}

