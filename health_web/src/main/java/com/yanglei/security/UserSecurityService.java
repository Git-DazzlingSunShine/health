package com.yanglei.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.pojo.Permission;
import com.yanglei.pojo.Role;
import com.yanglei.pojo.User;
import com.yanglei.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserSecurityService implements UserDetailsService {
    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户名
        User user = userService.queryUserByUserName(username);
        if (user == null) {
            return null;
        }
        Set<Role> roles = user.getRoles();
        //查询权限
        Set<Permission> permissions = user.getPermissions();
        List authorities = new ArrayList();
        permissions.stream().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getKeyword())));

        //返回security用户
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
