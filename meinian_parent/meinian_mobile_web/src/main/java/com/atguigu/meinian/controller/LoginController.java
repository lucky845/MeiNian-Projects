package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.constants.RedisMessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    /**
     * 登录功能
     *
     * @param map
     * @return
     */
    @RequestMapping("/checkLogin")
    public Result checkLogin(Map<String, String> map) {
        // 获取用户输入的手机号
        String telephone = map.get("telephone");
        // 获取用户输入的验证码
        String validateCode = map.get("validateCode");
        // 获取redis中的验证码
        String redisCode = jedisPool.getResource().get(telephone + ":" + RedisMessageConstant.SENDTYPE_LOGIN);
        if (redisCode == null || !redisCode.equalsIgnoreCase(validateCode)) {
            // 验证码错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 验证码正确， 将手机号注册为会员
        try {
            memberService.addMember(telephone);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

}
