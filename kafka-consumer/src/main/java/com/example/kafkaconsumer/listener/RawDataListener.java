package com.example.kafkaconsumer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.kafkaconsumer.pojo.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RawDataListener {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)，发送邮件
     * @param record
     * @throws IOException
     */
    /*@KafkaListener(topics = {"${spring.kafka.consumer.topic}"})
    public void listenEmail(ConsumerRecord<?,?> record) throws IOException{
        String value = (String) record.value();
        JSONObject obj = JSON.parseObject(value);
        String email = obj.getString("email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1248083709@qq.com");
        message.setTo(email);
        message.setSubject("注册成功");
        message.setText("欢迎使用i云服系统！");
        mailSender.send(message);
        System.out.println(value);
    }*/

    @KafkaListener(topics = {"${spring.kafka.consumer.topic}"})
    public void listen(ConsumerRecord<?,?> record) throws IOException{
        User user = (User) record.value();
        String email = user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1248083709@qq.com");
        message.setTo(email);
        message.setSubject("注册成功");
        message.setText("欢迎使用i云服系统！");
        mailSender.send(message);
        System.out.println(user.toString());
    }

}
