package com.atguigu.meinian.controller;

import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.constants.RedisMessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.utils.SMSUtils;
import com.atguigu.meinian.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约功能发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            // 生成随机验证码
            String redisCode = ValidateCodeUtils.generateValidateCode4String(6);
            // 发送验证码
            SMSUtils.sendShortMessage(telephone, redisCode);
            // 将验证码保存到redis中
            jedisPool.getResource().setex(telephone + ":" + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, redisCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 登录功能发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        try {
            // 生成随机验证码
            String redisCode = ValidateCodeUtils.generateValidateCode4String(6);
            // 发送验证码
            SMSUtils.sendShortMessage(telephone, redisCode);
            // 将验证码保存到redis中
            jedisPool.getResource().setex(telephone + ":" + RedisMessageConstant.SENDTYPE_LOGIN, 5 * 60, redisCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
