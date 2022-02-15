package com.atguigu.meinian.Security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.pojo.Permission;
import com.atguigu.meinian.pojo.Role;
import com.atguigu.meinian.pojo.User;
import com.atguigu.meinian.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 远程调用用户服务，根据用户名查询用户信息
        User user = userService.findUserByUsername(username);
        // 判断用户是否存在
        if (user == null) {
            return null;
        }
        Set<GrantedAuthority> set = new HashSet<>();
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                // 授权
                set.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        // 1. 指定用户名 2. 指定密码 3. 传递授予的角色和权限
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), set);
    }
}
