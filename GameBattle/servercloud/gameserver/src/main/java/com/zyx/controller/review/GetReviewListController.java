package com.zyx.controller.review;

import com.alibaba.fastjson2.JSONObject;
import com.zyx.service.review.GetReviewListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@RestController
public class GetReviewListController {

    @Resource
    private GetReviewListService getReviewListService;

    @GetMapping("/api/review/getlist")
    public JSONObject getList(@RequestParam Map<String,String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getReviewListService.getList(page);
    }
}
