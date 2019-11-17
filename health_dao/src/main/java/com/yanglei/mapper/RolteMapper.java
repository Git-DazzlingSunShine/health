package com.yanglei.mapper;

import com.yanglei.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolteMapper {

    List<Integer> queryMenuIdByUserId(@Param("userId") Integer userId);


    List<Role> queryPermissByUserId(@Param("userId") Integer userId);
}
