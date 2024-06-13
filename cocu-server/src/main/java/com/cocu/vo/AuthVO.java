package com.cocu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthVO {

    private Long id;

    private String email;

    private String newPwd;

    private String emailCode;

    private String imageCode;
}
