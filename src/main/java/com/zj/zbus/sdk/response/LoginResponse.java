package com.zj.zbus.sdk.response;

import lombok.Data;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:49
 */
@Data
public class LoginResponse {

    private Boolean result;
    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String email;
}
