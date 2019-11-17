package com.yanglei.mobile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.content.MessageConstant;
import com.yanglei.content.RedisMessageConstant;
import com.yanglei.content.SmsContent;
import com.yanglei.entry.Result;
import com.yanglei.pojo.Order;
import com.yanglei.pojo.OrderInfoVo;
import com.yanglei.service.OrderService;
import com.yanglei.util.JedisUtil;
import com.yanglei.util.SmsUtil;
import com.yanglei.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    JedisUtil jedisUtil;
    @Reference
    private OrderService orderService;

    @RequestMapping("submit")
    public Result submit(@RequestBody OrderInfoVo orderInfoVo) {
        String telephone = orderInfoVo.getTelephone();//手机号
        String token = orderInfoVo.getToken();
        String validateCode = orderInfoVo.getValidateCode();//验证码

        if (token == null) {
            return new Result(false, SmsContent.TOKEN_LLEGAL);
        }
        //验证token的正确性,如果redis有这条数据，删除后返回的是1
        Long del = jedisUtil.del(token);
        if (del == 0) {
            return new Result(false, SmsContent.TOKEN_LLEGAL);
        }

        //验证码校验
        String key = RedisMessageConstant.SENDTYPE_ORDER + telephone;
        String code = jedisUtil.get(key);
        if (validateCode == null || !validateCode.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        orderInfoVo.setOrderType(Order.ORDERTYPE_WEIXIN);//预约渠道设置为微信
        //进行数据保存
        return orderService.insertOrder(orderInfoVo);
    }
}
