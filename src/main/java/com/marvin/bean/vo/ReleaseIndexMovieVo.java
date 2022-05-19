package com.marvin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-03-18 16:24
 */

@Data
public class ReleaseIndexMovieVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "评分")
    private Double rate;

    @ApiModelProperty(value = "影片标题")
    private String title;

    @ApiModelProperty(value = "影片封面图片地址")
    private String cover;

    @ApiModelProperty(value = "上映状态")
    private Integer scheduleStatus;

    @ApiModelProperty(value = "创建时间")
    private Date created;
}
