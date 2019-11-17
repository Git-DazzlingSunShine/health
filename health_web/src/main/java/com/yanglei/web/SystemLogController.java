package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.service.SystemWebLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("systemlog")
public class SystemLogController {
    @Reference
    SystemWebLogService systemWebLogService;

    @RequestMapping("queryLog")
    @PreAuthorize("hasAuthority('SYSTEMLOG_QUERY')")
    public PageResult queryLog(@RequestBody QueryPageBean queryPageBean) {
        return systemWebLogService.queryLog(queryPageBean);
    }
}
