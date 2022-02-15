package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelItemMapper {
    /**
     * 添加自由行项
     *
     * @param travelItem
     * @throws Exception
     */
    void add(TravelItem travelItem) throws Exception;

    /**
     * 根据条件分页查询
     *
     * @param queryString
     * @return
     * @throws Exception
     */
    Page<TravelItem> findTravelItemByQueryString(@Param("queryString") String queryString) throws Exception;

    /**
     * 删除自由行项
     *
     * @param id
     * @throws Exception
     */
    void delete(@Param("id") Integer id) throws Exception;

    /**
     * 根据id查询自由行项
     *
     * @param id
     * @return
     * @throws Exception
     */
    TravelItem findById(@Param("id") Integer id) throws Exception;

    /**
     * 修改自由行项
     *
     * @param travelItem
     * @throws Exception
     */
    void update(TravelItem travelItem) throws Exception;

    /**
     * 查询所有自由行
     *
     * @return
     * @throws Exception
     */
    List<TravelItem> findAll() throws Exception;

    /**
     * 根据套餐游id查询自由行
     *
     * @param id
     * @return
     */
    List<TravelItem> findTravelItemListById(@Param("id") Integer id) throws Exception;
}
