package com.zyx.controller;

import com.zyx.service.RobotRunningService;
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
public class RobotRunningController {

    @Resource
    private RobotRunningService robotRunningService;

    @PostMapping("/robot/add")
    public String addRobot(@RequestParam MultiValueMap<String, String> data){

        Integer userId = Integer.valueOf(Objects.requireNonNull(data.getFirst("user_id")));
        String robotCode = data.getFirst("robot_code");
        String input = data.getFirst("input");
        return robotRunningService.addRobot(userId,robotCode,input);
    }
}
