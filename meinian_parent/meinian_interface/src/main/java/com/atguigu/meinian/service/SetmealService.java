package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {

    void delete(Integer id) throws Exception;

    PageResult findPage(QueryPageBean queryPageBean) throws Exception;

    void add(Setmeal setmeal, Integer[] travelGroupIds) throws Exception;

    Map<String, Object> findData(Integer id) throws Exception;

    void edit(Integer[] travelGroupIds, Setmeal setmeal) throws Exception;

    List<Setmeal> findAll() throws Exception;

    Setmeal findById(Integer id) throws Exception;

    List<Map<String, Object>> findSetmealCount();

}
