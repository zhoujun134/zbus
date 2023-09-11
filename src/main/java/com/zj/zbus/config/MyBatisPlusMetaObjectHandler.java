package com.zj.zbus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zj.common.exception.ResultCode;
import com.zj.common.exception.ValidateUtil;
import com.zj.zbus.domain.user.dto.LoginUserDTO;
import com.zj.zbus.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: zhoujun
 * @Date: 2023/2/19 18:48
 */
@Component
@Slf4j
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private UserUtils userUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill start ...");
        final LoginUserDTO currentUser = userUtils.getCurrentUser();
        final String username = Objects.isNull(currentUser) ? "Anonymous" : currentUser.getUsername();
        LocalDateTime currentTime = LocalDateTime.now();
        this.setFieldValByName("createTime", currentTime, metaObject);
        this.setFieldValByName("updateTime", currentTime, metaObject);
        this.setFieldValByName("createId", username, metaObject);
        this.setFieldValByName("updateId", username, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill start ...");
        final LoginUserDTO currentUser = userUtils.getCurrentUser();
        ValidateUtil.exceptionByTrue(Objects.isNull(currentUser) || StringUtils.isBlank(currentUser.getUsername()),
                ResultCode.DB_USER_NOT_LOGIN_NO_PERMIT);
        LocalDateTime currentTime = LocalDateTime.now();
        this.setFieldValByName("updateTime", currentTime, metaObject);
        this.setFieldValByName("updateId", currentUser.getUsername(), metaObject);
    }
}
