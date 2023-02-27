package com.zyx.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyx.mapper.UserMapper;
import com.zyx.pojo.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张宇森
 * @version 1.0
 */

@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    //查询用户列表
    @GetMapping("/user/list")
    public List<User> getUserList(){
        return userMapper.selectList(null);
    }
    //根据id查询用户
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",id);
        return userMapper.selectOne(userQueryWrapper);
    }
    //添加用户
    @GetMapping("/user/add/{id}/{username}/{password}")
    public String addUserById(
            @PathVariable("id") int id,
            @PathVariable("username") String username,
            @PathVariable("password") String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);

        User user = User.builder().id(id).username(username).password(encode).build();
        int row = userMapper.insert(user);
        return row == 1 ? "OK" : "Fail";
    }
    //根据id删除用户
    @GetMapping("/user/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id){
        int row = userMapper.deleteById(id);
        return row == 1 ? "OK" : "Fail";
    }
}
