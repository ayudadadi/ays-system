package com.marvin.utils;

import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Marvin
 * @Description com.marvin.utils
 * @create 2021-12-13 18:20
 */

public class PropertiesUtils {


    public String getProperties(String key) throws IOException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("movieInfo.properties");
        Properties pros = new Properties();
        pros.load(is);
        return pros.getProperty(key);
    }

}
