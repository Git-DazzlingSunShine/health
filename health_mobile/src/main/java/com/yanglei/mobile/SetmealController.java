package com.yanglei.mobile;

import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.entry.Result;
import com.yanglei.pojo.Setmeal;
import com.yanglei.service.SetmealService;
import com.yanglei.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisUtil jedisUtil;

    @RequestMapping("getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> setmeals = setmealService.getSetmeal();
        return new Result(true, "", setmeals);
    }

    @RequestMapping("findById")
    public Result findById(Integer id) {
        Setmeal setmeal = setmealService.findByIdStream(id);

        return new Result(true, "", setmeal);
    }

    @RequestMapping("onlyQuerySetmealById")
    public Result onlyQuerySetmealById(Integer id) {
        Setmeal setmeal = setmealService.onlyQuerySermealById(id);
        return new Result(true, "", setmeal);
    }

    @RequestMapping("getToken")
    public Result getToken() {
        String token = UUID.randomUUID().toString();
        jedisUtil.setex(token, 60 * 30, token);
        return new Result(true, "", token);
    }

}
