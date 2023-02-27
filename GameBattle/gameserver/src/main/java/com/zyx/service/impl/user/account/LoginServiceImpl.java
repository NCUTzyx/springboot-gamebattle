package com.zyx.service.impl.user.account;

import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import com.zyx.service.user.account.LoginService;
import com.zyx.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {

        //封装用户名和密码 => 加密后的字符串
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);

        //验证登录 如果登陆失败，会自动处理
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //登陆成功
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();

        String jwt = JwtUtil.createJWT(user.getId().toString());

        Map<String, String> map = new HashMap<>();
        map.put("message","success");
        map.put("token",jwt);

        return map;
    }

}
