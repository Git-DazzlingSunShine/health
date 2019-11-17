package com.yanglei.mapper;

import com.yanglei.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    //根据菜单ids查询菜单
    List<Menu> queryMenuByMenuIds(@Param("menuIds") List<Integer> menuIds);
}
