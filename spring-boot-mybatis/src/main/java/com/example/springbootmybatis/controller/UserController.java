package com.example.springbootmybatis.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootmybatis.bean.RestfulResponse;
import com.example.springbootmybatis.pojo.User;
import com.example.springbootmybatis.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *分页查询
     * @return
     */
    @RequestMapping("/findByPage")
    public JSONObject findByPage(@RequestBody JSONObject json){
        JSONObject obj = new JSONObject();
        int pageNum = json.getInteger("pageNum");
        int pageSize = json.getInteger("pageSize");
        int from = (pageNum - 1) * pageSize;
        List<User> list = userService.findByPage(from,pageSize);
        obj.put("list",list);
        JSONObject pageObj = new JSONObject();
        pageObj.put("pageNum",pageNum);
        pageObj.put("pageSize",pageSize);
        pageObj.put("total",userService.findCount());
        obj.put("page",pageObj);
        return obj;
    }

    /**
     * 根据条件查询
     */
    @RequestMapping("/getUserByCondition")
    public List<User> getUserByCondition(@RequestBody JSONObject json){
        User user = JSONObject.parseObject(json.toJSONString(),User.class);
        return userService.getUserByCondition(user);
    }

    /**
     * 根据条件查询,并分页
     */
    @RequestMapping("/getUserByConditionAndPage")
    public JSONObject getUserByConditionAndPage(@RequestBody JSONObject json){
        JSONObject obj = new JSONObject();
        int pageNum = json.getInteger("pageNum");
        int pageSize = json.getInteger("pageSize");
        int from = (pageNum - 1) * pageSize;
        User user = JSONObject.parseObject(json.toJSONString(),User.class);
        String userName = json.getString("userName");
        List<User> list = userService.getUserByConditionAndPage(userName,from,pageSize);
        obj.put("list",list);
        JSONObject pageObj = new JSONObject();
        pageObj.put("pageNum",pageNum);
        pageObj.put("pageSize",pageSize);
        pageObj.put("total",userService.findCountByCondition(user));
        obj.put("page",pageObj);
        return obj;
    }

    /**
     * 插入一条记录
     */
    @RequestMapping("/insertOneUser")
    public String insertOneUser(@RequestBody JSONObject json){
        User user = JSONObject.parseObject(json.toJSONString(),User.class);
        userService.insertOneUser(user);
        return "添加成功";
    }

    @RequestMapping("/testMulti")
    public RestfulResponse testMulti(@RequestBody JSONObject json){
        int userId = json.getInteger("userId");
        return RestfulResponse.success(userService.testMulti(userId));
    }

    @RequestMapping("/testMultiList")
    public RestfulResponse testMultiList(@RequestBody JSONObject json){
        JSONArray idList = json.getJSONArray("idList");
        List<Integer> userIdList = JSONObject.parseArray(idList.toJSONString(),Integer.class);
        return RestfulResponse.success(userService.testMultiList(userIdList));
    }



}
