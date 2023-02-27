package com.zyx;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class GameserverApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //以字符串形式编码
        System.out.println(bCryptPasswordEncoder.encode("123456"));

        //编码匹配
        System.out.println(bCryptPasswordEncoder.matches("123456","$2a$10$g1T2tcXg6SJtjVuEudaBTewkP4bZ8XGjFPS10hAJWMe4jKKvkJsDK"));
    }

}
