package com.yanglei.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.mapper.OrderSettingMapper;
import com.yanglei.pojo.OrderSetting;
import com.yanglei.service.OrderSettingService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public Result setNumberByDate(OrderSetting orderSetting) {
        try {
            Integer count = orderSettingMapper.queryCountByDate(orderSetting.getOrderDate());
            if (count > 0) {
                orderSettingMapper.update(orderSetting);
            } else {
                orderSettingMapper.add(orderSetting);
            }
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @Override
    public Result queryByMonth(String date) {
        String star = date + "-1";
        String end = date + "-31";
        try {

            List<OrderSetting> orderSettings = orderSettingMapper.queryByMonth(star, end);
            if (orderSettings == null) {
                return new Result(false, "暂未设置预约人数");
            }
            List<Map> result = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(orderSettings)) {
                for (OrderSetting orderSetting : orderSettings) {
                    //     {"date":22,"mouth":8,"number":300,"reservations":300,},
                    Map map = new HashMap<>();
                    Date orderDate = orderSetting.getOrderDate();
                    map.put("date", orderDate.getDate());
                    map.put("mouth", orderDate.getMonth());
                    map.put("number", orderSetting.getNumber());
                    map.put("reservations", orderSetting.getReservations());
                    result.add(map);
                }
            }
            return new Result(true, "", result);
        } catch (Exception e) {
            return new Result(false, MessageConstant.PARAM_FORMAT_ERR);
        }
    }

    @Override
    public Result setNumberByBatch(List<String[]> datas) {
        List<OrderSetting> orderSettings = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(datas)) {
            for (String[] data : datas) {
                if (data.length != 2) {
                    continue;
                }
                DateTime orderDate = null;
                Integer number = null;
                try {
                    orderDate = DateUtil.parse(data[0], "yy/MM/dd");
                    number = Integer.parseInt(data[1]);
                } catch (Exception e) {
                    return new Result(false, MessageConstant.PARAM_FORMAT_ERR);
                }
                OrderSetting orderSetting = new OrderSetting(orderDate, number);
                orderSettings.add(orderSetting);
            }
        }
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        List<List<OrderSetting>> partition = Lists.partition(orderSettings, 30);

        for (List<OrderSetting> settings : partition) {
            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    for (OrderSetting setting : settings) {
                        setNumberByDate(setting);
                    }
                    return ("线程" + Thread.currentThread().getName() + "执行完毕");
                }
            };

            Future submit = fixedThreadPool.submit(callable);

            try {
                System.out.println(submit.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

    }

}
