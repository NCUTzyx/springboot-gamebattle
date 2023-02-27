package com.zyx.controller.user.robot;

import com.zyx.service.user.robot.DeleteRobotService;
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
public class DeleteRobotController {

    @Resource
    private DeleteRobotService deleteRobotService;

    @PostMapping("/api/user/robot/delete")
    public Map<String,String> deleteRobot(@RequestParam Map<String,String> data){
        return deleteRobotService.deleteRobot(data);

    }
}
