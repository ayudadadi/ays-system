package com.marvin.bean.vo;

import lombok.Data;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-03-26 17:35
 */

@Data
public class ScheduleQuery {
    private String releaseDate;
    private String endTime;
    private String movieId;
    // 接收场次选择当日详细时间
    private String firstDate;
}
