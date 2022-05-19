package com.marvin.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-04-15 15:11
 */
@Data
public class ChartQuery {
    // 查询的字段
    private String queryCol;
    private String begin;
    private String end;

    @JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
    private Date scheduleStartTime;

    @JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
    private String time;

    private String title;
    private Integer price;
}
