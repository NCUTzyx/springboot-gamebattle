package com.zyx.controller.user.account;

import com.zyx.service.user.account.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 * 获取信息：get
 * 修改删除添加：post 细化=> post（创建） put（修改） delete（删除）
 */

//获取用户信息
@RestController
public class InfoController {

    @Resource
    private InfoService infoService;

    @GetMapping("/user/account/info")
    public Map<String, String> getInfo(){
        return infoService.getInfo();
    }

}
