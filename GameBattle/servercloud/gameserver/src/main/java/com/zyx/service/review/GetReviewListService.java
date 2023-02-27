package com.zyx.service.review;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author 张宇森
 * @version 1.0
 */
public interface GetReviewListService {

    JSONObject getList(Integer page);

}
