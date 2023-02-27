package com.zyx;

import com.zyx.service.impl.RobotRunningServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 张宇森
 * @version 1.0
 */
@SpringBootApplication
public class RobotRunningSystemApplication {

    public static void main(String[] args) {
        RobotRunningServiceImpl.robotPool.start();
        SpringApplication.run(RobotRunningSystemApplication.class,args);
    }
}
