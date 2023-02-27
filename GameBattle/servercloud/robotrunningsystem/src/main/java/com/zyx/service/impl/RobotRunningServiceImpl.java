package com.zyx.service.impl;

import com.zyx.service.RobotRunningService;
import com.zyx.service.utils.RobotPool;
import org.springframework.stereotype.Service;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class RobotRunningServiceImpl implements RobotRunningService {

    public final static RobotPool robotPool = new RobotPool();

    @Override
    public String addRobot(Integer userId, String robotCode, String input) {
        System.out.println(userId + " " + robotCode + " " + input);
        robotPool.insertRobot(userId,robotCode,input);
        return "success";
    }
}
