package com.example.eurekaproducer.service;

import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    public String index(String name){
        return name;
    }
}
