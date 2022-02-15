package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {

    /**
     * 添加一条套餐游数据
     *
     * @param setmeal
     * @throws Exception
     */
    void addSetmeal(Setmeal setmeal) throws Exception;

    /**
     * 添加中间表数据
     *
     * @param setmealId
     * @param travelGroupIds
     * @throws Exception
     */
    void addSetmealAndTravelGroup(@Param("setmealId") Integer setmealId, @Param("travelGroupIds") Integer[] travelGroupIds) throws Exception;

    /**
     * 分页查询所有套餐游列表
     *
     * @param queryString
     * @return
     * @throws Exception
     */
    Page<Setmeal> findPage(@Param("queryString") String queryString) throws Exception;

    /**
     * 查询所有跟团游信息
     *
     * @param setmealId
     * @return
     * @throws Exception
     */
    Setmeal findById(@Param("setmealId") Integer setmealId) throws Exception;

    /**
     * 查询该套餐游对应的跟团游信息
     *
     * @param setmealId
     * @return
     * @throws Exception
     */
    List<Integer> findTravelGroupIdBySetmealId(@Param("setmealId") Integer setmealId) throws Exception;

    /**
     * 修改套餐游信息
     *
     * @param setmeal
     * @throws Exception
     */
    void editSetmeal(Setmeal setmeal) throws Exception;

    /**
     * 删除中间表信息
     *
     * @param setmealId
     * @throws Exception
     */
    void deleteSetmealAndTravelGroup(@Param("setmealId") Integer setmealId) throws Exception;

    /**
     * 删除套餐游信息
     *
     * @param setmealId
     * @throws Exception
     */
    void deleteSetmeal(@Param("setmealId") Integer setmealId) throws Exception;

    /**
     * 查询所有套餐列表
     *
     * @return
     * @throws Exception
     */
    List<Setmeal> findAll() throws Exception;

    /**
     * 根据id查询对应的套餐信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    Setmeal findSetmealById(@Param("id") Integer id) throws Exception;

    /**
     * 获取套餐统计饼状图数据
     *
     * @return
     */
    List<Map<String, Object>> findSetmealCount();

}
