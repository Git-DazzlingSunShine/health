package com.yanglei.service;

import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.pojo.Setmeal;

public interface SetmealService {

    /**
     * 添加套餐
     * @param setmeal
     * @return
     */
    Result add(Setmeal setmeal);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult queryPage(QueryPageBean queryPageBean);

    Result delete(Integer id);

    Result update(Setmeal setmeal);

    Result querySetmealById(Integer id);

}
