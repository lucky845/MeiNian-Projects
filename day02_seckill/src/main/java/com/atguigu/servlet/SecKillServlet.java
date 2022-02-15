package com.atguigu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;


/**
 * Servlet implementation class miaosha
 */
@WebServlet(value = "/doseckill")
public class SecKillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String userid = new Random().nextInt(50000) + "";

        String prodid = request.getParameter("prodid");

        // 使用方案一或二
//        boolean if_success = SecKill_redis.doSecKill(userid, prodid);

        // 使用方案三
        boolean if_success = SecKill_redisByScript.doSecKill(userid, prodid);

        response.getWriter().print(if_success);
    }


}
