package com.yanglei.service;

import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.pojo.OperationLog;

public interface SystemWebLogService {
    void logging(OperationLog operationLog);

    PageResult queryLog(QueryPageBean queryPageBean);

}
