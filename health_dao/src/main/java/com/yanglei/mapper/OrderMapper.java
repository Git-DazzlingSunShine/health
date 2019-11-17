package com.yanglei.mapper;

import com.yanglei.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    public void add(Order order);

    public List<Order> findByCondition(Order order);

    public Map findById4Detail(@Param("id") Integer id);

    public Integer findOrderCountByDate(String date);

    public Integer findOrderCountAfterDate(String date);

    public Integer findVisitsCountByDate(String date);

    public Integer findVisitsCountAfterDate(String date);

    public List<Map> findHotSetmeal();
}
