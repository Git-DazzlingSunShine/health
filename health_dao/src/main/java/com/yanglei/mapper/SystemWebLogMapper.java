package com.yanglei.mapper;


import com.yanglei.pojo.OperationLog;

import java.util.List;

public interface SystemWebLogMapper {

    //记录日志
    void logging(OperationLog operationLog);

    List<OperationLog> queryLog();
}
