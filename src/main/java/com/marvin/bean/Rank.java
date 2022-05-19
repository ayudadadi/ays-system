package com.marvin.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Rank榜单对象", description="豆瓣_电影top250")
public class Rank implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "影片id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "导演")
    private String directors;

    @ApiModelProperty(value = "评分")
    private Double rate;

    @ApiModelProperty(value = "影片标题")
    private String title;

    @ApiModelProperty(value = "影片详细信息地址")
    private String url;

    @ApiModelProperty(value = "影片封面图片地址")
    private String cover;

    @ApiModelProperty(value = "主演")
    private String casts;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "制片国家")
    private String country;


    // 指定json返回日期格式
    @JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
    @ApiModelProperty(value = "上映日期")
    private Date releaseDate;

    @ApiModelProperty(value = "片长")
    private String length;

    @ApiModelProperty(value = "评论人数")
    private Long reviews;

    @Version
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "逻辑删除 1 （已删除）0（未删除）")
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updated;


}
