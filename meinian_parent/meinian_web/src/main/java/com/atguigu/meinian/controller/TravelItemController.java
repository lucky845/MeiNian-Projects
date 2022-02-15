package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.TravelItem;
import com.atguigu.meinian.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference // 引用注册中心的服务
    private TravelItemService travelItemService;

    /**
     * 新增自由行
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('TRAVELITEM_ADD')")// 权限校验
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.add(travelItem);
            // 添加自由行项成功
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            // 添加自由行项失败
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    /**
     * 根据条件查询分页列表
     */
    @RequestMapping("/findPage")
    @PreAuthorize("hasAnyAuthority('TRAVEITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            // 查询分页数据成功
            return travelItemService.findTravelItemByQueryString(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 查询分页数据失败
        return null;
    }

    /**
     * 根据id删除自由行项
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasAnyAuthority('TRAVELITEM_DELETE')")
    public Result delete(Integer id) {
        try {
            travelItemService.delete(id);
            // 删除自由行项成功
            return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 删除自由行项失败
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    /**
     * 根据id查找分页信息
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            TravelItem travelItem = travelItemService.findById(id);
            if (travelItem == null) {
                // 没有查询到自由行项信息
                return new Result(false, MessageConstant.QUERY_TRAVELITEM_ERROR);
            }
            // 查询到了自由行信息
            return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            // 查询自由行项失败
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    /**
     * 修改自由行项信息
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAnyAuthority('TRAVELITEM_EDIT')")
    public Result update(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.update(travelItem);
            // 修改自由行项成功
            return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 修改自由行项失败
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }

    /**
     * 查询所有自由行信息
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<TravelItem> travelItemList = travelItemService.findAll();
            return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

}
