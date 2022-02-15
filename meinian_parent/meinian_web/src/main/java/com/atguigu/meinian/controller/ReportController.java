package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.service.MemberService;
import com.atguigu.meinian.service.ReportService;
import com.atguigu.meinian.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        // 获取日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        //
        List<String> monthsList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            monthsList.add(new SimpleDateFormat(("yyyy-MM")).format(calendar.getTime()));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("months", monthsList);
        // 查循所有会员
        List<Integer> memberCount = memberService.findMemberCountByMonth(monthsList);
        map.put("memberCount", memberCount);

        // 获取会员统计数据成功
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    /**
     * 获取套餐统计饼状图数据
     *
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map<String, Object>> list = setmealService.findSetmealCount();
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, list);
    }

    /**
     * 获取运营统计数据
     *
     * @return
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

}
