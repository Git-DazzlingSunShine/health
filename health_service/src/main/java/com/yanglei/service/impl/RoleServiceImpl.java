package com.yanglei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.mapper.RoleMapper;
import com.yanglei.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

}
