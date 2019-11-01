package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.pojo.User;
import com.yanglei.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("users")
    public List<User> selectAllUser() {
        return userService.selectAllUser();
    }
}
