package com.zj.zbus.domain.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.zbus.domain.user.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author junzhou
 * @date 2022/9/18 21:33
 * @since 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfoPo extends BasePo {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;

}
