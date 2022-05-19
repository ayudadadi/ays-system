package com.marvin.bean.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-04-10 21:35
 */

@Data
public class OrderVo {
    // 订单id
    private String id;

    private String userId;
    private String nickName;
    // 订单创建时间
    private Date created;
    private Date updated;

    private Date payTime;

    private String cover;

    // 电影名
    private String title;

    // 影厅名
    private String name;

    private String seat;

    // 放映时间
    private Date scheduleStartTime;

    private Integer price;

    private Integer status;



    private String begin;

    private String end;
}
