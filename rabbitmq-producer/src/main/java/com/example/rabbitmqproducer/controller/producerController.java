package com.example.rabbitmqproducer.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmqproducer.configure.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class producerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMessage")
    public String sendMessage(){
        //String message = "注册成功！手机号：17521046077，邮箱：tianxiaofeng@iyunbao.com";
        JSONObject json = new JSONObject();
        json.put("message","注册成功！");
        json.put("mobile","17521046077");
        json.put("email","tianxiaofeng@iyunbao.com");
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME,"topic.sms.email",json);
        return "发送成功！";
    }
}
