package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.mapper.SetmealMapper;
import com.atguigu.meinian.mapper.TravelGroupMapper;
import com.atguigu.meinian.pojo.Setmeal;
import com.atguigu.meinian.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private TravelGroupMapper travelGroupMapper;

    /**
     * 添加一条套餐游
     *
     * @param setmeal
     * @param travelGroupIds
     * @throws Exception
     */
    @Override
    public void add(Setmeal setmeal, Integer[] travelGroupIds) throws Exception {
        // 添加套餐游信息
        setmealMapper.addSetmeal(setmeal);
        // 添加套餐游对应的中间表信息
        setmealMapper.addSetmealAndTravelGroup(setmeal.getId(), travelGroupIds);
    }

    /**
     * 查询分页信息
     *
     * @param queryPageBean
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) throws Exception {
        // 开启分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 分页根据条件查询
        Page<Setmeal> page = setmealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 编辑套餐游回显的数据
     * 1. 套餐游信息
     * 2. 所有跟团游信息
     * 3. 该套餐游对应的跟团游id的集合
     *
     * @param setmealId
     * @return
     */
    @Override
    public Map<String, Object> findData(Integer setmealId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 1. 查询套餐游信息
        map.put("setmeal", setmealMapper.findById(setmealId));
        // 2. 查询所有跟团游信息
        map.put("travelGroupList", travelGroupMapper.findAll());
        // 3. 查询该套餐游对应的跟团游信息
        map.put("setmealAndTravelGroup", setmealMapper.findTravelGroupIdBySetmealId(setmealId));
        return map;
    }

    /**
     * 修改套餐游信息
     *
     * @param travelGroupIds
     * @param setmeal
     * @throws Exception
     */
    @Override
    public void edit(Integer[] travelGroupIds, Setmeal setmeal) throws Exception {
        // 修改套餐信息
        setmealMapper.editSetmeal(setmeal);
        // 清空中间表信息
        setmealMapper.deleteSetmealAndTravelGroup(setmeal.getId());
        // 添加新的信息
        setmealMapper.addSetmealAndTravelGroup(setmeal.getId(), travelGroupIds);
    }

    /**
     * 删除自由行信息
     *
     * @param setmealId
     * @throws Exception
     */
    @Override
    public void delete(Integer setmealId) throws Exception {
        // 删除中间表信息
        setmealMapper.deleteSetmealAndTravelGroup(setmealId);
        // 删除套餐游信息
        setmealMapper.deleteSetmeal(setmealId);
    }

    /**
     * 查询所有的套餐列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Setmeal> findAll() throws Exception {
        return setmealMapper.findAll();
    }

    /**
     * 根据id查询对应的套餐信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Setmeal findById(Integer id) throws Exception {
        return setmealMapper.findSetmealById(id);
    }

    /**
     * 获取套餐统计饼状图数据
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealMapper.findSetmealCount();
    }
}
