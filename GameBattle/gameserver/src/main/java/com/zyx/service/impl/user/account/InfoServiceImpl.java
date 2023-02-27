package com.zyx.service.impl.user.account;

import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import com.zyx.service.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */

@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public Map<String, String> getInfo() {

        //获取用户上下文信息
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        //不要传密码
        Map<String, String> map = new HashMap<>();
        map.put("message","success");
        map.put("id",user.getId().toString());
        map.put("username",user.getUsername());
        map.put("headshot", user.getHeadshot());

        return map;
    }
}
