package com.zj.zbus.dao.user.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zbus.dao.user.manager.UserInfoManger;
import com.zj.zbus.dao.user.mapper.UserInfoMapper;
import com.zj.zbus.domain.user.entity.UserInfoPo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 18:46
 */
@Slf4j
@Service
public class UserInfoMangerImpl extends ServiceImpl<UserInfoMapper, UserInfoPo> implements UserInfoManger {

    @Override
    public boolean isExistForUserNameOrEmail(String userName, String email) {
        if (StringUtils.isAllBlank(userName, email)) {
            return false;
        }
        return lambdaQuery()
                .eq(StringUtils.isNotBlank(email), UserInfoPo::getEmail, email)
                .eq(StringUtils.isBlank(userName), UserInfoPo::getUserName, userName)
                .exists();
    }

    @Override
    public boolean isExistForUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }
        return lambdaQuery()
                .eq(UserInfoPo::getUserName, userName)
                .exists();
    }

    @Override
    public boolean isExistForEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return lambdaQuery()
                .eq(UserInfoPo::getEmail, email)
                .exists();
    }

    @Override
    public UserInfoPo getByPasswordAndUserName(String userNameOrEmail, String password) {
        if (StringUtils.isAnyBlank(userNameOrEmail, password)) {
            return null;
        }
        List<UserInfoPo> result = lambdaQuery()
                .eq(UserInfoPo::getPassword, password)
                .and(wrapper -> wrapper.eq(UserInfoPo::getUserName, userNameOrEmail)
                        .or()
                        .eq(UserInfoPo::getEmail, userNameOrEmail))
                .list();
        return Optional.ofNullable(result)
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserInfoPo getByUserNameOrEmail(String userNameOrEmail) {
        if (StringUtils.isBlank(userNameOrEmail)) {
            return null;
        }
        List<UserInfoPo> result = lambdaQuery()
                .eq(UserInfoPo::getUserName, userNameOrEmail)
                .or()
                .eq(UserInfoPo::getEmail, userNameOrEmail)
                .list();
        return Optional.ofNullable(result)
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .orElse(null);
    }
}
