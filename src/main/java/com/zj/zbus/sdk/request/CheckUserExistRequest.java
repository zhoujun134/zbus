package com.zj.zbus.sdk.request;

import lombok.Data;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:26
 */
@Data
public class CheckUserExistRequest {

    private String userName;

    private String email;
}
