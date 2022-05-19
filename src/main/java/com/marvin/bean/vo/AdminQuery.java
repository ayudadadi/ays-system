package com.marvin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2021-12-21 15:15
 */

@Data
public class AdminQuery {
    @ApiModelProperty(value = "管理员名称")
    private String name;

    @ApiModelProperty(value = "查询开始时间")
    // 这里是String类型，前端传过来的数据无需转型
    private String begin;

    @ApiModelProperty(value = "查询结束时间")
    private String end;
}
