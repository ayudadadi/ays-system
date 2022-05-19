package com.marvin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Marvin
 * @Description com.marvin.service
 * @create 2021-12-22 10:21
 */
public interface OssService {


    //上传头像的方法
    String uploadFileAvatar(MultipartFile file);
}
