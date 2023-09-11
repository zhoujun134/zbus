package com.zj.zbus.service.impl;

import com.zj.common.exception.AnonymousException;
import com.zj.common.json.JsonUtil;
import com.zj.zbus.dao.user.manager.UserInfoManger;
import com.zj.zbus.domain.user.dto.LoginUserDTO;
import com.zj.zbus.domain.user.entity.UserInfoPo;
import com.zj.zbus.service.ZjUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ZjUserDetailsServiceImpl implements ZjUserDetailsService {
    @Resource
    private UserInfoManger userInfoManger;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoPo userInfo = userInfoManger.getByUserNameOrEmail(username);
        if (Objects.isNull(userInfo)) {
            throw new AnonymousException();
        }
        String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
        List<String> authorityList = Collections.singletonList("admin");
        LoginUserDTO userDetail = new LoginUserDTO();
        BeanUtils.copyProperties(userInfo, userDetail);
        userDetail.setPassword(encodedPassword);
        userDetail.setPermissions(authorityList);
        log.info("ZjUserDetailsServiceImpl######loadUserByUsername: username={}, userDetail={}",
                username, JsonUtil.toJSONString(userDetail));
        return userDetail;
    }
}
