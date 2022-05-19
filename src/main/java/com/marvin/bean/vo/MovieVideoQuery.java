package com.marvin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-02-17 15:27
 */

@Data
public class MovieVideoQuery {
    @ApiModelProperty(value = "影片名")
    private String title;

    @ApiModelProperty(value = "查询开始时间")
    // 这里是String类型，前端传过来的数据无需转型
    private String begin;

    @ApiModelProperty(value = "查询结束时间")
    private String end;
}
