package com.example.springbootredislock.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootredislock.service.LockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.*;

@RestController
public class RedisController {

    private static final Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LockService lockService;

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
    public Object getValue(@RequestBody JSONObject json){
        String key = json.getString("key");
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 加锁，自旋重试三次
     * @param json
     * @return
     */
    @RequestMapping("/lock")
    public boolean lock(@RequestBody JSONObject json){
        log.info("进来啦。。。" + json);
        String key = json.getString("key");
        String value = json.getString("value");
        long expireTime = json.getLong("expireTime");

        boolean locked = false;
        int tryCount = 3;
        while (!locked && tryCount >0) {
            locked = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            tryCount--;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return locked;
    }

    /**
     * 非原子解锁，不安全
     * get/delete不是原子操作，客户端A解锁，在执行del()之前，锁突然过期了，此时客户端B尝试加锁成功，然后客户端A再执行del()方法，则将客户端B的锁给解除了
     *
     *
     */
    @RequestMapping("/unlock")
    public boolean unlock(@RequestBody JSONObject json){
        String key = json.getString("key");
        String value = json.getString("value");
        if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
            return false;
        }
        if(value.equals(redisTemplate.opsForValue().get(key))){
            boolean releaseLock = redisTemplate.delete(key);
            return releaseLock;
        }
        return false;
    }

    /**
     * 使用lua脚本解锁
     *  Lua脚本被当做一个命令去执行，在此期间redis不会执行其他指令，直到lua脚本执行完成
     *
     */
    @RequestMapping("/unlockLua")
    public boolean unlockLua(@RequestBody JSONObject json){
        String key = json.getString("key");
        String value = json.getString("value");
        if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
            return false;
        }
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        //用于解锁的lua脚本位置
        redisScript.setLocation(new ClassPathResource("unlock.lua"));
        redisScript.setResultType(Long.class);
        long result = (long) redisTemplate.execute(redisScript, Arrays.asList(key), value);
        return result == 1L;
    }

    @RequestMapping("/test")
    public void test() throws ExecutionException, InterruptedException {
        //自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,60L,TimeUnit.SECONDS,new LinkedBlockingQueue<>(200));
        for (int i=1;i<=6;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    lockService.count();
                }
            });
        }


    }

}
