package com.yanglei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.mapper.UserMapper;
import com.yanglei.pojo.User;
import com.yanglei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }
}
