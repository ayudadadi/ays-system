package com.marvin.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.marvin.bean.MovieVideo;
import com.marvin.common.Result;
import com.marvin.handler.AysException;
import com.marvin.service.VodService;
import com.marvin.utils.InitVodClient;
import com.marvin.utils.VodPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Marvin
 * @Description com.marvin.service.impl
 * @create 2022-02-11 21:12
 */

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadAlyVideo(MultipartFile file) {

        try {
            // 上传文件的原始名称
            String fileName = file.getOriginalFilename();
            // 上传之后显示名称（截取后缀名前面的名称 lastIndexOf从后面找.的下标 如：01.02.03.mp4）
            String title = fileName.substring(0,fileName.lastIndexOf("."));

            // 上传文件输入流
            InputStream inputStream = null;
            inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
           return null;
        }
    }

    public Result deleteVideo(String id){
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            GetVideoInfoResponse response = new GetVideoInfoResponse();
            return Result.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new AysException(20001,"删除视频失败");
        }
    }
}
