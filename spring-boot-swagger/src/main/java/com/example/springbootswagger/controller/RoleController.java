package com.example.springbootswagger.controller;

import com.example.springbootswagger.bean.RestfulResponse;
import com.example.springbootswagger.model.request.RoleReq;
import com.example.springbootswagger.model.request.UserReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shs-cyhlwzytxf
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @PostMapping("/add")
    @ApiOperation(value = "add", notes = "新增")
    public RestfulResponse add(@Validated @RequestBody RoleReq req){
        try{
            String roleName = req.getRoleName();
            System.out.println("角色名：" + roleName);
            return RestfulResponse.success();
        }catch (Exception e){
            return RestfulResponse.failed(e.getMessage());
        }
    }

    @PostMapping("/query")
    @ApiOperation(value = "query", notes = "查询")
    public String query(){
        return "查询角色成功";
    }
}
