package com.zyx.service.peak;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author 张宇森
 * @version 1.0
 */

public interface GetPeakListService {

    JSONObject getList(Integer page);
}
