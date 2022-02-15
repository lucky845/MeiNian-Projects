package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constants.MessageConstant;
import com.atguigu.meinian.constants.RedisConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.Setmeal;
import com.atguigu.meinian.service.SetmealService;
import com.atguigu.meinian.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传
     *
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            // 使用UUID生成随机文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + imgFile.getOriginalFilename();
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            // 将文件名存储到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            // 图片上传成功
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            // 图片上传失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 删除一条套餐游信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            setmealService.delete(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
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
            return setmealService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加一条套餐游信息
     *
     * @param setmeal
     * @param travelGroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] travelGroupIds) {
        try {
            setmealService.add(setmeal, travelGroupIds);
            // 将文件名存储到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            // 添加成功
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 添加失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 编辑套餐游回显的数据
     * 1. 套餐游信息 setmeal
     * 2. 所有跟团游信息 travelGroupList
     * 3. 该套餐游对应的跟团游id的集合 setmealAndTravelGroup
     *
     * @param id
     * @return
     */
    @RequestMapping("/findData")
    public Result findData(Integer id) {
        try {
            Map<String, Object> map = setmealService.findData(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 修改套餐游信息
     *
     * @param travelGroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(Integer[] travelGroupIds, @RequestBody Setmeal setmeal) {
        try {
            setmealService.edit(travelGroupIds, setmeal);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

}
