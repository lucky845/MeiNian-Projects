package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.Result;

import java.util.Map;

public interface OrderService {

    /**
     * 提交预约信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    Result submitOrder(Map<String, String> map) throws Exception;

    /**
     * 根据id查询预约信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    Map<String, Object> findById(Integer id) throws Exception;
}
