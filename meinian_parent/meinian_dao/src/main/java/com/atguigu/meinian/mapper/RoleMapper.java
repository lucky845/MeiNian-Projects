package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleMapper {

    Set<Role> findRolesByUserId(@Param("userId") Integer userId);

}
