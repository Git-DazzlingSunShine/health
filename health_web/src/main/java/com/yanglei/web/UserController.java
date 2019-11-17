package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.pojo.User;
import com.yanglei.service.MenuService;
import com.yanglei.service.UserService;
import com.yanglei.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *用户数据初始化
 *TODO
 *
 * @Author:Yang.Lei
 *
 * @Date:${DATE}:${TIME}
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;
    @Reference
    private MenuService menuService;
    @Autowired
    private JedisUtil jedisUtil;


    @RequestMapping("users")
    public List<User> selectAllUser() {
        return userService.selectAllUser();
    }

    /**
     * 根据用户token查询用户信息，若没有token就根据session验证并生成token，并返回
     * @param token 用户标示
     * @return 用户数据对象
     */
    @RequestMapping("queryUser")
    public Result queryUser(@RequestHeader(value = "token", required = false) String token) {
        try {
            String obj = jedisUtil.get(token);

            User redisUser = null;
            try {
                redisUser = JSON.parseObject(obj, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (redisUser != null) {
                return new Result(true, MessageConstant.LOGIN_WELCOME + redisUser.getUsername(), redisUser.getUsername());
            }
            //若用户没有token，就根据session判断后，并查出用户数据，生成token，存入redis
            Map<String, String> map = new HashMap<>();
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            redisUser = userService.queryUserByUserName(user.getUsername());
            token = UUID.randomUUID().toString();
            String setex = jedisUtil.setex(token, 60 * 50, JSON.toJSONString(redisUser));
            map.put("username", redisUser.getUsername());
            map.put("token", token);
            return new Result(true, MessageConstant.LOGIN_WELCOME + redisUser.getUsername(), map);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("queryAllMenu")
    public Result queryAllMenu(@RequestHeader(value = "token", required = false) String token) {
        String query = jedisUtil.get(token);
        User user = JSON.parseObject(query, User.class);
        if (user == null) {
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
        return menuService.queryAllMenu(user);
    }
}
