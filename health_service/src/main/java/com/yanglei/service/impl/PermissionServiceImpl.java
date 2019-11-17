package com.yanglei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.mapper.PermissionMapper;
import com.yanglei.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<String> queryPermissionKeyByUerName(Integer userId) {
        return permissionMapper.queryPermissionKeyByUerName(userId);
    }
}
