package com.yanglei.web;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("report")
public class ReportController {
    @Reference
    ReportService reportService;

    @RequestMapping("getMemberReport")
    public Result getMemberReport() {
        //获取过去一年月份
        DateTime dateTime = DateUtil.offsetDay(new Date(), -12);

        List<String> months = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DateTime dateTime1 = DateUtil.offsetDay(dateTime, i);
            months.add(dateTime.toString("yyyy-MM"));
        }

        List<Integer> memberCount = reportService.queryMemberReport(months);
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, memberCount);
    }
}
