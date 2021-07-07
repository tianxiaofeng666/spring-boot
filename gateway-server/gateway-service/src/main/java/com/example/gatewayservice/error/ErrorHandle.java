package com.example.gatewayservice.error;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandle {

    @RequestMapping("/default-error")
    public String DefaultErrorHandle(){
        System.out.println("default-error");
        return "这是通用错误处理返回的信息。";
    }

    @RequestMapping("/cloud-user-error")
    public String CloudUserErrorHandle(){
        System.out.println("cloud-user-error");
        return "这是cloud-user返回的信息。";
    }

    @RequestMapping("/user-center-error")
    public String userCenterErrorHandle(){
        System.out.println("user-center-error");
        return "这是user-center返回的信息。";
    }
}
