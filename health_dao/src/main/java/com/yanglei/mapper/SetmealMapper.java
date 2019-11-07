package com.yanglei.mapper;

import com.yanglei.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {

    int add(Setmeal setmeal);

    int setSetmealAndCheckGroupRelationBatch(List<Map> params);

    List<Setmeal> queryAll(@Param("queryString") String queryString);

    int deleteSetmealAndCheckGroupRelation(@Param("setmealId") Integer setmealId);

    int deletSetmealByid(@Param("id") Integer id);

    int updateSetmeal(Setmeal setmeal);

    Setmeal querySetmealById(@Param("id") Integer id);

    List<Integer> querySetmealAndCheckGroupRelation(@Param("setmealId") Integer setmealId);
}
