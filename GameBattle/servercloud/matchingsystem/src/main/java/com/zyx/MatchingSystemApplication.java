package com.zyx;

import com.zyx.service.impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 张宇森
 * @version 1.0
 */

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchingPool.start(); //启动匹配线程
        SpringApplication.run(MatchingSystemApplication.class,args);
    }
}
