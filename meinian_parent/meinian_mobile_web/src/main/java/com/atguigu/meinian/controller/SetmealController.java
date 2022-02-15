package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.Setmeal;
import com.atguigu.meinian.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有套餐列表
     *
     * @return
     */
    @RequestMapping("/getSetmeal")
    public Result findAll() {
        try {
            List<Setmeal> setmealList = setmealService.findAll();
            // 查询成功
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            // 查询失败
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {

        try {
            Setmeal setmeal = setmealService.findById(id);
            // 查询套餐详情成功
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            // 查询套餐详情失败
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
