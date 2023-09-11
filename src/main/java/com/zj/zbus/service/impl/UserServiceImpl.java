package com.zj.zbus.service.impl;

import com.zj.common.exception.ResultCode;
import com.zj.common.exception.ValidateUtil;
import com.zj.common.json.JsonUtil;
import com.zj.zbus.converter.UserConverter;
import com.zj.zbus.dao.user.manager.UserInfoManger;
import com.zj.zbus.domain.constant.SystemConstants;
import com.zj.zbus.domain.user.dto.LoginUserDTO;
import com.zj.zbus.domain.user.entity.UserInfoPo;
import com.zj.zbus.sdk.request.CheckUserExistRequest;
import com.zj.zbus.sdk.request.LoginRequest;
import com.zj.zbus.sdk.request.RegisterRequest;
import com.zj.zbus.sdk.response.LoginResponse;
import com.zj.zbus.service.UserService;
import com.zj.zbus.utils.JwtUtil;
import com.zj.zbus.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:12
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoManger userInfoManger;

    @Resource
    private UserConverter userConverter;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;
    @Value("${login.user.cookie.token.cache.time.minutes}")
    private Integer COOKIE_CACHE_TIME_MINUTES;

    @Override
    public boolean checkUserExist(CheckUserExistRequest request) {
        if (Objects.isNull(request) || StringUtils.isAllBlank(request.getUserName(), request.getEmail())) {
            log.info("异常请求，request is null");
            return false;
        }
        return userInfoManger.isExistForUserNameOrEmail(request.getUserName(), request.getEmail());
    }

    @Override
    public boolean register(RegisterRequest request) {
        boolean existForUserName = userInfoManger.isExistForUserName(request.getUserName());
        ValidateUtil.exceptionByTrue(existForUserName, "用户名已存在，请修改后重试！");
        boolean existForEmail = userInfoManger.isExistForEmail(request.getEmail());
        ValidateUtil.exceptionByTrue(existForEmail, "邮箱已经注册，请使用邮箱对应的用户名登录！");
        UserInfoPo userInfoPo = userConverter.toPo(request);
        if (Objects.isNull(userInfoPo)) {
            return false;
        }
        return userInfoManger.save(userInfoPo);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserInfoPo userInfo = userInfoManger.getByPasswordAndUserName(loginRequest.getUserName(), loginRequest.getPassword());
        ValidateUtil.exceptionByTrue(Objects.isNull(userInfo), "用户信息不存在，请重试！");

        // 验证用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ValidateUtil.exceptionByTrue(Objects.isNull(authentication), ResultCode.LOGIN_ERROR);

        // 将用户信息存入 redis
        LoginUserDTO loginUser = (LoginUserDTO) authentication.getPrincipal();
        String userName = loginUser.getUsername();
        String token = JwtUtil.createJWT(userName);
        String loginUserString = JsonUtil.toJSONString(loginUser);
        redisCache.setCacheObject(SystemConstants.REDIS_USER_ID_PREFIX + token, loginUserString,
                COOKIE_CACHE_TIME_MINUTES, TimeUnit.MINUTES);
        if (Objects.nonNull(response)) {
            Cookie cookie = new Cookie(SystemConstants.REDIS_USER_LOGIN_TOKEN_PREFIX, token);
            cookie.setPath("/");
            response.addCookie(cookie);
            log.info("login: 添加 token");
        }
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(userInfo, loginResponse);
        loginResponse.setResult(true);
        return loginResponse;
    }
}
