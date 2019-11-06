package com.yanglei.service;


import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.exception.ForeignAssociationException;
import com.yanglei.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    /**
     * 新增检查项目
     *
     * @param checkItem 检查参数实体
     */
    public void add(CheckItem checkItem);

    /**
     * 分页查询检查项目
     *
     * @param queryPageBean 查询参数实体
     * @return
     */
    public PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据编号查询用户数据
     *
     * @param code 编号
     * @return
     */
    public CheckItem selectByCode(String code);

    /**
     * 删除数据
     *
     * @param id 主键
     */
    public boolean delete(Integer id) throws ForeignAssociationException;

    /**
     * 根据编号更改用户信息
     *
     * @param checkItem
     * @return
     */
    boolean updateByCode(CheckItem checkItem);

    /**
     * 查询全部的检查项目
     * @return 检查项目集合
     */
    List<CheckItem> findAll();
}
