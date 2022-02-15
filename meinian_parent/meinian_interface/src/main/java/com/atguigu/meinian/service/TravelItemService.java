package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.pojo.TravelItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelItemService {
    /**
     * 添加自由行项
     *
     * @param travelItem
     * @throws Exception
     */
    void add(TravelItem travelItem) throws Exception;

    /**
     * 根据条件查询分页列表
     *
     * @param queryPageBean
     * @return
     * @throws Exception
     */
    PageResult findTravelItemByQueryString(QueryPageBean queryPageBean) throws Exception;

    /**
     * 删除自由行项
     * @param id
     * @throws Exception
     */
    void delete(Integer id) throws Exception;

    /**
     * 根据id查询自由行项
     * @param id
     * @return
     * @throws Exception
     */
    TravelItem findById(@Param("id") Integer id) throws Exception;

    /**
     * 修改自由行项
     * @param travelItem
     * @throws Exception
     */
    void update(@Param("travelItem") TravelItem travelItem) throws Exception;

    /**
     * 查询所有自由行信息
     * @return
     * @throws Exception
     */
    List<TravelItem> findAll() throws Exception;
}
