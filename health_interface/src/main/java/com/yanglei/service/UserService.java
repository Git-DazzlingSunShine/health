package com.yanglei.service;

import com.yanglei.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> selectAllUser();

    User queryUserByUserName(String username);
}
