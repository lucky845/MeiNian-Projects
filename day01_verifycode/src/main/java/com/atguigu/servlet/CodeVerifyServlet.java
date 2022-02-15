package com.atguigu.servlet;

import redis.clients.jedis.Jedis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CodeVerifyServlet", value = "/CodeVerifyServlet")
public class CodeVerifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 获取请求参数
        String phone_no = req.getParameter("phone_no");
        String verify_code = req.getParameter("verify_code");
        // 拼接key
        String codeKey = "verifyCode:" + phone_no + ":code";
        // 获取redis中存储的正确验证码
        Jedis jedis = new Jedis("192.168.229.128", 6379);
        String codeInRedis = jedis.get(codeKey);
        // 判断验证码是否正确
        if (verify_code.equals(codeInRedis)) {
            // 验证码正确
            resp.getWriter().print(true);
        } else {
            // 验证码错误
            resp.getWriter().print(false);
        }
        jedis.close();
    }

}
