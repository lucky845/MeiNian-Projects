package com.atguigu.job;

import com.atguigu.meinian.constants.RedisConstant;
import com.atguigu.meinian.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class ClearImg {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {

        // 清理七牛云中的图片
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // 清理
        for (String fileName : set) {
            // 清理七牛云中的数据
            QiniuUtils.deleteFileFromQiniu(fileName);
            System.out.println("清理了文件,文件名是:  " + fileName);
            // 清理redis中的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
        }

    }

}
