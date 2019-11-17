package com.yanglei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.mapper.ReportMapper;
import com.yanglei.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportMapper reportMapper;

    @Override
    public List<Integer> queryMemberReport(List<String> months) {
        List<Integer> number = new ArrayList<>();
        for (String month : months) {
            Integer count = reportMapper.queryMemberReport(month);
            number.add(count);
        }

        return number;
    }
}
