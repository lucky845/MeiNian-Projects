package com.atguigu.meinian.service;

import com.atguigu.meinian.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void addOrderSetting(List<String[]> list) throws Exception;

    List<Map<String, Object>> queryDate(String date) throws Exception;

    void editNumberByOrderDate(OrderSetting orderSetting) throws Exception;
}
