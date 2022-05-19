package com.marvin.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Marvin
 * @Description com.marvin.utils
 * @create 2021-12-22 9:33
 */

// 当项目已启动，spring接口，spring加载之后，
// 读取配置文件的内容，执行接口的afterPropertiesSet方法
// 自动读取配置，这三个注解，一个都少不了
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ConstantPropertiesUtils implements InitializingBean {

    private String endPoint;

    private String keyId;

    private String keySecret;

    private String bucketName;

    //定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;

    }
}
