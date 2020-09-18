package com.example.rabbitmqconsumer.configure;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ReceiveHandler {

    @Autowired
    private JavaMailSender mailSender;

    //监听邮件队列
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_email", durable = "true"),
            exchange = @Exchange(
                    value = "topic.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"topic.#.email.#","email.*"}))
    public void rece_email(JSONObject obj){
        System.out.println(" [邮件服务] received : " + obj.toJSONString() + "!");
        String email = obj.getString("email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1248083709@qq.com");
        message.setTo(email);
        message.setSubject("注册成功");
        message.setText("欢迎使用i云服系统！");
        mailSender.send(message);
    }*/

    //监听短信队列
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_sms", durable = "true"),
            exchange = @Exchange(
                    value = "topic.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"topic.#.sms.#"}))
    public void rece_sms(JSONObject obj){
        System.out.println(" [短信服务] received : " + obj.toJSONString() + "!");
    }*/

}
