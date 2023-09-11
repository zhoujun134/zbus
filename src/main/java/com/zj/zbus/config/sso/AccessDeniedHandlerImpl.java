package com.zj.zbus.config.sso;

import com.zj.common.exception.Result;
import com.zj.common.exception.ResultCode;
import com.zj.common.json.JsonUtil;
import com.zj.common.web.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        Result<Object> result = Result.fail(ResultCode.NO_OPERATOR_AUTH, e.getMessage());
        WebUtils.renderString(response, JsonUtil.toJSONString(result));
    }
}