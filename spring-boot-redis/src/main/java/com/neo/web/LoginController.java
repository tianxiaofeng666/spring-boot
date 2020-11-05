package com.neo.web;

import com.alibaba.fastjson.JSONObject;
import com.neo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/user")
public class LoginController extends BaseController{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/login")
    public JSONObject login(String userName, String password){
        JSONObject obj = new JSONObject();
        User user=new User();
        user.setId((long)111);
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail("12@qq.com");
        if(user != null && userName.equals("xiaofeng") && password.equals("Iyunbao666")){
            session.setAttribute("loginUserId",user.getId());
            session.setMaxInactiveInterval(1800);
            //redisTemplate.opsForValue().set(user.getId(),session,1800L, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("loginUser:" + user.getId(),session.getId(),(long)1800,TimeUnit.SECONDS);
            obj.put("code",0);
            obj.put("content","登录成功");
            return obj;
        }else{
            obj.put("code",1);
            obj.put("content","用户名或密码错误");
            return obj;
        }
    }

    @RequestMapping("/getUserInfo")
    public JSONObject getUserInfo(long id){
        JSONObject obj = new JSONObject();
        User user=new User();
        user.setId((long)111);
        user.setUserName("xiaofeng");
        user.setPassword("Iyunbao666");
        user.setEmail("12@qq.com");
        obj.put("code",0);
        obj.put("content",user);
        return obj;
    }

}
