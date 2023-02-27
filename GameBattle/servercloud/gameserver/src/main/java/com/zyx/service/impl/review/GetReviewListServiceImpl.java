package com.zyx.service.impl.review;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyx.mapper.ReviewMapper;
import com.zyx.mapper.UserMapper;
import com.zyx.pojo.Review;
import com.zyx.pojo.User;
import com.zyx.service.review.GetReviewListService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 张宇森
 * @version 1.0
 */
@Service
public class GetReviewListServiceImpl implements GetReviewListService {

    @Resource
    private ReviewMapper reviewMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page) {

        IPage<Review> reviewIPage = new Page<>(page,10);
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Review> reviewList = reviewMapper.selectPage(reviewIPage,queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        List<JSONObject> items = new LinkedList<>();
        for (Review review: reviewList) {
            User userA = userMapper.selectById(review.getAId());
            User userB = userMapper.selectById(review.getBId());
            JSONObject item = new JSONObject();
            item.put("a_headshot",userA.getHeadshot());
            item.put("a_username",userA.getUsername());
            item.put("b_headshot",userB.getHeadshot());
            item.put("b_username",userB.getUsername());

            String result = "平局";
            if ("A".equals(review.getLoser())){
                result = "B胜";
            }else if ("B".equals(review.getLoser())){
                result = "A胜";
            }

            item.put("result",result);
            item.put("review",review);
            items.add(item);
        }

        resp.put("reviews",items);
        resp.put("reviews_count",reviewMapper.selectCount(null));

        return resp;
    }
}
