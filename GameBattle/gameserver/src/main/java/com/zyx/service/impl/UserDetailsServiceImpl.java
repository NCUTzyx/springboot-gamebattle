package com.zyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyx.mapper.UserMapper;
import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 张宇森
 * @version 1.0
 */

@Service
//用来接入数据库信息
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);

        if (user == null){
            throw new RuntimeException("用户不存在");
        }

        return new UserDetailsImpl(user);

    }
}
