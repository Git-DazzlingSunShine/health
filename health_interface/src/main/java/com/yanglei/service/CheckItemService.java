package com.yanglei.service;

import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.pojo.CheckItem;

public interface CheckItemService {

    /**
     * 新增检查项目
     * @param checkItem 检查参数实体
     */
    public void add(CheckItem checkItem);

    /**
     * 分页查询检查项目
     * @param queryPageBean 查询参数实体
     * @return
     */
    public PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据编号查询用户数据
     * @param code 编号
     * @return
     */
    public CheckItem selectByCode(String code);

    /**
     * 删除数据
     * @param code 编号
     */
    public void delete(String code);

    /**
     * 根据编号更改用户信息
     * @return
     * @param checkItem
     */
    boolean updateByCode(CheckItem checkItem);

}
