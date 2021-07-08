package com.example.clouduser.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.clouduser.pojo.User;
import com.example.clouduser.service.NacosRemote;
import com.example.clouduser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return nacosRemote.queryUserCenter(json);
    }
}
