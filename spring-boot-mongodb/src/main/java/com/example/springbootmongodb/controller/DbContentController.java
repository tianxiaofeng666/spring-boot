package com.example.springbootmongodb.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootmongodb.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DbContentController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/insertOneContent")
    public Content insertOne(@RequestBody JSONObject json){
        Content content = new Content("体育","男排");
        Content res = mongoTemplate.insert(content);
        return res;
    }


}
