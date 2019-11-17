package com.yanglei.mapper;

import com.github.pagehelper.Page;
import com.yanglei.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    public List<Member> findAll();

    public Page<Member> selectByCondition(String queryString);

    public void add(Member member);

    public void deleteById(Integer id);

    public Member findById(Integer id);

    public Member findByTelephone(@Param("telephone") String telephone);

    public void edit(Member member);

    public Integer findMemberCountBeforeDate(String date);

    public Integer findMemberCountByDate(String date);

    public Integer findMemberCountAfterDate(String date);

    public Integer findMemberTotalCount();
}
