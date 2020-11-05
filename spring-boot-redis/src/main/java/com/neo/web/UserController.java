package com.neo.web;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.model.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 使用Jedis来操作redis
     * @param json
     * @return
     */
    @RequestMapping("/setRedisValueNew")
    public String setRedisValueNew(@RequestBody JSONObject json){
        String key = json.getString("key");
        String value = json.getString("value");
        Long time = json.getLong("time");
        Jedis jedis = jedisPool.getResource();
        /*boolean keyExist = jedis.exists(key);
        if(keyExist){
            String res = jedis.get(key);
            return value;
        }*/
        //jedis.del(key);
        String a = jedis.set(key, value, "NX", "EX", time);
        return a;
    }

    /**
     * 使用StringRedisTemplate来操作redis,key/value都为字符串类型
     * @param json
     * @return
     */
    @RequestMapping("/setRedisValue")
    public String setRedisValue(@RequestBody JSONObject json){
        String key = json.getString("key");
        String value = json.getString("value");
        Long time = json.getLong("time");
        stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
        //stringRedisTemplate.delete(key);
        return "缓存设置成功。。。";
    }

    @RequestMapping("/getRedisValueString")
    public String getRedisValueString(@RequestBody JSONObject json){
        String key = json.getString("key");
        String value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 使用RedisTemplate来操作redis,value是复杂的对象类型，取出的时候不做任何的数据转换，直接从redis里面取出一个对象
     * @param json
     * @return
     */
    @RequestMapping("/setRedisValue11")
    public String setRedisValue11(@RequestBody JSONObject json){
        String key = json.getString("key");
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        Long time = json.getLong("time");
        redisTemplate.opsForValue().set(key,user,time,TimeUnit.SECONDS);
        return "缓存设置成功。。。";
    }

    @RequestMapping("/getRedisValue")
    public JSONObject getRedisValue(@RequestBody JSONObject json){
        JSONObject res = new JSONObject();
        String key = json.getString("key");
        User value = (User)redisTemplate.opsForValue().get(key);
        res.put("result","success");
        res.put("content",value);
        return res;
    }

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public User getUser() {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }


    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

    @RequestMapping("/getLock")
    public boolean getLock(@RequestBody JSONObject json){
        logger.info("进来了。。：" + json);
        String key = json.getString("key");
        String value = json.getString("value");
        Long time = json.getLong("time");
        Jedis jedis = jedisPool.getResource();
        String a = jedis.set(key, value, "NX", "EX", time);
        if("OK".equals(a)){
            return true;
        }
        return false;
    }
}