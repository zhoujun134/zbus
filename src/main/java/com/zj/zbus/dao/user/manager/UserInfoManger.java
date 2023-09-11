package com.zj.zbus.dao.user.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zbus.domain.user.entity.UserInfoPo;

public interface UserInfoManger extends IService<UserInfoPo> {

    boolean isExistForUserNameOrEmail(String userName, String email);

    boolean isExistForUserName(String userName);

    boolean isExistForEmail(String email);

    UserInfoPo getByPasswordAndUserName(String userNameOrEmail, String password);
    UserInfoPo getByUserNameOrEmail(String userNameOrEmail);
}
