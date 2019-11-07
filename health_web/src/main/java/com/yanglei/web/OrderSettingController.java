package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.pojo.OrderSetting;
import com.yanglei.service.OrderSettingService;
import com.yanglei.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("setNumberByDate")
    public Result setNumberByDate(@RequestBody OrderSetting orderSetting) {
        return orderSettingService.setNumberByDate(orderSetting);
    }

    @RequestMapping("queryByMonth")
    public Result queryByMonth(String formatDate) {
        return orderSettingService.queryByMonth(formatDate);
    }

    @RequestMapping("upload")
    public Result upload(MultipartFile excelFile) {
        try {
            List<String[]> datas = POIUtils.readExcel(excelFile);
            return orderSettingService.setNumberByBatch(datas);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }

    }
}
