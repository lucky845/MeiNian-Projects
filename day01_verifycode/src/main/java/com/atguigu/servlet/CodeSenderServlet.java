package com.atguigu.servlet;

import redis.clients.jedis.Jedis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "CodeSenderServlet", value = "/CodeSenderServlet")
public class CodeSenderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // ① 判断发送次数
        // 获取手机号码
        String phone_no = req.getParameter("phone_no");
        // 拼接key
        String codeKey = "verifyCode:" + phone_no + ":code";
        String countKey = "verifyCode:" + phone_no + ":count";
        // 获取Jedis连接对象
        Jedis jedis = new Jedis("192.168.229.128", 6379);
        // 获取修改手机号发送验证码的次数
        String countStr = jedis.get(countKey);
        // 判断是否为第一次获取验证码
        if (countStr == null) {
            // 表示redis中没有存储手机号发送验证码的次数,即为当天的第一次
            // 记录当前手机的次数为1,每天三次验证码,所以验证码的次数需要保存一天
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else {
            // 表示redis中已经存储了手机号发送验证码的次数,次数加一
            int count = Integer.parseInt(jedis.get(countKey));
            if (count == 3) {
                // 说明今天的发送次数已经到三次了,直接响应服务器
                resp.getWriter().print("limit");
                jedis.close();
                return;
            } else {
                // 说明今天的发送次数还没有到三次,次数+1
                jedis.incr(countKey);
            }
            // 获取验证码,并将验证码保存到redis中
            jedis.setex(codeKey, 120, getRandomCode());
            resp.getWriter().print(true);
            jedis.close();
        }

    }

    /**
     * 获得一个六位数的随机验证码
     *
     * @return 随机验证码
     */
    public String getRandomCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
