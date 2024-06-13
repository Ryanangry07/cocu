package com.cocu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cocu_menu")
@ApiModel(value="Menu对象", description="")
public class Menu {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String menuName;

    private String menuComponent;

    private String menuIcon;

    @ApiModelProperty(value = "menu path")
    private String menuClick;

    @ApiModelProperty(value = "0:system admin，1:club admin，2:club member，3:student")
    private String menuRight;

    private String menuCode;

    private String menuLevel;

    private String menuParentCode;


}
