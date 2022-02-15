package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.mapper.OrderSettingMapper;
import com.atguigu.meinian.pojo.OrderSetting;
import com.atguigu.meinian.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    /**
     * 添加预约信息到数据库
     *
     * @param list
     * @throws Exception
     */
    @Override
    public void addOrderSetting(List<String[]> list) throws Exception {
        // 遍历集合中的每一行的数据(预约日期，可预约人数)
        for (String[] str : list) {
            // 将excel中的每一行数据转换为OrderSetting对象
            OrderSetting orderSetting = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));
            // 判断当前日期，之前是否已经添加过预约设置信息，使用当前时间作为查询条件
            long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
            if (count != 0) {
                // 当前日期之前已经添加过了预约设置信息，更新预约人数
                orderSettingMapper.editOrderSettingByOrderDate(orderSetting);
            } else {
                // 当前日期之前没有添加过预约设置信息， 添加一条新记录
                orderSettingMapper.addOrderSetting(orderSetting);
            }
        }
    }

    /**
     * 查询日历信息
     *
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryDate(String date) throws Exception {
        // 开始的日期
        String startDate = date + "-01";
        // 结束的日期
        String endDate = date + "-31";
        // 需要返回的ist集合
        List<Map<String, Object>> list = new ArrayList<>();
        // 查询所有的OrderSetting集合
        List<OrderSetting> orderSettingList = orderSettingMapper.findOrderSettingByDate(startDate, endDate);
        // 遍历集合，为map赋值
        for (OrderSetting orderSetting : orderSettingList) {
            // list内部的map
            Map<String, Object> map = new HashMap<>();
            // 获取日
            map.put("date", orderSetting.getOrderDate().getDate());
            // 获取可预约人数
            map.put("number", orderSetting.getNumber());
            // 获取已预约人数
            map.put("reservations", orderSetting.getReservations());
            // 将map放到list里面
            list.add(map);
        }
        // 将list返回
        return list;
    }

    /**
     * 根据日期修改预约设置信息
     *
     * @param orderSetting
     * @throws Exception
     */
    @Override
    public void editNumberByOrderDate(OrderSetting orderSetting) throws Exception {
        // 判断当前日期，之前是否已经添加过预约设置信息，使用当前时间作为查询条件
        long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count != 0) {
            // 当前日期之前已经添加过了预约设置信息，更新预约人数
            orderSettingMapper.editOrderSettingByOrderDate(orderSetting);
        } else {
            // 当前日期之前没有添加过预约设置信息， 添加一条新记录
            orderSettingMapper.addOrderSetting(orderSetting);
        }
    }
}
