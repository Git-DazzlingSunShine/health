package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.content.MessageConstant;
import com.yanglei.content.RedisConstant;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.pojo.Setmeal;
import com.yanglei.service.SetmealService;
import com.yanglei.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("setmeal")
@RestController
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("upload")
    public Result upload(MultipartFile imgFile) {
        //文件原始名字
        try {
            String originalFilename = imgFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();

            //最后一个点的位置
            int index = originalFilename.lastIndexOf(".");

            //文件后缀名
            String suffix = originalFilename.substring(index);

            //生成引得名字
            String newName = uuid + suffix;
            //调用工具类七牛上传
            QiniuUtil.upload(imgFile.getBytes(), newName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal) {
        return setmealService.add(setmeal);
    }

    @RequestMapping("queryPage")
    public PageResult queryPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.queryPage(queryPageBean);
    }

    @RequestMapping("delete")
    public Result delete(@RequestParam("id") Integer id) {
        return setmealService.delete(id);
    }

    @RequestMapping("update")
    public Result update(@RequestBody Setmeal setmeal) {
        return setmealService.update(setmeal);
    }

    @RequestMapping("querySetmeal")
    public Result querSetmeal(Integer id) {
        return setmealService.querySetmealById(id);
    }
}
