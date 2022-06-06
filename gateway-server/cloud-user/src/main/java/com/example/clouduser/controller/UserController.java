package com.example.clouduser.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.example.clouduser.pojo.User;
import com.example.clouduser.service.NacosRemote;
import com.example.clouduser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    NacosRemote nacosRemote;

    @RequestMapping("/query")
    public List<User> query() throws Exception {
        Thread.sleep(1500);
        return userService.getAll();
    }

    @RequestMapping("/add")
    public boolean addUser(){
        User user = new User();
        user.setAccountId(11111L);
        user.setUsername("晓峰");
        user.setPassword("123456Aa");
        user.setEmail("1111@163.com");
        user.setMobile("12345678911");
        return userService.addUser(user);
    }

    @RequestMapping("/queryUserCenter")
    public String queryUserCenter(@RequestBody JSONObject json){
        //RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        return nacosRemote.queryUserCenter(json);
    }

    @GetMapping("/sendData")
    public String sendData(@RequestParam("no") String no){
        return nacosRemote.sendData(no);
    }

    public static void main(String[] args) throws NacosException, IOException {
        String serverAddr = "localhost";
        String dataId = "appA";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put("serverAddr",serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId,group,5000);
        System.out.println("first receive:" + content);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String s) {
                System.out.println("currentTime:" + new Date() + ",receive:" + s);
            }
        });
        int n = System.in.read();
    }
}
