package com.yanglei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.mapper.CheckItemMapper;
import com.yanglei.pojo.CheckItem;
import com.yanglei.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件
        Page<Object> pages = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<CheckItem> checkItems = checkItemMapper.findPage(queryPageBean.getQueryString());

        return new PageResult(pages.getTotal(), checkItems);
    }

    @Override
    public CheckItem selectByCode(String code) {
        return checkItemMapper.selectByCode(code);
    }

    @Override
    public void delete(String code) {
        checkItemMapper.delete(code);
    }

    @Override
    public boolean updateByCode(CheckItem checkItem) {
        int i = checkItemMapper.updateByCode(checkItem);
        return i==1;
    }
}
