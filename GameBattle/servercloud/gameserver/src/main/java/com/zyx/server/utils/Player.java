package com.zyx.server.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张宇森
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private Integer id;
    private Integer robotId;  //-1 表示人工， 其他表示机器人
    private String robotCode;
    //起始坐标
    private Integer sx;
    private Integer sy;
    //蛇每一步方向
    private List<Integer> steps;

    public String getStepsToString() {
        StringBuffer res = new StringBuffer();
        for (int d: steps) {
            res.append(d);
        }
        return res.toString();
    }

    //当前回合蛇是否变长
    private boolean is_add_length(int step) {
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    //当前回合返回蛇的身体
    public List<SnakeBody> getSnakeBody() {
        List<SnakeBody> list = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        list.add(new SnakeBody(x,y));
        int step = 0; //回合数

        for (int d: steps) {
            x += dx[d];
            y += dy[d];
            list.add(new SnakeBody(x,y));
            if (!is_add_length(++step)) {
                list.remove(0);
            }
        }
        return list;
    }

}
