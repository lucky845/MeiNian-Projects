package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {

    /**
     * 根据日期查询记录数
     *
     * @param orderDate
     * @return
     * @throws Exception
     */
    long findCountByOrderDate(@Param("orderDate") Date orderDate) throws Exception;

    /**
     * 根据日期修改预约人数
     *
     * @param orderSetting
     * @throws Exception
     */
    void editOrderSettingByOrderDate(@Param("orderSetting") OrderSetting orderSetting) throws Exception;

    /**
     * 添加一条预约设置信息
     *
     * @param orderSetting
     * @throws Exception
     */
    void addOrderSetting(@Param("orderSetting") OrderSetting orderSetting) throws Exception;

    /**
     * 根据开始和结束时间查询预约设置集合
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    List<OrderSetting> findOrderSettingByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;

    /**
     * 根据日期查询预约设置信息
     *
     * @param orderDate
     * @return
     * @throws Exception
     */
    OrderSetting getOrderSettingByOrderDate(@Param("orderDate") Date orderDate) throws Exception;

    /**
     * 根据时间查询预约设置信息将预约数+1
     * @param orderDate
     * @throws Exception
     */
    void editReservationsByOrderDate(@Param("orderDate") Date orderDate) throws Exception;
}
