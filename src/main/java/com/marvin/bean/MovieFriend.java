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
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MovieFriend对象", description="")
public class MovieFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "匹配id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "邀请内容")
    private String content;

    @ApiModelProperty(value = "匹配率")
    private Integer matchingRate;

    @ApiModelProperty(value = "匹配状态")
    private Integer status;

    @ApiModelProperty(value = "用户1的id")
    private String firstUserId;

    @ApiModelProperty(value = "用户2的id")
    private String secondUserId;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updated;


}
