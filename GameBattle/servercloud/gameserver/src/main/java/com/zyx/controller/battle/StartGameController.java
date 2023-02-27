package com.zyx.controller.battle;

import com.zyx.service.battle.StartGameService;
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
public class StartGameController {

    @Resource
    private StartGameService startGameService;

    @PostMapping("/battle/startGame")
    public String startGame(@RequestParam MultiValueMap<String,String> data) {

        int aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        int aRobotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_robot_id")));
        int bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        int bRobotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_robot_id")));
        return startGameService.startGame(aId,aRobotId,bId,bRobotId);
    }
}
