package com.neo.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestFilter {

    @RequestMapping("/testFilter")
    public String testFilter(@RequestAttribute(value = "realParam") String param){
        JSONObject obj = JSONObject.parseObject(param);
        String partner = obj.getString("partner");
        return partner;
    }
}
