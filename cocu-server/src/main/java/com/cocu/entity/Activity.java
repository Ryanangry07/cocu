package com.cocu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

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
 * @author Ryanangry
 * @since 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cocu_activity")
@ApiModel(value="Activity", description="")
public class Activity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String avatar;

    private String title;

    private String summary;

    @ApiModelProperty(value = "0:pending, 1:approved, 2:rejected")
    private String auditStatus;

    private String auditContent;

    private String location;

    @ApiModelProperty(value = "max number of participants")
    private Integer capacity;

    @ApiModelProperty(value = "cur number of participants")
    private Integer participants;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Long organizerId;

    private Long clubId;

    private Integer categoryId;

    private Long contentId;

    private Integer views;

    private Integer likes;

    private Integer stars;

    private Integer comments;

    private Integer weight;

    private boolean isRecommend;


}
