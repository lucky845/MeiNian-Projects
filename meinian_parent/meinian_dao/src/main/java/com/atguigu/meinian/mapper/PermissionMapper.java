package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PermissionMapper {

    Set<Permission> findPermissionsByRoleId(@Param("roleId") Integer roleId);

}
