package com.yanglei.mapper;


import org.apache.ibatis.annotations.Param;

public interface ReportMapper {

    Integer queryMemberReport(@Param("month") String month);
}
