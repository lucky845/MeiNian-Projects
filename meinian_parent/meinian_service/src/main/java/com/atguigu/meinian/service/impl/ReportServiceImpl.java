package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.mapper.MemberMapper;
import com.atguigu.meinian.mapper.OrderMapper;
import com.atguigu.meinian.mapper.ReportMapper;
import com.atguigu.meinian.service.ReportService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 获取运营统计数据
     *
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String, Object> map = null;
        try {
            // 1. reportDate: 日期
            Date reportDate = new Date();
            // 获取本周第一天
            Date thisWeekMonday = DateUtils.getThisWeekMonday();
            // 获取本周最后一天
            Date thisWeekSunday = DateUtils.getSundayOfThisWeek();
            // 获取本月第一天
            Date thisMonthFirstDay = DateUtils.getFirstDay4ThisMonth();
            // 获取本月最后一天
            Date thisMonthLastDay = DateUtils.getLastDay4ThisMonth();

            // 2. todayNewMember : 新增会员数
            Integer todayNewMember = memberMapper.getTodayNewMember(reportDate);
            // 3. totalMember: 总会员数
            Integer totalMember = memberMapper.getTotalMember();
            // 4. thisWeekNewMember: 本周新增会员数
            Integer thisWeekNewMember = memberMapper.getThisWeekAndMonthNewMember(thisWeekMonday);
            // 5. thisMonthNewMember: 本月新增会员数
            Integer thisMonthNewMember = memberMapper.getThisWeekAndMonthNewMember(thisMonthFirstDay);
            // 6. todayOrderNumber: 今日预约数
            Integer todayOrderNumber = orderMapper.getTodayOrderNumber(reportDate);
            // 7. todayVisitsNumber: 今日出游数
            Integer todayVisitsNumber = orderMapper.getTodayVisitsNumber(reportDate);
            // 8. thisWeekOrderNumber: 本周预约数
            Integer thisWeekOrderNumber = orderMapper.getThisWeekAndMonthOrderNumber(thisWeekMonday, thisWeekSunday);
            // 9. thisWeekVisitsNumber: 本周出游数
            Integer thisWeekVisitsNumber = orderMapper.getThisWeekAndMonthVisitsNumber(thisWeekMonday, thisWeekSunday);
            // 10. thisMonthOrderNumber: 本月预约数
            Integer thisMonthOrderNumber = orderMapper.getThisWeekAndMonthOrderNumber(thisMonthFirstDay, thisMonthLastDay);
            // 11. thisMonthVisitsNumber: 本月出游数
            Integer thisMonthVisitsNumber = orderMapper.getThisWeekAndMonthVisitsNumber(thisMonthFirstDay, thisMonthLastDay);
            // 12. hotSetmeal: 热门套餐
            List<Map<String, Object>> hotSetmeal = orderMapper.getHotSetmeal();
            map = new HashMap<>();
            map.put("reportDate", reportDate);
            map.put("todayNewMember", todayNewMember);
            map.put("totalMember", totalMember);
            map.put("thisWeekNewMember", thisWeekNewMember);
            map.put("thisMonthNewMember", thisMonthNewMember);
            map.put("todayOrderNumber", todayOrderNumber);
            map.put("todayVisitsNumber", todayVisitsNumber);
            map.put("thisWeekOrderNumber", thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber", thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
            map.put("hotSetmeal", hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
