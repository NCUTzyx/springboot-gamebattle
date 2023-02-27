package com.zyx.controller.user.robot;

import com.zyx.service.user.robot.AddRobotService;
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
public class AddRobotController {

    @Resource
    private AddRobotService addRobotService;

    @PostMapping("/user/robot/add")
    public Map<String, String> addRobot(@RequestParam Map<String,String> data){
        return addRobotService.addRobot(data);
    }

}
