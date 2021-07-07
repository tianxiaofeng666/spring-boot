package com.example.clouduser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @RequestMapping("/query")
    public String query() throws Exception {
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
