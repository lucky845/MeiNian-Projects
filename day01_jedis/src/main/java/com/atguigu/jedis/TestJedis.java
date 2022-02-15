package com.atguigu.jedis;

import redis.clients.jedis.Jedis;

public class TestJedis {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.229.128", 6379);
        System.out.println("jedis = " + jedis);
        // 测试连接是否成功
        // System.out.println(jedis.ping());
        jedis.set("username", "张三");
        String username = jedis.get("username");
        System.out.println("username = " + username);
        jedis.del("username");
        jedis.close();

    }

}
