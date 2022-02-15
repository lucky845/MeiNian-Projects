package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.Address;
import com.atguigu.meinian.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    /**
     * 显示公司地址信息
     *
     * @return
     */
    @RequestMapping("/findAllMaps")
    public List<Address> findAllMaps() {
        return addressService.findAllMaps();
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return addressService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增一个地址
     *
     * @param address
     * @return
     */
    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address) {
        addressService.addAddress(address);
        return new Result(true, "新增分公司地址成功");
    }

    /**
     * 删除一条地址信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        addressService.deleteById(id);
        return new Result(true, "删除分公司地址成功");
    }

}
