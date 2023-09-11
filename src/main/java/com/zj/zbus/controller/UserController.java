package com.zj.zbus.controller;

import com.zj.common.exception.Result;
import com.zj.common.json.JsonUtil;
import com.zj.zbus.sdk.request.CheckUserExistRequest;
import com.zj.zbus.sdk.request.LoginRequest;
import com.zj.zbus.sdk.request.RegisterRequest;
import com.zj.zbus.sdk.response.LoginResponse;
import com.zj.zbus.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:01
 */
@Api(tags = "人员信息", value = "UserController")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/checkUser")
    @ApiOperation("校验用户信息是否存在")
    public Result<Boolean> checkUserExist(@RequestBody CheckUserExistRequest request) {
        boolean result = userService.checkUserExist(request);
        log.info("######checkUserExist: request={}, result={}", JsonUtil.toJSONString(result), result);
        return Result.ok(result);
    }

    @PostMapping("/register")
    @ApiOperation("注册用户信息")
    public Result<Boolean> register(@RequestBody RegisterRequest request) {
        boolean result = userService.register(request);
        log.info("######register: request={}, result={}", JsonUtil.toJSONString(result), result);
        return Result.ok(result);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        LoginResponse result = userService.login(loginRequest, request, response);
        log.info("######register: request={}, result={}", JsonUtil.toJSONString(loginRequest), JsonUtil.toJSONString(result));
        return Result.ok(result);
    }
}
