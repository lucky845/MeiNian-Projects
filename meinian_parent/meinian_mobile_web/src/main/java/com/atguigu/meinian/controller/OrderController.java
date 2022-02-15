package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.constants.RedisMessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.service.OrderService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约信息
     *
     * @param map
     * @return
     */
    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map<String, String> map) {
        try {
            // 获取map中的手机号
            String telephone = map.get("telephone");
            // 获取map中的验证码
            String requestCode = map.get("validateCode");
            // 获取redis中正确的验证码
            String redisCode = jedisPool.getResource().get(telephone + ":" + RedisMessageConstant.SENDTYPE_ORDER);
            // 判断验证码是否正确
            if (redisCode == null || !redisCode.equalsIgnoreCase(requestCode)) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            // 调用业务层, 进行判断
            return orderService.submitOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    /**
     * 根据id查询预约成功界面信息回显
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map<String, Object> map = orderService.findById(id);
            // 处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            String dateStr = DateUtils.parseDate2String(orderDate);
            map.put("orderDate", dateStr);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
