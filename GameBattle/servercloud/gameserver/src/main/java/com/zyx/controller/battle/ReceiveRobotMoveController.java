package com.zyx.controller.battle;

import com.zyx.service.battle.ReceiveRobotMoveService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 张宇森
 * @version 1.0
 */
@RestController
public class ReceiveRobotMoveController {

    @Resource
    private ReceiveRobotMoveService receiveRobotMoveService;

    @PostMapping("/battle/receiveMove")
    public String receiveMove(@RequestParam MultiValueMap<String,String> data){

        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        Integer direction = Integer.parseInt(Objects.requireNonNull(data.getFirst("direction")));
        return receiveRobotMoveService.receiveRobotMove(userId,direction);
    }


}
