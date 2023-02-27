package com.zyx.server;

import com.alibaba.fastjson2.JSONObject;
import com.zyx.mapper.ReviewMapper;
import com.zyx.mapper.RobotMapper;
import com.zyx.mapper.UserMapper;
import com.zyx.pojo.Robot;
import com.zyx.pojo.User;
import com.zyx.server.utils.Game;
import com.zyx.server.utils.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张宇森
 * @version 1.0
 */
@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public static final ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private User user;
    private Session session = null;

    // WebSocket 非单例模式
    public static UserMapper userMapper;
    public static ReviewMapper reviewMapper;
    public static RestTemplate restTemplate;
    private static RobotMapper robotMapper;

    public Game game = null;

    private final static String addPlayerUrl = "http://127.0.0.1:3002/player/add";
    private final static String deletePlayerUrl = "http://127.0.0.1:3002/player/delete";

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setReviewMapper(ReviewMapper reviewMapper) {
        WebSocketServer.reviewMapper = reviewMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        WebSocketServer.restTemplate = restTemplate;
    }

    @Autowired
    public void setRobotMapper(RobotMapper robotMapper) {
        WebSocketServer.robotMapper = robotMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("connected!");

        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if (this.user != null) {
            users.put(userId, this);
        } else {
            this.session.close();
        }

        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected!");
        if (this.user != null) {
            users.remove(this.user.getId());
        }
    }

    public static void startGame(Integer aId, Integer aRobotId, Integer bId, Integer bRobotId) {
        User a = userMapper.selectById(aId);
        Robot aRobot = robotMapper.selectById(aRobotId);

        User b = userMapper.selectById(bId);
        Robot bRobot = robotMapper.selectById(bRobotId);

        Game game = new Game(
                13,
                14,
                20,
                a.getId(),
                aRobot,
                b.getId(),
                bRobot
        );
        game.createMap();
        if (users.get(a.getId()) != null) {
            users.get(a.getId()).game = game;
        }
        if (users.get(b.getId()) != null) {
            users.get(b.getId()).game = game;
        }
        game.start();

        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getMap());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", b.getUsername());
        respA.put("opponent_headshot", b.getHeadshot());
        respA.put("game", respGame);
        if (users.get(a.getId()) != null){
            users.get(a.getId()).sendMessage(respA.toJSONString());
        }

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", a.getUsername());
        respB.put("opponent_headshot", a.getHeadshot());
        respB.put("game", respGame);
        if (users.get(b.getId()) != null) {
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }

    }

    private void startMatching(Integer robotId) {
        System.out.println("start-matching 处理");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        data.add("robot_id",robotId.toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);

    }

    private void stopMatching() {
        System.out.println("stop-matching 处理");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(deletePlayerUrl, data, String.class);

    }

    //移动
    private void move(int directives) {
        if (game.getPlayerA().getId().equals(user.getId())) {
            if (game.getPlayerA().getRobotId().equals(-1)){   //亲自上阵
                game.setNextStepA(directives);
            }
        } else if (game.getPlayerB().getId().equals(user.getId())) {
            if (game.getPlayerB().getRobotId().equals(-1)){
                game.setNextStepB(directives);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {  //当作路由
        // 从Client接收消息
        System.out.println("receive message!");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)) {
            startMatching(data.getInteger("robot_id"));
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)) {
            move(data.getInteger("directives"));
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
