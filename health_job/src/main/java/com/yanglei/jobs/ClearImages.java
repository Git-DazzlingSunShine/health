package com.yanglei.jobs;

import com.yanglei.content.RedisConstant;
import com.yanglei.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Controller("clearImages")
public class ClearImages {
    @Autowired
    private JedisPool jedisPool;

    public void run() {
        Set<String> needDeleteImg = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String img : needDeleteImg) {
            //删除七牛
            QiniuUtil.delete(img);
            //删除缓存中的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,img);
        }
    }
}
