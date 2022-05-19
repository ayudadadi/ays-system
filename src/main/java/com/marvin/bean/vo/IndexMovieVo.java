package com.marvin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-03-18 15:32
 */
@Data
public class IndexMovieVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "评分")
    private Double rate;

    @ApiModelProperty(value = "影片标题")
    private String title;

    @ApiModelProperty(value = "影片封面图片地址")
    private String cover;

    @ApiModelProperty(value = "播放次数")
    private Long playCount;
}
