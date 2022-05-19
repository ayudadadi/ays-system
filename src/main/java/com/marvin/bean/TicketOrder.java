package com.marvin.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TicketOrder对象", description="")
public class TicketOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "场次id")
    private String scheduleId;

    @ApiModelProperty(value = "电影票座位 （x排x座）")
    private String seat;

    @ApiModelProperty(value = "订单状态 0:未支付 1:已支付 2:退票中 3:退票成功")
    private Integer status;

    @ApiModelProperty(value = "订单金额")
    private Integer price;

    @ApiModelProperty(value = "逻辑删除 1 （已删除）0（未删除）")
    private Integer deleted;

    @ApiModelProperty(value = "订单支付时间")
    private Date payTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "订单创建时间")
    private Date created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "订单更新时间")
    private Date updated;


}
