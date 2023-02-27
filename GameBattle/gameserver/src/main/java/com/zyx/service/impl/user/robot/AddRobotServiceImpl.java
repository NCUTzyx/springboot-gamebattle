package com.zyx.service.impl.user.robot;

import com.zyx.mapper.RobotMapper;
import com.zyx.pojo.Robot;
import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import com.zyx.service.user.robot.AddRobotService;
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
public class AddRobotServiceImpl implements AddRobotService {

    @Resource
    private RobotMapper robotMapper;

    @Override
    public Map<String, String> addRobot(Map<String, String> data) {

        //获取用户上下文信息
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

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

        Date now = new Date();
        Robot robot = new Robot(null,user.getId(),name,description,content,1500,now,now);

        robotMapper.insert(robot);
        map.put("message","success");

        return map;
    }
}
