package com.zj.zbus.converter;

import com.zj.zbus.domain.user.entity.UserInfoPo;
import com.zj.zbus.sdk.request.RegisterRequest;
import org.mapstruct.Mapper;

/**
 * @Author: zhoujun
 * @Date: 2023/9/10 19:40
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserInfoPo toPo(RegisterRequest request);
}
