package com.marvin.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Marvin
 * @Description com.marvin.utils
 * @create 2022-02-11 21:24
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.vod.file")
public class VodPropertiesUtils implements InitializingBean {

    private String keyId;

    private String keySecret;


    //定义公开静态常量

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
