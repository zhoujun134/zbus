package com.zj.zbus.utils;

import com.zj.common.exception.ResultCode;
import com.zj.common.exception.ValidateUtil;
import com.zj.zbus.domain.user.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
@Slf4j
public class UserUtils {

    @Resource
    private RedisCache redisCache;


    public LoginUserDTO getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        ValidateUtil.exceptionByTrue(Objects.isNull(authentication), ResultCode.ANONYMOUS_USER_EXCEPTION);
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUserDTO) {
            return (LoginUserDTO) principal;
        }
        return null;
    }

}
