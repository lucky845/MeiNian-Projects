package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.TravelGroup;
import com.atguigu.meinian.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    /**
     * 添加一条跟团游信息
     *
     * @param travelGroup
     * @param travelItemIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        try {
            travelGroupService.add(travelGroup, travelItemIds);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    /**
     * 查询分页信息
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return travelGroupService.findPage(queryPageBean);
    }

    /**
     * 删除一条跟团游信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            travelGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }

    /**
     * 编辑界面需要回显的数据
     * 1. 跟团游信息 travelGroup
     * 2. 所有自由行信息 travelItemList
     * 3. 该跟团游对应的自由行的id
     *
     * @param id
     * @return
     */
    @RequestMapping("/findData")
    public Result findData(Integer id) {
        try {
            Map<String, Object> map = travelGroupService.findData(id);
            return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    /**
     * 修改跟团游信息
     *
     * @param travelItemIds
     * @param travelGroup
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup) {
        try {
            travelGroupService.edit(travelItemIds, travelGroup);
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }

    /**
     * 查询所有的跟团游信息
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<TravelGroup> travelGroupList =  travelGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

}
