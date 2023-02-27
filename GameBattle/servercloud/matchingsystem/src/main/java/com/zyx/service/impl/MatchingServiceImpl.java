package com.zyx.service.impl;

import com.zyx.service.MatchingService;
import com.zyx.utils.MatchingPool;
import org.springframework.stereotype.Service;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class MatchingServiceImpl implements MatchingService {

    public final static MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer rating, Integer robotId) {
        System.out.println("add - " + userId + " " + "robotId" +rating);
        matchingPool.addPlayer(userId,rating,robotId);
        return "success";
    }

    @Override
    public String deletePlayer(Integer userId) {
        System.out.println("delete - " + userId);
        matchingPool.deletePlayer(userId);
        return "success";
    }
}
