package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 通过用户名查询用户信息
     *
     * @param username
     * @return
     */
    User findUserByUsername(@Param("username") String username);
}
