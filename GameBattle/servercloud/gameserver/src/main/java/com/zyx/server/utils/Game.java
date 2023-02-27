package com.zyx.server.utils;

import com.alibaba.fastjson2.JSONObject;
import com.zyx.pojo.Review;
import com.zyx.pojo.Robot;
import com.zyx.pojo.User;
import com.zyx.server.WebSocketServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 张宇森
 * @version 1.0
 * 存储游戏流程
 */
public class Game extends Thread {
    private final Integer rows;
    private final Integer cols;
    //地图刷新的随机障碍物数量
    private final Integer ranWalls_count;
    private final int[][] map;
    private final static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};

    private final Player playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;

    private ReentrantLock lock = new ReentrantLock();
    private String status = "playing";   //playing -> finished
    private String loser = ""; //AB A B;
    private final static String addRobotUrl = "http://127.0.0.1:3003/robot/add";

    public Game(Integer rows, Integer cols, Integer ranWalls_count, Integer idA, Robot aRobot, Integer idB, Robot bRobot) {
        this.rows = rows;
        this.cols = cols;
        this.ranWalls_count = ranWalls_count;
        this.map = new int[rows][cols];

        Integer aRobotId = -1, bRobotId = -1;
        String aRobotCode = "", bRobotCode = "";
        if (aRobot != null) {
            aRobotId = aRobot.getId();
            aRobotCode = aRobot.getContent();
        }

        if (bRobot != null) {
            bRobotId = bRobot.getId();
            bRobotCode = bRobot.getContent();
        }
        playerA = new Player(idA,aRobotId,aRobotCode,this.rows-2,1,new ArrayList<>());
        playerB = new Player(idB,bRobotId,bRobotCode,1,this.cols - 2, new ArrayList<>());
    }

    //返回地图
    public int[][] getMap() {
        return map;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public void setNextStepA(Integer nextStepA) {
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        } finally {
            lock.unlock();
        }

    }
    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        }finally {
            lock.unlock();
        }
    }

    private boolean is_connective(int sx, int sy, int tx, int ty) {
         if (sx == tx && sy == ty) return true;
           map[sx][sy] = 1;

           for (int i = 0; i < 4; i ++){
               int x = sx + dx[i], y = sy + dy[i];
               if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && map[x][y] == 0){
                   if (is_connective(x,y,tx,ty)){
                       map[sx][sy] = 0;
                       return true;
                   }
               }
           }
           map[sx][sy] = 0;
           return false;
    }

    //画地图
    private boolean draw() {
        for (int i = 0; i < this.rows; i ++) {
            for (int j = 0; j < this.cols; j ++) {
                map[i][j] = 0;
            }
        }
        //给地图增加墙障碍物
        for (int r = 0; r < this.rows; r ++) {
            map[r][0] = map[r][this.cols-1] = 1;
        }

        for (int c = 0; c < this.cols; c ++) {
            map[0][c] = map[this.rows-1][c] = 1;
        }

        Random random = new Random();
        for (int i = 0; i < this.ranWalls_count / 2; i ++){
            for (int j = 0; j < 1000; j ++) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (map[r][c] == 1 || map[this.rows-1-r][this.cols-1-c] == 1)
                    continue;
                if ( r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2 )
                    continue;
                map[r][c] = map[this.rows-1-r][this.cols-1-c] = 1;
                break;
            }
        }
        return is_connective(this.rows-2,1,1,this.cols-2);
    }

    public String getMapToString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < rows; i ++){
            for (int j = 0; j < cols; j ++){
                res.append(map[i][j]);
            }
        }
        return res.toString();
    }

    public void createMap() {

        for (int i = 0; i < 1000; i ++) {
            if (draw()) break;
        }
    }

    //将当前局面信息编码为字符串
    private String getInput(Player player) {
        // 字符串 => 地图 # me.sx # me.sy # me操作 # ta.sx # ta.sy # you操作
        Player me = null, you = null;
        if (playerA.getId().equals(player.getId())){
            me = playerA;
            you = playerB;
        } else {
            me = playerB;
            you = playerA;
        }

        return getMapToString() + "#" +
                me.getSx() + "#" +
                me.getSy() + "#(" +
                me.getStepsToString() + ")#" +
                you.getSx() + "#" +
                you.getSy() + "#(" +
                you.getStepsToString() + ")" ;
    }

    //是否向botSystem 传代码
    private void sendBotCode(Player player) {
        if (player.getRobotId().equals(-1)) return;  //人出场不需要传代码
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id",player.getId().toString());
        data.add("robot_code",player.getRobotCode());
        data.add("input",getInput(player));
        WebSocketServer.restTemplate.postForObject(addRobotUrl,data,String.class);

    }

    //等待两名玩家的下一步操作
    private boolean nextStep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sendBotCode(playerA);
        sendBotCode(playerB);

        for (int i = 0; i < 50; i ++) {
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if (nextStepA != null && nextStepB != null) {
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    //判断蛇身体是否合法
    private boolean check_valid(List<SnakeBody> snakeA, List<SnakeBody> snakeB) {

        int n = snakeA.size();
        SnakeBody snakeBody = snakeA.get(n - 1);
        if (map[snakeBody.x][snakeBody.y] == 1) return false;

        for (int i = 0; i < n-1; i ++) {
            if (snakeA.get(i).x == snakeBody.x && snakeA.get(i).y == snakeBody.y)
                return false;
        }

        for (int i = 0; i < n-1; i ++) {
            if (snakeB.get(i).x == snakeBody.x && snakeB.get(i).y == snakeBody.y)
                return false;
        }

        return true;
    }


    //判断两名玩家下一步操作是否合法
    private void judge() {

        List<SnakeBody> snakeAs = playerA.getSnakeBody();
        List<SnakeBody> snakeBs = playerB.getSnakeBody();

        boolean checkA = check_valid(snakeAs, snakeBs);
        boolean checkB = check_valid(snakeBs, snakeAs);

        if (!checkA || !checkB) {
            status = "finished";

            if (!checkA && !checkB) {
                loser = "AB";
            }else if (!checkA) {
                loser = "A";
            }else {
                loser = "B";
            }
        }
    }

    //向每一个人广播信息
    private void sendAllMessage(String message) {
        if (WebSocketServer.users.get(playerA.getId()) != null) {
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        }

        if (WebSocketServer.users.get(playerB.getId()) != null) {
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);
        }

    }

    //向Client传递移动信息
    private void sendMove() {
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event","move");
            resp.put("a_move",nextStepA);
            resp.put("b_move",nextStepB);
            sendAllMessage(resp.toJSONString());
            //清空
            nextStepA = nextStepB = null;

        }finally {
            lock.unlock();
        }
    }

    //将分数存入数据库
    private void updateUserRating(Player player,Integer rating) {

        User user = WebSocketServer.userMapper.selectById(player.getId());
        user.setRating(rating);
        WebSocketServer.userMapper.updateById(user);
    }


    //将数据参入数据库
    public void saveData() {

        //更新天梯积分
        Integer ratingA = WebSocketServer.userMapper.selectById(playerA.getId()).getRating();
        Integer ratingB = WebSocketServer.userMapper.selectById(playerB.getId()).getRating();

        if ("A".equals(loser)){
            ratingA -= 3;
            ratingB += 6;
        }else if ("B".equals(loser)){
            ratingA += 6;
            ratingB -= 3;
        }

        updateUserRating(playerA,ratingA);
        updateUserRating(playerB,ratingB);

        Review review = new Review(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepsToString(),
                playerB.getStepsToString(),
                getMapToString(),
                loser,
                new Date()
        );

        WebSocketServer.reviewMapper.insert(review);
    }

    //向两个Client 公布结果
    private void sendResult() {
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser",loser);
        saveData();
        sendAllMessage(resp.toJSONString());
    }

    @Override
    public void run() {

        for (int i = 0; i < 1000; i ++){
            //是否获取两名玩家的下一步操作
            if (nextStep()) {
                judge();
                if (status.equals("playing")) {
                    sendMove();
                } else {
                    sendResult();
                    break;
                }

            }else {
                status = "finished";
                lock.lock();

                try {
                    if (nextStepA == null && nextStepB == null){
                        loser = "AB";
                    } else if (nextStepA == null){
                        loser = "A";
                    }else {
                        loser = "B";
                    }

                }finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }
}
