package com.zyx.service.user.account;

import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
public interface LoginService {

    Map<String, String> getToken(String username, String password);

}
