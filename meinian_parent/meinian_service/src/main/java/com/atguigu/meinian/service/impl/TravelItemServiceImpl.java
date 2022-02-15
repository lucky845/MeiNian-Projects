package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.mapper.TravelItemMapper;
import com.atguigu.meinian.pojo.TravelItem;
import com.atguigu.meinian.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    private TravelItemMapper travelItemMapper;

    @Override
    public void add(TravelItem travelItem) throws Exception {
        travelItemMapper.add(travelItem);
    }

    @Override
    public PageResult findTravelItemByQueryString(QueryPageBean queryPageBean) throws Exception {
        // 不使用分页插件PageHelper
        // 至少写2条sql语句完成查询
        // 第1条，select count(*) from t_travelitem，查询的结果封装到PageResult中的total
        // 第2条，select * from t_travelitem where NAME = '001' OR CODE = '001' limit ?,?
        //（0,10）（10,10）(（currentPage-1)*pageSize,pageSize）
        // 使用分页插件PageHelper（简化上面的操作）
        // 1：初始化分页操作
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 2：使用sql语句进行查询（不必在使用mysql的limit了）
        Page<TravelItem> page = travelItemMapper.findTravelItemByQueryString(queryPageBean.getQueryString());
        // 3：封装
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) throws Exception {
        travelItemMapper.delete(id);
    }

    @Override
    public TravelItem findById(Integer id) throws Exception {
        return travelItemMapper.findById(id);
    }

    @Override
    public void update(TravelItem travelItem) throws Exception {
        travelItemMapper.update(travelItem);
    }

    @Override
    public List<TravelItem> findAll() throws Exception {
        return travelItemMapper.findAll();
    }
}
