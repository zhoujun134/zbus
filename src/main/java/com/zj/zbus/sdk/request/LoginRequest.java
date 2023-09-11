package com.zj.zbus.sdk.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:50
 */
@Data
public class LoginRequest {

    @NotBlank(message = "用户名不能为空！")
    private String userName;

    @NotBlank(message = "密码不能为空！")
    private String password;

}
