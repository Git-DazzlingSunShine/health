package com.yanglei.service;

import com.yanglei.entry.Result;
import com.yanglei.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 预约信息后台管业务
 * Author:YL
 */
public interface OrderSettingService {

    public Result setNumberByDate(OrderSetting orderSetting);

    Result queryByMonth(String date);

    Result setNumberByBatch(List<String[]> datas);
}
