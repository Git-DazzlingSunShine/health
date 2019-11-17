package com.yanglei.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperationLog implements Serializable {
    private String operationName;
    private String orderType;
    private String operationDate;
    private String operationIp;

}
