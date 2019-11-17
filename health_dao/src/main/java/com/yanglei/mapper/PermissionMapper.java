package com.yanglei.mapper;

import com.yanglei.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    List<String> queryPermissionKeyByUerName(@Param("userId") Integer userId);

    Set<Permission> queryPermissByRoleIds(@Param("roleIds") ArrayList<Integer> roleIds);
}
