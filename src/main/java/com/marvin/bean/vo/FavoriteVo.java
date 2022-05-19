package com.marvin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-04-11 0:42
 */
@Data
public class FavoriteVo {
    private String userId; // 用户id
    private String movieId;
    private String title;
    private Double rate;
    @ApiModelProperty(value = "影片封面图片地址")
    private String cover;
    private String created;
}
