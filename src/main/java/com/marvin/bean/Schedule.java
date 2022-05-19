package com.marvin.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2022-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Schedule对象", description="场次表")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场次编号")
    @TableId(value = "schedule_id", type = IdType.ASSIGN_ID)
    private String scheduleId;

    @ApiModelProperty(value = "所属放映厅编号")
    private String hallId;

    @ApiModelProperty(value = "所属电影编号")
    private String movieId;

    @ApiModelProperty(value = "电影放映时间")
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



}
