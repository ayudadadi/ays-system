package com.marvin.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-03-20 14:42
 */
@Data
public class CommentVo {
    private String id;
    private String movieId;
    private String userId;
    private String content;
    private String nickName;
    private String avatar;
    private Date created;
}
