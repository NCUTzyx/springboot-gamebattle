package com.zyx.service.user.account;

import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
public interface RegisterService {

    Map<String, String> register(String username, String password, String confirmPassword);
}
