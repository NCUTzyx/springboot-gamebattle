package com.zyx.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyx.mapper.UserMapper;
import com.zyx.pojo.User;
import com.zyx.service.user.account.RegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmPassword) {

        Map<String,String> map = new HashMap<>();
        if (username == null) {
            map.put("message","用户名不能为空");
            return map;
        }
        if (password == null || confirmPassword == null) {
            map.put("message","密码或确认密码不能为空");
            return map;
        }

        username = username.trim();
        if (username.length() == 0){
            map.put("message","用户名不能为空");
            return map;
        }

        if (username.length() > 100) {
            map.put("message","用户名不能太长");
            return map;
        }

        if (password.length() == 0 || confirmPassword.length() == 0){
            map.put("message","密码长度不能为空");
            return map;
        }

        if (password.length() > 100 || confirmPassword.length() > 100) {
            map.put("message","密码不能太长");
            return map;
        }

        if (!password.equals(confirmPassword)){
            map.put("message","两次输入的密码不一致");
            return map;
        }

        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username",username);
        List<User> userList = userMapper.selectList(userWrapper);
        if (!userList.isEmpty()){
            map.put("message","用户名已存在");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String headshot = "https://cdn.acwing.com/media/user/profile/photo/112333_lg_de7028c6e7.jpg";
        User user = User.builder().username(username).password(encodedPassword).headshot(headshot).rating(1500).build();
        userMapper.insert(user);

        map.put("message","success");
        return map;
    }
}
