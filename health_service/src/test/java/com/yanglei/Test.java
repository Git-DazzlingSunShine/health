package com.yanglei;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.pojo.User;
import com.yanglei.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class Test {

    @Autowired
    private UserService userService;

    @org.junit.Test
    public void test() {
        List<User> users = userService.selectAllUser();
        System.out.println("users = " + users);
    }

}
