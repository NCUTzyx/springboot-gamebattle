package com.zyx.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//模板
public class Robot implements java.util.function.Supplier<Integer>{


    static class SnakeBody {
        public int x, y;
        public SnakeBody(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //当前回合蛇是否变长
    private boolean is_add_length(int step) {
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    //当前回合返回蛇的身体
    public List<SnakeBody> getSnakeBodys(int sx, int sy, String steps) {
        steps = steps.substring(1,steps.length()-1);
        List<SnakeBody> list = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        list.add(new SnakeBody(x,y));
        int step = 0; //回合数

        for (int i = 0; i < steps.length(); i ++) {
            int d = steps.charAt(i) - '0';
            x += dx[d];
            y += dy[d];
            list.add(new SnakeBody(x,y));
            if (!is_add_length(++step)) {
                list.remove(0);
            }
        }
        return list;
    }

    public Integer nextMove(String input) {

        String[] strs = input.split("#");
        int[][] map = new int[13][14];
        for (int i = 0, k = 0; i < 13; i ++) {
            for (int j = 0; j < 14; j ++, k ++) {
                if (strs[0].charAt(k) == '1')
                map[i][j] = 1;
            }
        }

        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);


        List<SnakeBody> snakeA = getSnakeBodys(aSx,aSy,strs[3]);
        List<SnakeBody> snakeB = getSnakeBodys(bSx,bSy,strs[6]);

        for (SnakeBody a : snakeA) map[a.x][a.y] = 1;
        for (SnakeBody b : snakeB) map[b.x][b.y] = 1;

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

        for (int i = 0; i < 4; i ++) {
            int x = snakeA.get(snakeA.size() - 1).x + dx[i];
            int y = snakeA.get(snakeA.size() - 1).y + dy[i];
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && map[x][y] == 0){
                return i;
            }
        }

        return 0;
    }
    @Override
    public Integer get() {
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            return nextMove(sc.next());
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
