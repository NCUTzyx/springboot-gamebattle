package com.zyx.service.impl.peak;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyx.mapper.UserMapper;
import com.zyx.pojo.User;
import com.zyx.service.peak.GetPeakListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class GetPeakListServiceImpl implements GetPeakListService {

    @Resource
    private UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page) {

        IPage<User> userIPage = new Page<>(page,10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        List<User> users = userMapper.selectPage(userIPage,queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        for (User user: users){
            user.setPassword("");
        }
        resp.put("users",users);
        resp.put("users_count",userMapper.selectCount(null));
        return resp;
    }
}
