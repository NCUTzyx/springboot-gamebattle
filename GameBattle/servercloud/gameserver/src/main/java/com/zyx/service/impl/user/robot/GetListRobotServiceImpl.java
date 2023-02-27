package com.zyx.service.impl.user.robot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyx.mapper.RobotMapper;
import com.zyx.pojo.Robot;
import com.zyx.pojo.User;
import com.zyx.service.impl.utils.UserDetailsImpl;
import com.zyx.service.user.robot.GetListRobotService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class GetListRobotServiceImpl implements GetListRobotService {

    @Resource
    private RobotMapper robotMapper;

    @Override
    public List<Robot> getListRobot() {

        //获取用户上下文信息
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Robot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());

        return robotMapper.selectList(queryWrapper);
    }
}
