package com.yanglei.mapper;

import com.yanglei.pojo.CheckGroup;
import com.yanglei.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {

    /**
     * 添加用户信息
     *
     * @return
     */
    public int add(CheckGroup checkGroup);

    /**
     * 在中建表中插入检查组和检查项的关系
     *
     * @param params
     */
    int setCheckItemAndCheckGroupRelationBatch(List<Map> params);

    List<CheckGroup> findPage(@Param("word") String word);

    CheckGroup findGroupById(@Param("id") Integer id);

    List<Integer> findCheckItemsByGroupId(@Param("id") Integer id);

    void updataGroup(CheckGroup checkGroup);

    CheckGroup queryGroupByCode(@Param("code") String code);

    void deleteCheckItemAndCheckGroupRelationBatch(@Param("groupId") Integer groupId);

    void deleteGroupById(@Param("id") Integer id);

}
