package com.example.springbootaop.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootaop.aspect.WebLog;
import com.example.springbootaop.aspect.WebLogAspect;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    private final static Logger logger = LoggerFactory.getLogger(AopController.class);

    @RequestMapping("/testAop")
    @WebLog(description = "测试日志功能")
    public String testAop(@RequestBody JSONObject json){
        String name = json.getString("name");
        logger.info("姓名：{}",name);
        return name;
    }

    @RequestMapping("/testAop2")
    @WebLog(description = "测试日志功能.手机号")
    public String testAop2(@RequestBody JSONObject json){
        String mobile = json.getString("mobile");
        logger.info("手机号：{}",mobile);
        return mobile;
    }

    @RequestMapping("/test")
    public String test(@RequestBody JSONObject json){
        String mobile = json.getString("mobile");
        logger.info("姓名：{}",mobile);
        return mobile;
    }
}

