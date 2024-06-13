package com.cocu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2024-06-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cocu_user_apply_activity")
@ApiModel(value="UserApplyActivity Object", description="")
public class UserApplyActivity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long activityId;

    @ApiModelProperty(value = "0:pending, 1:approved, 2:rejected")
    private String auditStatus;

    private String auditContent;


}
