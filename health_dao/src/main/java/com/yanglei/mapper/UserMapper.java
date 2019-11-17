package com.yanglei.mapper;

import com.yanglei.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    public  User queryUserByUserName(@Param("username") String username);

    public List<User> selectAllUser();
}
