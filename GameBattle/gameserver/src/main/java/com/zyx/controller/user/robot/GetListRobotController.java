package com.zyx.controller.user.robot;

import com.zyx.pojo.Robot;
import com.zyx.service.user.robot.GetListRobotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 张宇森
 * @version 1.0
 */
@SuppressWarnings("all")
@RestController
public class GetListRobotController {

    @Resource
    private GetListRobotService getListRobotService;

    @GetMapping("/user/robot/getlist")
    public List<Robot> getListRobot(){
        return getListRobotService.getListRobot();
    }

}
