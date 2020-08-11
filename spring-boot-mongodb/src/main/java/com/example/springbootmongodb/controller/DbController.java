package com.example.springbootmongodb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootmongodb.pojo.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class DbController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/findAll")
    public List<User> findAll(@RequestBody JSONObject json){
        List<User> list = mongoTemplate.findAll(User.class);
        return list;
    }

    @RequestMapping("/findMany")
    public List<User> findMany(@RequestBody JSONObject json){
        String name = json.getString("name");
        Query query = new Query(Criteria.where("name").is(name));
        List<User> res = mongoTemplate.find(query, User.class);
        return res;
    }

    @RequestMapping("/findOne")
    public User findOne(@RequestBody JSONObject json){
        String name = json.getString("name");
        String mobile = json.getString("mobile");
        Query query = new Query(Criteria.where("name").is(name).and("mobile").is(mobile));
        User user = mongoTemplate.findOne(query, User.class);
        return user;
    }

    @RequestMapping("/insertOne")
    public User insertOne(@RequestBody JSONObject json){
        User user = new User("张三","17521046666");
        User res = mongoTemplate.insert(user);
        return res;
    }

    @RequestMapping("/insertMany")
    public String insertMany(@RequestBody JSONObject json){
        /*String param = {
                "channel":"CLOUD",
                "list":[
                            {
                                "name":"小米",
                                "mobile":"11122233344"
                            },
                            {
                                "name":"华为",
                                "mobile":"12345678911"
                            }
                ]
        };*/
        JSONArray list = json.getJSONArray("list");
        List<User> userList = JSONObject.parseArray(JSONObject.toJSONString(list), User.class);
        List<User> res = (List<User>) mongoTemplate.insertAll(userList);
        return "添加成功！";
    }

    @RequestMapping("/delete")
    public DeleteResult delete(@RequestBody JSONObject json){
        String name = json.getString("name");
        String mobile = json.getString("mobile");
        Query query = new Query(Criteria.where("name").is(name).and("mobile").is(mobile));
        DeleteResult res = mongoTemplate.remove(query, User.class);
        return res;
    }

    @RequestMapping("/update")
    public UpdateResult update(@RequestBody JSONObject json){
        String name = json.getString("name");
        String mobile = json.getString("mobile");
        String newMobile = json.getString("newMobile");
        Query query = new Query(Criteria.where("name").is(name).and("mobile").is(mobile));
        Update update = new Update();
        update.set("mobile",newMobile);
        //UpdateResult res = mongoTemplate.updateFirst(query, update, User.class);
        UpdateResult res = mongoTemplate.updateMulti(query, update, User.class);
        return res;
    }
}
