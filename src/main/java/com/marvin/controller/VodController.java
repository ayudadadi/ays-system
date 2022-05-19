package com.marvin.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.marvin.bean.MovieVideo;
import com.marvin.common.Result;
import com.marvin.handler.AysException;
import com.marvin.service.VodService;
import com.marvin.utils.InitVodClient;
import com.marvin.utils.VodPropertiesUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Marvin
 * @Description com.marvin.controller
 * @create 2022-02-11 21:10
 */

@Api("电影视频上传")
@RestController
@RequestMapping("/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频到阿里云，同时获取信息
    @PostMapping("uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file){
        String videoId = vodService.uploadAlyVideo(file);
        return Result.ok().data("videoId",videoId);
    }

    // 根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable String id){
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return Result.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new AysException(20001,"删除视频失败");
        }

    }
}
