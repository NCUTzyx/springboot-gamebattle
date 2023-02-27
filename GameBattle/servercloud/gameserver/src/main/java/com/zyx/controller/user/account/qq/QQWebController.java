package com.zyx.controller.user.account.qq;

import com.alibaba.fastjson2.JSONObject;
import com.zyx.service.user.account.qq.QQWebService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@RestController
public class QQWebController {

    @Resource
    private QQWebService qqWebService;

    @GetMapping("/api/user/account/qq/apply_code")
    public JSONObject applyCode(){
        return qqWebService.applyCode();
    }

    @GetMapping("/api/user/account/qq/receive_code")
    public JSONObject receiveCode(@RequestParam Map<String,String> data) {

        String code = data.get("code");
        String state = data.get("state");
        return qqWebService.receiveCode(code,state);
    }
}
