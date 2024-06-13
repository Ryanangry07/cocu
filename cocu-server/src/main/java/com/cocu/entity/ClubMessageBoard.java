package com.cocu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cocu_club_message_board")
@ApiModel(value="ClubMessageBoard对象", description="")
public class ClubMessageBoard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private Long userId;

    /*private LocalDateTime createDate;

    private LocalDateTime updateDate;*/


}
