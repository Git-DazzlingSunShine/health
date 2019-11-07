package com.yanglei.mapper;

import com.yanglei.entry.Result;
import com.yanglei.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {

    Integer queryCountByDate(@Param("orderDate") Date orderDate);

    Integer update(OrderSetting orderSetting);

    Integer add(OrderSetting orderSetting);

    List<OrderSetting> queryByMonth(@Param("begin") String begin, @Param("end") String end);

    Result setNumberByBatch(MultipartFile excelFile);
}
