package com.zyx.controller.user.account;

import com.zyx.service.user.account.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */

//获取用户的token
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/api/user/account/token")
    public Map<String, String> getToken(@RequestParam Map<String, String> map) {

        String username = map.get("username");
        String password = map.get("password");
        System.out.println(username + ":" + password);

        return loginService.getToken(username,password);
    }

}
