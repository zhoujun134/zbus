package com.zj.zbus.service;

import com.zj.zbus.sdk.request.CheckUserExistRequest;
import com.zj.zbus.sdk.request.LoginRequest;
import com.zj.zbus.sdk.request.RegisterRequest;
import com.zj.zbus.sdk.response.LoginResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:11
 */
public interface UserService {

    /**
     * 检查用户名和邮箱是否存在
     * @return true 用户信息已存在，false 用户信息不存在
     */
    boolean checkUserExist(CheckUserExistRequest request);

    boolean register(RegisterRequest request);

    LoginResponse login(LoginRequest loginRequest,
                        HttpServletRequest request,
                        HttpServletResponse response);

}
