package com.yanglei.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class OrderInfoVo implements Serializable {
    private String idCard;//身份证
    private String name;//名称
    private Date orderDate;//预约日期
    private Integer setmealId;//套餐id
    private Integer sex;//性别
    private String telephone;//手机号码
    private String validateCode;//验证码
    private String token;//唯一表示
    private String orderType;//预约渠道
}
