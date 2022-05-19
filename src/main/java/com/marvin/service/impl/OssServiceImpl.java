package com.marvin.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.marvin.service.OssService;
import com.marvin.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Marvin
 * @Description com.marvin.service.impl
 * @create 2021-12-22 10:21
 */

@Service
public class OssServiceImpl implements OssService {


    //上传头像的方法：參考阿里云oss javaSDK 上傳文件流方式
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        InputStream inputStream = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            // 1、避免重复的文件名，生成唯一的UUID，用空字符串并替换uuid中的-
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid + fileName;

            // 2、把文件按日期分类
            // 2021/12/22.jpg
            // 使用日期工具类，获取当前日期
            String dataPath = new DateTime().toString("yyyy/MM/dd");

            //拼接:2021/12/22/uuid+dog.jpg
            fileName = dataPath+"/"+fileName;


            // 调用oss方法实现上传
            // 参数1 BucketName，
            // 参数2 上传到oss文件路径和文件名称（aa/1.jpg），
            // 参数3 上传文件流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 返回上传的文件路径，需要手动拼接oss路径
            // url示例：https://ays-pic.oss-cn-beijing.aliyuncs.com/man.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
