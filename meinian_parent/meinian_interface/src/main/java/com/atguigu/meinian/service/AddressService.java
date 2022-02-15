package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.pojo.Address;

import java.util.List;

public interface AddressService {


    List<Address> findAllMaps();

    PageResult findPage(QueryPageBean queryPageBean);

    void addAddress(Address address);

    void deleteById(Integer id);
}
