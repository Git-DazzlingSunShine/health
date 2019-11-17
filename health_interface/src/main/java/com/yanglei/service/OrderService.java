package com.yanglei.service;

import com.yanglei.entry.Result;
import com.yanglei.pojo.OrderInfoVo;

public interface OrderService {
    /**
     * 添加预约订单
     * @param orderInfoVo
     * @return
     */
    Result insertOrder(OrderInfoVo orderInfoVo);
}
