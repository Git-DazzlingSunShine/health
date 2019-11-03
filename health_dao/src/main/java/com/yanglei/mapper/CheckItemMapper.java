package com.yanglei.mapper;

import com.yanglei.pojo.CheckItem;

import java.util.List;

public interface CheckItemMapper {

    public void add(CheckItem checkItem);

    public List<CheckItem> findPage(String queryString);

    CheckItem selectByCode(String code);

    public void delete(String code);

    int updateByCode(CheckItem checkItem);
}
