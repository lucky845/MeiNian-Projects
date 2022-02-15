package com.atguigu.servlet;

import com.atguigu.utils.JedisPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 秒杀案例方案三: 使用lua脚本
 */
public class SecKill_redisByScript {

    private static final Logger logger = LoggerFactory.getLogger(SecKill_redisByScript.class);
    static String secKillScript = "local userid=KEYS[1];\r\n" +
            "local prodid=KEYS[2];\r\n" +
            "local qtkey='seckill:'..prodid..\":kc\";\r\n" +
            "local usersKey='seckill:'..prodid..\":user\";\r\n" +
            "local userExists=redis.call(\"sismember\",usersKey,userid);\r\n" +
            "if tonumber(userExists)==1 then \r\n" +
            "   return 2;\r\n" +
            "end\r\n" +
            "local num= redis.call(\"get\" ,qtkey);\r\n" +
            "if tonumber(num)<=0 then \r\n" +
            "   return 0;\r\n" +
            "else \r\n" +
            "   redis.call(\"decr\",qtkey);\r\n" +
            "   redis.call(\"sadd\",usersKey,userid);\r\n" +
            "end\r\n" +
            "return 1";
    static String secKillScript2 =
            "local userExists=redis.call(\"sismember\",\"{sk}:0101:usr\",userid);\r\n" +
                    " return 1";

    public static boolean doSecKill(String uid, String prodid) throws IOException {
        JedisPool jedisPool = JedisPoolUtils.getJedisPoolInstance();
        Jedis jedis = jedisPool.getResource();
        String sha1 = jedis.scriptLoad(secKillScript);
        // 解析Low脚本
        Object result = jedis.evalsha(sha1, 2, uid, prodid);
        String reString = String.valueOf(result);
        if ("0".equals(reString)) {
            System.err.println("已抢空！！");
        } else if ("1".equals(reString)) {
            System.out.println("抢购成功！！！！");
        } else if ("2".equals(reString)) {
            System.err.println("该用户已抢过！！");
        } else {
            System.err.println("抢购异常！！");
        }
        jedis.close();
        return true;
    }

    public static void main(String[] args) {


        JedisPool jedispool = JedisPoolUtils.getJedisPoolInstance();

        Jedis jedis = jedispool.getResource();
        System.out.println(jedis.ping());

        Set<HostAndPort> set = new HashSet<>();


        //	doSecKill("201","sk:0101");


    }


}
