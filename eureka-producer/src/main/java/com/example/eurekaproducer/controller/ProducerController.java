package com.example.eurekaproducer.controller;

import com.example.eurekaproducer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        //return "hello "+name+"，this is first messge";
        String result = producerService.index(name);
        return result;
    }
}
