package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelGroupMapper {

    /**
     * 添加一条跟团游信息
     *
     * @param travelGroup
     * @throws Exception
     */
    void add(TravelGroup travelGroup) throws Exception;

    /**
     * 将数据添加到中间表中
     *
     * @param travelGroupId
     * @param travelItemIds
     * @throws Exception
     */
    void addTravelGroupAndTravelItem(@Param("travelGroupId") Integer travelGroupId, @Param("travelItemIds") Integer[] travelItemIds) throws Exception;

    /**
     * 查询分页信息
     *
     * @param queryString
     * @return
     */
    Page<TravelGroup> findPage(@Param("queryString") String queryString);

    /**
     * 删除中间表信息
     *
     * @param travelGroupId
     * @throws Exception
     */
    void deleteTravelGroupAndTravelItem(@Param("travelGroupId") Integer travelGroupId) throws Exception;

    /**
     * 删除跟团游信息
     *
     * @param travelGroupId
     * @throws Exception
     */
    void deleteTravelGroupById(@Param("travelGroupId") Integer travelGroupId) throws Exception;

    /**
     * 根据id查询跟团游信息
     *
     * @param travelGroupId
     * @return
     * @throws Exception
     */
    TravelGroup findById(@Param("travelGroupId") Integer travelGroupId) throws Exception;

    /**
     * 查询该跟团游对应的中间表的信息
     *
     * @param travelGroupId
     * @return
     * @throws Exception
     */
    List<Integer> findTravelGroupAndTravelItem(@Param("travelGroupId") Integer travelGroupId) throws Exception;

    /**
     * 修改跟团游信息
     *
     * @param travelGroup
     * @throws Exception
     */
    void edit(TravelGroup travelGroup) throws Exception;

    /**
     * 查询所有的跟团游信息
     *
     * @return
     * @throws Exception
     */
    List<TravelGroup> findAll() throws Exception;

    /**
     * 根据套餐id查询跟团游信息
     *
     * @param id
     * @return
     */
    List<TravelGroup> findTravelGroupListById(@Param("id") Integer id);
}
