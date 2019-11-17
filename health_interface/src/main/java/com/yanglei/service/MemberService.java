package com.yanglei.service;

import com.yanglei.pojo.Member;

public interface MemberService {

    Member queryMemberByTelephone(String telephone);
}
