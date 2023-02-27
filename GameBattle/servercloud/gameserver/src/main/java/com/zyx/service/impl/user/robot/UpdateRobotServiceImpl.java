package com.zyx.service.impl.user.robot;

import com.zyx.mapper.RobotMapper;
import com.zyx.pojo.Robot;
import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import com.zyx.service.user.robot.UpdateRobotService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
@SuppressWarnings("all")
public class UpdateRobotServiceImpl implements UpdateRobotService {

    @Resource
    private RobotMapper robotMapper;

    @Override
    public Map<String, String> updateRobot(Map<String, String> data) {

        //获取用户上下文信息
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        int robot_id = Integer.parseInt(data.get("robot_id"));

        String name = data.get("name");
        String description = data.get("description");
        String content = data.get("content");

        Map<String,String> map = new HashMap<>();

        if (name == null || name.length() == 0){
            map.put("message","名字不能为空");
            return map;
        }

        if (name.length() > 100){
            map.put("message","名字不能太长");
            return map;
        }

        if (description == null || description.length() == 0){
            description = "这个用户很懒,什么都没有留下~";

        }

        if (description.length() > 300){
            map.put("message","机器人的描述不能太长");
            return map;
        }

        if (content == null || content.length() == 0){
            map.put("message","机器人的代码不能为空");
            return map;
        }
        if (content.length() > 10000){
            map.put("message","机器人的代码不能太长");
            return map;
        }

        Robot robot = robotMapper.selectById(robot_id);
        if (robot == null ){
            map.put("message","机器人不存在或已被删除");
        }

        if (!robot.getUserId().equals(user.getId())){
            map.put("message","你没有权限修改该机器人");
            return map;
        }

        Date now = new Date();
        Robot new_robot = new Robot(
                robot.getId(),user.getId(),name,description,content,robot.getCreatetime(),now);

        robotMapper.updateById(new_robot);

        map.put("message","success");

        return map;
    }
}
