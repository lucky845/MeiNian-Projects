package com.atguigu.test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class RedisClusterTest {

    public static void main(String[] args) {

        HostAndPort hostAndPort = new HostAndPort("192.168.229.128",6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);
        System.out.println(jedisCluster.get("a"));

//        Jedis jedis = new Jedis("192.168.229.128",6379);
//        System.out.println(jedis.ping());
//        jedis.close();

    }

}
