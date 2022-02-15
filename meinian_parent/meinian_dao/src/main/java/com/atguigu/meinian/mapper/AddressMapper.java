package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Address;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    /**
     * 获取所有公司地址
     *
     * @return
     */
    List<Address> findAllMaps();

    /**
     * 查询分页信息
     *
     * @param queryString
     * @return
     */
    Page<Address> findPage(@Param("queryString") String queryString);

    /**
     * 新增一个分公司地址
     *
     * @param address
     */
    void addAddress(Address address);

    /**
     * 删除一条分公司信息
     *
     * @param id
     */
    void deleteById(@Param("id") Integer id);
}
