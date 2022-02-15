package com.atguigu.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
    private static volatile JedisPool jedisPool = null;


    private JedisPoolUtils() {
    }

    /**
     * 获取JedsiPool的对象
     *
     * @return 连接池对象
     */
    public static JedisPool getJedisPoolInstance() {

        if (null == jedisPool) {
            synchronized (JedisPoolUtils.class) {
                if (null == jedisPool) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    //控制一个pool可分配多少个jedis实例
                    poolConfig.setMaxTotal(200);
                    //控制一个pool最多有多少个状态为idle(空闲)的jedis实例
                    poolConfig.setMaxIdle(32);
                    //获取连接时的最大等待毫秒数
                    poolConfig.setMaxWaitMillis(100 * 1000);
                    //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
                    poolConfig.setBlockWhenExhausted(true);
                    ////在获取连接的时候检查有效性, 默认false
                    poolConfig.setTestOnBorrow(true);  // ping  PONG

                    jedisPool = new JedisPool(poolConfig, "192.168.229.128", 6379, 60000);

                }
            }
        }
        return jedisPool;
    }

    /**
     * 释放资源
     *
     * @param jedisPool
     * @param jedis
     */
    public static void release(JedisPool jedisPool, Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnResource(jedis);
        }
    }

}
