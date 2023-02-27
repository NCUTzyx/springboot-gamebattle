package com.zyx.controller.user.robot;

import com.zyx.service.user.robot.UpdateRobotService;
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
public class UpdateRobotController {

    @Resource
    private UpdateRobotService updateRobotService;

    @PostMapping("/api/user/robot/update")
    public Map<String,String> updateRobot(@RequestParam Map<String,String> data){
        return updateRobotService.updateRobot(data);
    }
}
