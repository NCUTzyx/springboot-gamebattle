package com.zyx.service.utils;

import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author 张宇森
 * @version 1.0
 */
@Component
public class Consumer extends Thread{

    private Robot robot;
    private static RestTemplate restTemplate;
    private final static String  ReceiveRobotMoveUrl = "http://127.0.0.1:3001/battle/receiveMove";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        Consumer.restTemplate = restTemplate;
    }


    public void startTimeout(long timeout, Robot robot) {
        this.robot = robot;
        this.start();
        try {
            this.join(timeout); //最多等待timeout秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            this.interrupt(); //终端当前线程
        }
    }

    private String addUUID(String code, String uid) {  //code中的Robot类名 + uuid
        int k = code.indexOf(" implements java.util.function.Supplier<Integer>");
        return code.substring(0,k) + uid + code.substring(k);
    }

    @Override
    public void run() {

        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0,8);

        Supplier<Integer> robotInterface = Reflect.compile(
                "com.zyx.utils.Robot" + uid,
                addUUID(robot.getRobotCode(),uid)

        ).create().get();

        File file = new File("input.txt");
        try(PrintWriter input = new PrintWriter(file)) {
            input.println(robot.getInput());
            input.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Integer direction = robotInterface.get();
        System.out.println("move" + robot.getUserId() + " " + direction);

        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("user_id",robot.getUserId().toString());
        data.add("direction",direction.toString());
        restTemplate.postForObject(ReceiveRobotMoveUrl,data,String.class);
    }
}
