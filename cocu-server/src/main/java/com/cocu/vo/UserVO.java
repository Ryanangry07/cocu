package com.cocu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String username;

    private String mobile;

    private String avatar;

    private Integer gender;

    private Integer age;

    private Long roleId;
}
