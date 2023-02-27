package com.zyx.service;

/**
 * @author 张宇森
 * @version 1.0
 */
public interface MatchingService {

    //添加玩家
    String addPlayer(Integer userId, Integer rating, Integer robotId);
    //删除玩家
    String deletePlayer(Integer userId);
}
