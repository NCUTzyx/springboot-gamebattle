package com.zyx.server.utils;

import com.zyx.utils.JwtUtil;
import io.jsonwebtoken.Claims;

/**
 * @author 张宇森
 * @version 1.0
 * 工具类
 */
public class JwtAuthentication {

    public static Integer getUserId(String token){

        int userId = -1;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
}
