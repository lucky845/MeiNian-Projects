package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.mapper.MemberMapper;
import com.atguigu.meinian.mapper.OrderMapper;
import com.atguigu.meinian.mapper.OrderSettingMapper;
import com.atguigu.meinian.pojo.Member;
import com.atguigu.meinian.pojo.Order;
import com.atguigu.meinian.pojo.OrderSetting;
import com.atguigu.meinian.service.OrderService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 提交预约信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Result submitOrder(Map<String, String> map) throws Exception {
        // 获取预约日期
        String orderDateStr = map.get("orderDate");
        // 将字符串类型的日期转换为date类型
        Date orderDate = DateUtils.parseString2Date(orderDateStr);

        // *******1. 判断当前预约日期是否可以预约，即将预约日期作为条件查询预约设置信息，判断当前预约日期是否可以预约*******
        OrderSetting orderSetting = orderSettingMapper.getOrderSettingByOrderDate(orderDate);
        // 判断orderSetting是否为空
        if (orderSetting == null) {
            // 预约日期没有对应的预约设置，即预约失败
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 表示预约日期可以预约

        // ******2. 判断当前预约日期是否已满*******
        if (orderSetting.getNumber() == orderSetting.getReservations()) {
            // 表示当前日期预约已满
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        // 表示当前日期还有可预约数

        // ******3. 判断当前用户是否是会员*******
        // 获取当前用户输入的手机号
        String telephone = map.get("telephone");
        // 获取数据库中对应的会员信息
        Member member = memberMapper.getMemberByTelephone(telephone);
        // 判断当前用户是否是会员
        if (member == null) {
            // 未注册为会员，需要将其注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(orderDate);
            member.setIdCard(map.get("idCard"));
            member.setSex(map.get("sex"));
            member.setName(map.get("name"));
            // 添加会员
            memberMapper.addMember(member);
        }
        // *******4.当前用户已经注册为会员，需要判断当前会员是否预约了同一天的同一个套餐********
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.parseInt(map.get("setmealId")));
        // 根据预约信息查询记录数
        Integer count = orderMapper.getCountByCondition(order);
        if (count > 0) {
            // 该用户当天已经预约过了同一个套餐，预约失败
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
        // ******5.实现预约功能，将预约设置表的预约人数+1，并保存预约信息
        // orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.editReservationsByOrderDate(orderDate);
        Order order1 = new Order();
        order1.setSetmealId(Integer.parseInt(map.get("setmealId")));
        order1.setOrderDate(orderDate);
        order1.setMemberId(member.getId());
        order1.setOrderType(Order.ORDERTYPE_WEIXIN);
        order1.setOrderStatus(Order.ORDERSTATUS_NO);
        // 添加预约信息
        orderMapper.addOrder(order1);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order1.getId());
    }

    /**
     * 根据id查询预约信息
     *
     * @param id 订单id
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findById(Integer id) throws Exception {
        return orderMapper.findById(id);
    }
}
