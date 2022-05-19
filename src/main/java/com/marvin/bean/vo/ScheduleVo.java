package com.marvin.bean.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Marvin
 * @Description com.marvin.bean.vo
 * @create 2022-02-21 1:19
 */

@Data
public class ScheduleVo {
    private String scheduleId;

    private String movieTitle;

    private String hallName;

    private String movieId;

    // 忽略 json 中值为null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date scheduleStartTime;

    @ApiModelProperty(value = "场次价格")
    private Integer schedulePrice;

    @ApiModelProperty(value = "剩余座位数 默认=hall_capacity")
    private Integer scheduleRemain;

    @ApiModelProperty(value = "场次状态 默认1 1：上映中 0：下架")
    private Integer scheduleStatus;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date created;

    @ApiModelProperty(value = "结束时间")
    private String end;
}
