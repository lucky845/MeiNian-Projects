package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.mapper.AddressMapper;
import com.atguigu.meinian.pojo.Address;
import com.atguigu.meinian.service.AddressService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 显示公司地址信息
     *
     * @return
     */
    @Override
    public List<Address> findAllMaps() {
        return addressMapper.findAllMaps();
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
        Page<Address> page = addressMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增一个分公司地址
     *
     * @param address
     */
    @Override
    public void addAddress(Address address) {
        addressMapper.addAddress(address);
    }

    /**
     * 删除分公司地址成功
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        addressMapper.deleteById(id);
    }
}
