package com.example.kafkaproducer.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.kafkaproducer.config.KafkaProducerConfig;
import com.example.kafkaproducer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ProducerController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate2;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    @RequestMapping("/testProducer")
    public String testProducer(@RequestBody JSONObject json){
        String value = json.getString("msg");
        ListenableFuture aa = kafkaTemplate.send(topic, value);
        return aa.toString();
    }

    @RequestMapping("/testEmail")
    public String testEmail(){
        for(int i=0; i<10; i++){
            JSONObject json = new JSONObject();
            json.put("message","注册成功！");
            json.put("mobile","1752104607" + i);
            json.put("email","shs-cyhlwzytxf@chinaunicom.cn");
            kafkaTemplate.send(topic,json.toString());
        }
        return "发送成功";
    }

    @RequestMapping("/test")
    public String test(){
        User user = new User("注册成功","17521046077","shs-cyhlwzytxf@chinaunicom.cn");
        kafkaTemplate2.send(topic,user);
        return "发送成功";
    }
}
