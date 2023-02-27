package com.zyx.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 张宇森
 * @version 1.0
 */
@Component
public class MatchingPool extends Thread{

    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    private static RestTemplate restTemplate;

    private static final String StartGameUrl = "http://127.0.0.1:3001/battle/startGame";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }
    //匹配池里添加玩家
    public void addPlayer(Integer userId, Integer rating, Integer robotId) {
        lock.lock();
        try{
            players.add(new Player(userId, rating,robotId,0));

        }finally {
            lock.unlock();
        }
    }

    //匹配池里删除玩家
    public void deletePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player : players) {
                if (!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }
            }
            players = newPlayers;

        }finally {
            lock.unlock();
        }
    }

    //匹配等待时间+1
    private void increaseWaitingTime() {
        for (Player player: players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    //尝试匹配在匹配池里的玩家
    private void matchPlayers() {
        //玩家是否已经被匹配
        boolean[] users = new boolean[players.size()];
        for (int i = 0; i < players.size(); i ++){
            if (users[i]) continue;
            for (int j = i + 1; j < players.size(); j ++ ){
                if (users[j]) continue;
                Player a = players.get(i), b = players.get(j);
                if (checkMatched(a,b)){
                    users[i] = users[j] = true;
                    sendResult(a,b);
                    break;
                }
            }
        }
        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i ++ ) {
            if (!users[i]) {
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    //判断两名玩家是否匹配
    private boolean checkMatched(Player a, Player b) {
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingDelta <= waitingTime * 10;
    }

    //返回两名玩家的匹配结果
    private void sendResult(Player a, Player b) {
        System.out.println("sendResult " + a + " " + b);
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("a_robot_id",a.getRobotId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("b_robot_id",b.getRobotId().toString());
        restTemplate.postForObject(StartGameUrl,data,String.class);
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();

                }finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

    }
}
