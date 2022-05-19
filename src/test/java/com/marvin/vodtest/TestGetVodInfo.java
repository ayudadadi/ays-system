package com.marvin.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.marvin.bean.MovieVideo;
import com.marvin.utils.InitVodClient;
import com.marvin.utils.VodPropertiesUtils;
import org.junit.jupiter.api.Test;

/**
 * @author Marvin
 * @Description com.marvin.vodtest
 * @create 2022-02-17 15:05
 */
public class TestGetVodInfo {
    @Test
    public void getInfo() throws ClientException {
        String accessKeyId = "LTAI5tCDtJWscz3yCHmAuSLp";
        String accessKeySecret = "LPNSZUYXxRFpxCvXxwoxeNea8CP2K9";
        // 创建客户端 获取视频信息
        DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);
        GetVideoInfoRequest request1 = new GetVideoInfoRequest();
        request1.setVideoId("c9411a8793f14ea58be54ff42ee376e0");
        GetVideoInfoResponse response1 = client.getAcsResponse(request1);

        MovieVideo movieVideo = new MovieVideo();
        movieVideo.setDuration(response1.getVideo().getDuration());
        System.out.println(movieVideo.getDuration());
        movieVideo.setStatus(response1.getVideo().getStatus());
        System.out.println(movieVideo.getStatus());
        movieVideo.setSize(response1.getVideo().getSize());
        System.out.println(movieVideo.getSize());
        movieVideo.setVideoSourceId("c9411a8793f14ea58be54ff42ee376e0");
    }
}
