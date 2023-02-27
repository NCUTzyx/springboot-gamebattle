package com.zyx.controller.user.account;

import com.zyx.service.user.account.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@RestController
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @PostMapping("/user/account/register")
    public Map<String,String> register(@RequestParam Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");

        return registerService.register(username,password,confirmPassword);
    }
}
