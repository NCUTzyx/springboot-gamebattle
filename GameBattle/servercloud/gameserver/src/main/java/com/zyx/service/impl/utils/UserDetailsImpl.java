package com.zyx.service.impl.utils;

import com.zyx.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author 张宇森
 * @version 1.0
 * 用户登录使用的工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //返回用户密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //返回用户名
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //是否没有被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //授权是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //用户是否被启用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
