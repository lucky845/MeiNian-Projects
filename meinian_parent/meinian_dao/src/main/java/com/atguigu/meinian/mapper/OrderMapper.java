package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {

    /**
     * 添加预约信息
     *
     * @param order
     * @throws Exception
     */
    void addOrder(@Param("order") Order order) throws Exception;

    /**
     * 根据日期查询记录数
     *
     * @param order
     * @return
     * @throws Exception
     */
    Integer getCountByCondition(@Param("order") Order order) throws Exception;

    /**
     * 根据id查询预约信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    Map<String, Object> findById(@Param("id") Integer id) throws Exception;

    /**
     * 获取今日预约数
     *
     * @param orderDate
     * @return
     */
    Integer getTodayOrderNumber(@Param("orderDate") Date orderDate);

    /**
     * 获取今日出游数
     *
     * @param orderDate
     * @return
     */
    Integer getTodayVisitsNumber(@Param("orderDate") Date orderDate);

    /**
     * 查询本周/本月预约数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getThisWeekAndMonthOrderNumber(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询本周/本月出游数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getThisWeekAndMonthVisitsNumber(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 获取热门套餐
     *
     * @return
     */
    List<Map<String, Object>> getHotSetmeal();

}
