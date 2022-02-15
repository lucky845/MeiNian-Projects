package com.atguigu.servlet;

import com.atguigu.utils.JedisPoolUtils;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;

/**
 * 秒杀案例方案一: 不使用事务
 * 秒杀案例方案二: 使用事务
 */
public class SecKill_redis {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SecKill_redis.class);

    public static void main(String[] args) {

        JedisPool jedisPool = JedisPoolUtils.getJedisPoolInstance();
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());

    }

    // 使用ab工具发起请求
    //ab -n 1000 -c 200 -p /opt/postfile -T "application/x-www-form-urlencoded" 192.168.14.63:8080/doseckill

    public static boolean doSecKill(String uid, String prodid) throws IOException {

        //拼接key
        String kcKey = "seckill:" + prodid + ":kc";
        String userKey = "seckill:" + prodid + ":user";

        // 获取jedis连接
        JedisPool jedisPool = JedisPoolUtils.getJedisPoolInstance();
        Jedis jedis = jedisPool.getResource();
        // 监视kcKey
        jedis.watch(kcKey);
        // 获取商品的库存信息
        String kcStr = jedis.get(kcKey);

        // 判断库存是否为空
        if (kcStr == null) {
            // 表示redis中没有库存信息,即秒杀未开始
            System.out.println("秒杀还未开始!");
            // 关闭资源
            JedisPoolUtils.release(jedisPool, jedis);
            return false;
        }

        // 判断uid是否已经存在于redis中,即该用户是否已经秒杀成功
        if (jedis.sismember(userKey, uid)) {
            // 表示该用户已经秒杀成功
            System.out.println("您已经秒杀过了,请勿重复秒杀!");
            JedisPoolUtils.release(jedisPool, jedis);
            return false;
        }

        // 将字符串类型的库存转换为int类型
        int kc = Integer.parseInt(kcStr);
        // 判断库存是否足够
        if (kc <= 0) {
            // 表示秒杀已经结束
            System.out.println("秒杀已结束!");
            JedisPoolUtils.release(jedisPool, jedis);
            return false;
        }

        // 表示秒杀成功
        // 库存减一,人数加一

        // ①不使用事务(出现超卖)
//        jedis.decr(kcKey);
//        jedis.sadd(userKey,uid);
//        System.out.println("秒杀成功!");
//        JedisPoolUtils.release(jedisPool,jedis);
//        return true;

        // ② 使用事务(出现库存遗留)
        // 开启事务
        Transaction transaction = jedis.multi();
        // 库存减一
        transaction.decr(kcKey);
        // 用户加一
        transaction.sadd(userKey, uid);
        // 执行事务
        List<Object> exec = transaction.exec();
        // 判断事务是否正常执行
        if (exec == null || exec.size() == 0) {
            // 表示事务未正常执行
            System.out.println("你很不幸!");
            JedisPoolUtils.release(jedisPool, jedis);
            return false;
        }
        System.out.println("秒杀成功!");
        JedisPoolUtils.release(jedisPool, jedis);
        return true;
    }


}
















