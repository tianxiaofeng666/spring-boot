package com.example.springbootaop.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @RequestMapping("/test11")
    public String test11(@RequestBody JSONObject json){
        String a = json.getString("name");
        log.info("入参：{}",a);
        return a;
    }
}
