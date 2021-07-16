package com.example.clouduser.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.clouduser.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/query")
    public String query() throws Exception {
        String userStr = request.getHeader("user");
        System.out.println("用户信息：" + userStr);
        if(StringUtils.isNotBlank(userStr)){
            User user = JSONObject.parseObject(userStr, User.class);
            System.out.println("姓名：" + user.getUsername());
        }
        Random random = new Random();
        int start = 100;
        int end = 200;
        int number = random.nextInt(end - start + 1) + start;
        Thread.sleep(number);
        return "query success!!!!" + number;
    }

    @RequestMapping("/add")
    public String addRole(){
        return "add success!";
    }
}
