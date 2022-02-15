package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.OrderSetting;
import com.atguigu.meinian.service.OrderSettingService;
import com.atguigu.meinian.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 上传excel文件
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {

        try {
            // 读取excel文件获取所有行的信息
            List<String[]> list = POIUtils.readExcel(excelFile);
            // 将信息保存到数据库中
            orderSettingService.addOrderSetting(list);
            // 批量导入数据成功
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 批量导入失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 查询日历信息并展示
     *
     * @param date
     * @return
     */
    @RequestMapping("/queryDate")
    public Result queryDate(String date) {

        try {
            List<Map<String, Object>> list = orderSettingService.queryDate(date);
            // 查询日历信息成功
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            // 查询日历信息失败
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }

    }

    /**
     * 根据日期修改预约人数
     *
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByOrderDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
