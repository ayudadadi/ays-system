package com.marvin.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.marvin.bean.MovieVideo;

/**
 * @author Marvin
 * @Description com.marvin.utils
 * @create 2022-02-17 15:42
 */
public class GetVodInfo {
    public static void getVodInfo(MovieVideo movieVideo) throws ClientException {
        // 创建客户端 获取视频信息
        DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
        GetVideoInfoRequest request1 = new GetVideoInfoRequest();
        request1.setVideoId(movieVideo.getVideoSourceId());
        GetVideoInfoResponse response1 = client.getAcsResponse(request1);

        // 获取时长 秒
        movieVideo.setDuration(response1.getVideo().getDuration());
//        System.out.println(movieVideo.getDuration());
        // 获取视频状态
        movieVideo.setStatus(response1.getVideo().getStatus());
        // 获取视频大小 字节
        movieVideo.setSize(response1.getVideo().getSize());
//        System.out.println(movieVideo.getSize());

    }
}
