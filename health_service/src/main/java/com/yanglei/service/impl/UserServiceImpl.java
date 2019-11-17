package com.yanglei.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.mapper.PermissionMapper;
import com.yanglei.mapper.RolteMapper;
import com.yanglei.mapper.UserMapper;
import com.yanglei.pojo.Permission;
import com.yanglei.pojo.Role;
import com.yanglei.pojo.User;
import com.yanglei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolteMapper rolteMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public User queryUserByUserName(String username) {
        User user = userMapper.queryUserByUserName(username);
        if (null != user) {
            List<Role> roles = rolteMapper.queryPermissByUserId(user.getId());
            if (CollectionUtil.isNotEmpty(roles)) {
                ArrayList<Integer> roleIds = CollectionUtil.newArrayList();
                roles.stream().forEach(role -> roleIds.add(role.getId()));
                Set<Permission> permissions = permissionMapper.queryPermissByRoleIds(roleIds);
                user.setPermissions(permissions);
            }
        }

        return user;
    }
}
