package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.mapper.TravelGroupMapper;
import com.atguigu.meinian.mapper.TravelItemMapper;
import com.atguigu.meinian.pojo.TravelGroup;
import com.atguigu.meinian.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupMapper travelGroupMapper;

    @Autowired
    private TravelItemMapper travelItemMapper;

    /**
     * 添加一条跟团游信息
     *
     * @param travelGroup
     * @param travelItemIds
     * @throws Exception
     */
    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) throws Exception {
        // 1. 将数据添加到travelGroup表中
        travelGroupMapper.add(travelGroup);
        // 2. 将数据添加到中间表中
        travelGroupMapper.addTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    /**
     * 查询分页信息
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<TravelGroup> page = travelGroupMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除一条跟团游信息
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(Integer id) throws Exception {
        // 删除中间表数据
        travelGroupMapper.deleteTravelGroupAndTravelItem(id);
        // 删除跟团游表信息
        travelGroupMapper.deleteTravelGroupById(id);
    }

    /**
     * 编辑界面需要回显的数据
     * 1. 跟团游信息
     * 2. 所有自由行信息
     * 3. 该跟团游对应的自由行的id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findData(Integer id) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("travelGroup", travelGroupMapper.findById(id));
        map.put("travelItemList", travelItemMapper.findAll());
        map.put("travelGroupAndTravelItem", travelGroupMapper.findTravelGroupAndTravelItem(id));
        return map;
    }

    /**
     * 修改跟团游信息
     *
     * @param travelItemIds
     * @param travelGroup
     * @return
     */
    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) throws Exception {
        // 1. 修改跟团游基本信息
        travelGroupMapper.edit(travelGroup);
        // 2. 修改跟团游和自由行的中间表信息
        // 删除之前中间表的数据
        travelGroupMapper.deleteTravelGroupAndTravelItem(travelGroup.getId());
        // 添加新增的选中的信息
        travelGroupMapper.addTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    /**
     * 查询所有的跟团游信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<TravelGroup> findAll() throws Exception {
        return travelGroupMapper.findAll();
    }
}
