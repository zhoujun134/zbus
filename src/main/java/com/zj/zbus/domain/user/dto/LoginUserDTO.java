package com.zj.zbus.domain.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class LoginUserDTO implements UserDetails {

    private String name;

    private String password;

    private String userName;

    private String email;

    private List<String> permissions;

    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 在重写UserDetails接口时，有个默认实现的方法 public boolean isAccountNonLocked()，默认返回的是false，
     * 翻译成人话就是：是否不上锁，否，即上锁。异常代码如下：
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
