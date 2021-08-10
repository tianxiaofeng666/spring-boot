package com.example.kafkaconsumer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
public class ConsumerController {

    @RequestMapping("/test")
    public String test(){
        System.out.println("当前线程22：" + Thread.currentThread().getName());
        return "hello world!";
    }
}
