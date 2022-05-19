package com.marvin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2021-12-20 9:10
 */

@Data
public class MovieQuery {
    @ApiModelProperty(value = "影片标题")
    private String title;

    @ApiModelProperty(value = "影片类型")
    private String type;

    @ApiModelProperty(value = "影片演员")
    private String casts;

    @ApiModelProperty(value = "自定义排序字段")
    private String orderRule;

    @ApiModelProperty(value = "影片国家")
    private String country;

    @ApiModelProperty(value = "影片规则")
    private String releaseDate;

    @ApiModelProperty(value = "查询开始时间")
    // 这里是String类型，前端传过来的数据无需转型
    private String begin;

    @ApiModelProperty(value = "查询结束时间")
    private String end;

}
