package com.zyx.service.user.account.qq;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author 张宇森
 * @version 1.0
 */
public interface QQWebService {

    JSONObject applyCode();
    JSONObject receiveCode(String code,String state);
}
