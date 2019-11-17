package com.yanglei.mobile;

import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yanglei.content.MessageConstant;
import com.yanglei.content.RedisMessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.pojo.LoginInfoVo;
import com.yanglei.pojo.Member;
import com.yanglei.service.MemberService;
import com.yanglei.util.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    JedisUtil jedisUtil;
    @Reference
    MemberService memberService;

    @RequestMapping("login")
    public Result login(@RequestBody LoginInfoVo loginInfoVo) {
        String telephone = loginInfoVo.getTelephone();
        String code = loginInfoVo.getValidateCode();

        //第一步：从redis取出验证码
        String key = RedisMessageConstant.SENDTYPE_LOGIN + telephone;
        String queryCode = jedisUtil.get(key);

        //第二步：校验验证码
        if (! StringUtils.equals(queryCode,code)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //第三步：根据手机查询是否是会员
        Member member = memberService.queryMemberByTelephone(telephone);
        //第四步：不是会员就进行注册
        if (member == null) {
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
        }
        //第五步：使用UUID作为token，将用户数据保存到Redis
        String token = UUID.randomUUID().toString();
        String setex = jedisUtil.setex(token, 60 * 50, JSON.toJSONString(member));
        if (setex == null) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        return new Result(true, MessageConstant.LOGIN_SUCCESS, token);
    }

}
