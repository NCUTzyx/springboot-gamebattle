package com.zyx.service.impl.battle;

import com.zyx.server.WebSocketServer;
import com.zyx.service.battle.StartGameService;
import org.springframework.stereotype.Service;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class StartGameServiceImpl implements StartGameService {

    @Override
    public String startGame(Integer aId, Integer aRobotId, Integer bId, Integer bRobotId) {
        System.out.println("start game: " + aId +" "+ aRobotId + " "+ bId + " " + bRobotId);
        WebSocketServer.startGame(aId,aRobotId,bId,bRobotId);
        return "Matching Success";
    }
}
