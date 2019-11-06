package com.yanglei.service;

import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.pojo.CheckGroup;

public interface CheckGroupService {

    /**
     * 添加分组数据
     * @param checkGroup
     * @return
     */
    public Result add(CheckGroup checkGroup);

    /**
     * 分页查询分组
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 查询全部分组信息
     * @param id
     * @return
     */
    Result findByIdUpDate(Integer id);

    /**
     * 更新检查组信息
     * @param checkGroup
     * @return
     */
    Result updateGroup(CheckGroup checkGroup);

    Result deleteGroupById(Integer id);

    /**
     * 查询全部分组数据
     * @return
     */
    Result queryAll();
}
