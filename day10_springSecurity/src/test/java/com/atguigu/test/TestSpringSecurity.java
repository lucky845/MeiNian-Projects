package com.atguigu.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestSpringSecurity {

    // springSecurity加盐加密
    @Test
    public void testSpringSecurity(){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String admin = encoder.encode("admin");
        System.out.println("admin = " + admin);


    }

}
