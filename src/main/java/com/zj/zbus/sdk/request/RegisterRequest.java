package com.zj.zbus.sdk.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:34
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "用户名称不能为空!")
    private String name;

    @NotBlank(message = "用户名不能为空!")
    private String userName;

    @NotBlank(message = "邮箱不能为空!")
    private String email;

    @NotBlank(message = "密码不能为空!")
    private String password;
}
