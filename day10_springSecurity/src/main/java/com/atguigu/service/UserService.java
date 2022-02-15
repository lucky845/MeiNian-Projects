package com.atguigu.service;

import com.atguigu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class UserService implements UserDetailsService {

    // 模拟数据库中的用户数据
    private static Map<String, User> map = new HashMap<>();

    static {
        map.put("admin", new User("admin", "$2a$10$RD.vSObCN115ExnhUxdsk.RAE6i2hPGJO7dCzCg4jH/E0Ovs00SAu", "111"));
        map.put("xiaoming", new User("xiaoming", "$2a$10$RD.vSObCN115ExnhUxdsk.RAE6i2hPGJO7dCzCg4jH/E0Ovs00SAu", "222"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟根据用户名查询数据库
        User user = map.get(username);

        if (user == null) {
            // 根据用户名没有查询到对应的用户，抛出异常，表示输入有误
            return null;
        }

        // 模拟数据库中的密码,后期需要查询数据库
        String passwordInDb = user.getPassword();
        // 授权，后期需要改为数据库查询获得用户的角色和权限
        Set<GrantedAuthority> set = new HashSet<>();
        set.add(new SimpleGrantedAuthority("add"));
        set.add(new SimpleGrantedAuthority("delete"));
        set.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 参数一： 存放登录名 参数二： 存放数据库中取出来的密码，参数三： 存放当前用户具有的所有权限的集合
        return new org.springframework.security.core.userdetails.User(username, passwordInDb, set);
    }
}
