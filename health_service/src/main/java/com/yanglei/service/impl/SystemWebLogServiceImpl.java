package com.yanglei.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.mapper.SystemWebLogMapper;
import com.yanglei.pojo.OperationLog;
import com.yanglei.service.SystemWebLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SystemWebLogServiceImpl implements SystemWebLogService {
    @Autowired
    SystemWebLogMapper systemWebLogMapper;

    @Override
    public void logging(OperationLog operationLog) {
        systemWebLogMapper.logging(operationLog);
    }

    @Override
    public PageResult queryLog(QueryPageBean queryPageBean) {
        /*分页查询*/
        Page<Object> pages = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<OperationLog> operationLogs = systemWebLogMapper.queryLog();


        return new PageResult(pages.getTotal(), operationLogs);
    }
}
