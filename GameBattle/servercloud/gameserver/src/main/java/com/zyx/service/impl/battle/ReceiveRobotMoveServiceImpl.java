package com.zyx.service.impl.battle;

import com.zyx.server.WebSocketServer;
import com.zyx.server.utils.Game;
import com.zyx.service.battle.ReceiveRobotMoveService;
import org.springframework.stereotype.Service;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class ReceiveRobotMoveServiceImpl implements ReceiveRobotMoveService {

    @Override
    public String receiveRobotMove(Integer userId, Integer direction) {
        System.out.println("receive => " + userId + " " + direction);

        if (WebSocketServer.users.get(userId) != null) {
            Game game = WebSocketServer.users.get(userId).game;

            if (game != null) {
                if (game.getPlayerA().getId().equals(userId)) {
                    game.setNextStepA(direction);
                } else if (game.getPlayerB().getId().equals(userId)) {
                    game.setNextStepB(direction);
                }
            }
        }

        return "success";
    }
}
