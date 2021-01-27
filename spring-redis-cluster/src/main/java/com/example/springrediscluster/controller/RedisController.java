package com.example.springrediscluster.controller;

import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/setValue")
    public String setValue(@RequestBody JSONObject json){
        String uuid = UUID.randomUUID().toString();
        String key = json.getString("key");
        String value = json.getString("value");
        long expireTime = json.getLong("expireTime");
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        return uuid;
    }

    @RequestMapping("/getValue")
    public String getValue(@RequestBody JSONObject json){
        String key = json.getString("key");
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }
}

