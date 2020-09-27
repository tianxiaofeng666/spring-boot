package com.example.springbootmycat.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootmycat.pojo.Member;
import com.example.springbootmycat.service.MembereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MembereService membereService;

    @RequestMapping("/save")
    public int save(@RequestBody Member member){
        int aa = membereService.insert(member);
        return aa;
    }

    @RequestMapping("/getOne")
    public Member getOne(@RequestBody JSONObject json){
        long id = json.getLong("id");
        return membereService.getOne(id);
    }

}
