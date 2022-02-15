package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.mapper.UserMapper;
import com.atguigu.meinian.pojo.User;
import com.atguigu.meinian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
