package com.zj.zbus.config.sso;

import com.zj.common.exception.Result;
import com.zj.common.json.JsonUtil;
import com.zj.common.web.WebUtils;
import com.zj.zbus.domain.constant.SystemConstants;
import com.zj.zbus.domain.user.dto.LoginUserDTO;
import com.zj.zbus.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        // 获取 token
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies) || cookies.length == 0) {
            log.info("JwtAuthenticationTokenFilter######doFilterInternal: cookie 为空！");
            filterChain.doFilter(request, response);
            return;
        }
        Cookie zjTokenCookie = Stream.of(cookies)
                .filter(cookie -> org.apache.commons.lang3.StringUtils.equals(cookie.getName(), SystemConstants.REDIS_USER_LOGIN_TOKEN_PREFIX))
                .findFirst()
                .orElse(null);
        String token = zjTokenCookie == null ? "": zjTokenCookie.getValue();
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenKey = SystemConstants.REDIS_USER_ID_PREFIX + token;
        if (!redisCache.existKey(tokenKey)) {
            log.warn("JwtAuthenticationTokenFilter######doFilterInternal: 用户未登录！tokenKey={}", tokenKey);
            Result<Object> result = Result.fail("无效 token，非法请求。");
            WebUtils.renderString(response, JsonUtil.toJSONString(result));
            return;
        }
        String cacheObject = redisCache.getCacheObject(tokenKey);
        LoginUserDTO loginUser = JsonUtil.parseObject(cacheObject, LoginUserDTO.class);
        if (loginUser == null) {
            Result<Object> result = Result.fail("用户信息不正确，请清除 cookie 后重新登陆");
            WebUtils.renderString(response, JsonUtil.toJSONString(result));
            return;
        }

        // 将封装的 authentication 放到 SecurityContext 中
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
