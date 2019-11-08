package com.yanglei.mobile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.entry.Result;
import com.yanglei.pojo.Setmeal;
import com.yanglei.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

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
}
