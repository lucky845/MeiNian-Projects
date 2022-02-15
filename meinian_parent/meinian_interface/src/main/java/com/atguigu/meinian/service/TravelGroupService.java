package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.pojo.TravelGroup;

import java.util.List;
import java.util.Map;

public interface TravelGroupService {


    void add(TravelGroup travelGroup, Integer[] travelItemIds) throws Exception;

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id) throws Exception;

    Map<String, Object> findData(Integer id) throws Exception;

    void edit(Integer[] travelItemIds, TravelGroup travelGroup) throws Exception;

    List<TravelGroup> findAll() throws Exception;
}
