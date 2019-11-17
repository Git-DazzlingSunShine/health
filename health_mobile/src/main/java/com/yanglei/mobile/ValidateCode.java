package com.yanglei.mobile;

import com.yanglei.content.MessageConstant;
import com.yanglei.content.RedisMessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.util.JedisUtil;
import com.yanglei.util.SmsUtil;
import com.yanglei.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validateCode")
public class ValidateCode {
    @Autowired
    JedisUtil jedisUtil;

    //预约短信验证
    @RequestMapping("send4Order")
    public Result send4Order(String telephone) {
        if (telephone == null) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //生成key
        String key = RedisMessageConstant.SENDTYPE_ORDER + telephone;
        Integer code = ValidateCodeUtils.generateValidateCode(4);//验证码
        //发送短信
        SmsUtil.sendSmsCode(telephone, String.valueOf(code));
        jedisUtil.setex(key, 60 * 500, String.valueOf(code));

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //登录/注册短信验证

    @RequestMapping("send4Login")
    public Result send4Login(String telephone) {
        //第一步：生成4位验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        //第二步：调用短信用具类，发送短信
        SmsUtil.sendSmsCode(telephone, String.valueOf(code));

        //第三步：验证码存入Redis
        String key = RedisMessageConstant.SENDTYPE_LOGIN + telephone;
        jedisUtil.setex(key, 60 * 500, String.valueOf(code));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
