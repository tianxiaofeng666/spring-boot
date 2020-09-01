package com.example.springbootmybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootmybatis.pojo.Role;
import com.example.springbootmybatis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    public RoleService roleService;

    @RequestMapping("/getAllRole")
    public List<Role> getAllRole(){
        return roleService.getAll();
    }

    @RequestMapping("/getOneRole")
    public Role getOne(@RequestBody JSONObject json){
        int id = json.getInteger("id");
        return roleService.getOne(id);
    }

    @RequestMapping("/insertRole")
    public String insertRole(@RequestBody JSONObject json){
        /*JSONObject aa = {
                "name":"测试",
                "isAssign":"N",
                "isDeleted":"N",
                "creator":-10000,
                "modifier":-10000
        };*/
        Role role = JSONObject.parseObject(json.toJSONString(),Role.class);
        roleService.insert(role);
        return "添加成功！";
    }

    @RequestMapping("/getRoleByCondition")
    public List<Role> getRoleByCondition(@RequestBody JSONObject json){
         Role role = JSONObject.parseObject(json.toJSONString(),Role.class);
         return roleService.getRoleByCondition(role);
    }
}
