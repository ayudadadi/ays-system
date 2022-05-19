package com.marvin.bean.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-04-13 15:47
 */

@Data
public class FriendVo {

    private String id;
    private String content;
    private String firstUserId;
    private String secondUserId;
    private Integer matchingRate;


    private Date created;
    private Date updated;
    private Integer status;

    private String nickName;
    private String avatar;
    private String mobile;

    private String secondName;
    private String secondMobile;

    private Integer sex;
    private Integer age;
    private String hobby;

}
