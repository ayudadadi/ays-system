package com.marvin.ayssystem;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.marvin.bean.Admin;
import com.marvin.bean.User;
import com.marvin.service.AdminService;
import com.marvin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Marvin
 * @Description com.marvin.ayssystem
 * @create 2021-12-21 15:57
 */
@SpringBootTest
public class TestUser {

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;



    @Test
    public void insertAdminBatch(){
        ArrayList<Admin> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Admin admin = new Admin();
            admin.setName("admin"+i);
            admin.setPassword("123456"+i);
            list.add(admin);
        }
        adminService.saveBatch(list);
    }

    @Test
    public void testUpload() throws FileNotFoundException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tCDtJWscz3yCHmAuSLp";
        String accessKeySecret = "LPNSZUYXxRFpxCvXxwoxeNea8CP2K9";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream("D:\\ays_system\\dog.jpg");
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("ays-pic", "example.jpg", inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
