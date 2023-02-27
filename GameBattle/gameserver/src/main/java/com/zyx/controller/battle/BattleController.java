package com.zyx.controller.battle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张宇森
 * @version 1.0
 *  对战页面
 */

@Controller
public class BattleController {

    @RequestMapping("/")
    public String index(){
        return "battle/index";
    } 
}
