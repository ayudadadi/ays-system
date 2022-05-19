package com.marvin.service;

import com.marvin.bean.MovieVideo;
import com.marvin.common.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Marvin
 * @Description com.marvin.service
 * @create 2022-02-11 21:12
 */
public interface VodService {
    String uploadAlyVideo(MultipartFile file);
    Result deleteVideo(String id);
}
