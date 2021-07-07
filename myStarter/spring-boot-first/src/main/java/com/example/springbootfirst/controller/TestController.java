package com.example.springbootfirst.controller;

import com.example.autoconfigure.service.UniviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UniviewService univiewService;

    @PostMapping("/test")
    public String test(){
        String result = univiewService.test();
        return result;
    }
}
