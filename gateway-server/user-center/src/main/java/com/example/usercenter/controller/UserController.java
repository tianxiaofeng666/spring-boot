package com.example.usercenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.usercenter.pojo.User;
import com.example.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/query")
    public List<User> query() throws Exception{
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
    public String queryUserCenter(@RequestBody JSONObject json, HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println("token:" + token);
        String name = json.getString("name");
        System.out.println("姓名：" + name);
        return "query success!";
    }

    @GetMapping("/sendData")
    public String sendData(@RequestParam("no") String no, HttpServletRequest request){
        String token = request.getHeader("token");
        return no + "***" + token;
    }
}
