package com.example.springbootswagger.controller;

import com.example.springbootswagger.bean.RestfulResponse;
import com.example.springbootswagger.model.request.UserReq;
import com.example.springbootswagger.model.response.UserResp;
import com.example.springbootswagger.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shs-cyhlwzytxf
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/add")
    @ApiOperation(value = "新增用户", httpMethod = "POST", notes = "新增")
    //@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = false)
    public RestfulResponse<UserResp> add(@Validated @RequestBody UserReq req){
        return RestfulResponse.success(userService.add(req));
    }

    @PostMapping("/query")
    @ApiOperation(value = "query", notes = "查询")
    public String query(){
        return "查询用户成功";
    }
}
