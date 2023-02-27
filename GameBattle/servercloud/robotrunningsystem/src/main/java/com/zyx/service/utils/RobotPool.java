package com.zyx.service.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 张宇森
 * @version 1.0
 */
public class RobotPool extends Thread{

    private final ReentrantLock lock = new ReentrantLock();
    //条件变量
    private final Condition condition = lock.newCondition();
    private final Queue<Robot> robots = new LinkedList<>();

     public void insertRobot(Integer userId, String robotCode, String input) {
          lock.lock();
          try{
              //生产者模式
              robots.add(new Robot(userId,robotCode,input));
              condition.signalAll();
          }finally {
              lock.unlock();
          }
     }

    //消费者模式
    private void consume(Robot robot) {

         Consumer consumer = new Consumer();
         consumer.startTimeout(2000,robot);
    }

    @Override
    public void run() {

        while (true){
            lock.lock();
            if (robots.isEmpty()){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            }else {
                Robot robot = robots.remove();
                lock.unlock();
                consume(robot);
            }
        }
    }
}
