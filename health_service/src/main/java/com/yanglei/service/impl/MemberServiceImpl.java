package com.yanglei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.mapper.MemberMapper;
import com.yanglei.pojo.Member;
import com.yanglei.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member queryMemberByTelephone(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }
}
