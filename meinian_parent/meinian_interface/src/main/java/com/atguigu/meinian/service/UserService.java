package com.atguigu.meinian.service;

import com.atguigu.meinian.pojo.User;

public interface UserService {


    User findUserByUsername(String username);
}
