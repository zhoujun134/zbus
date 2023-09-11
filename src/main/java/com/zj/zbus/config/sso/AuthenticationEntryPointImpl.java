package com.zj.zbus.config.sso;

import com.zj.common.exception.Result;
import com.zj.common.exception.ResultCode;
import com.zj.common.json.JsonUtil;
import com.zj.common.web.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zj
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        Result<Object> result = null;

        if (e instanceof InsufficientAuthenticationException) {
            log.warn("AuthenticationEntryPointImpl######commence: 匿名用户信息异常！InsufficientAuthenticationException: ", e);
            result = Result.fail(ResultCode.ANONYMOUS_USER_EXCEPTION);
        } else if (e instanceof BadCredentialsException) {
            log.warn("AuthenticationEntryPointImpl######commence: 匿名用户信息异常！BadCredentialsException: ", e);
            result = Result.fail(ResultCode.ANONYMOUS_USER_EXCEPTION.getCode(), e.getMessage());
        } else {
            log.warn("AuthenticationEntryPointImpl######commence: 认证失败！ ", e);
            result = Result.fail(ResultCode.SYSTEM_ERROR, "认证失败");
        }

        WebUtils.renderString(response, JsonUtil.toJSONString(result));
    }
}
