package com.zyx.service.impl.user.robot;

import com.zyx.mapper.RobotMapper;
import com.zyx.pojo.Robot;
import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import com.zyx.service.user.robot.DeleteRobotService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class DeleteRobotServiceImpl implements DeleteRobotService {

    @Resource
    private RobotMapper robotMapper;

    @Override
    public Map<String, String> deleteRobot(Map<String, String> data) {

        //获取用户上下文信息
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        int robot_id = Integer.parseInt(data.get("robot_id"));
        Robot robot = robotMapper.selectById(robot_id);

        Map<String, String> map = new HashMap<>();

        if (robot == null){
            map.put("message","你的机器人不存在或已被删除");
            return map;
        }

        if (!robot.getUserId().equals(user.getId())){
            map.put("message","你没有权限删除该机器人");
            return map;
        }

        robotMapper.deleteById(robot_id);

        map.put("message","success");

        return map;
    }
}
