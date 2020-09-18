package com.example.rabbitmqproducer.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmqproducer.configure.RabbitmqConfigFanout;
import com.example.rabbitmqproducer.pojo.Mail;
import com.example.rabbitmqproducer.pojo.MsgLog;
import com.example.rabbitmqproducer.util.DateUtil;
import com.example.rabbitmqproducer.util.MessageUtil;
import com.example.rabbitmqproducer.util.SeqUtil;
import com.example.rabbitmqproducer.util.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class producerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static Logger log = LoggerFactory.getLogger(producerController.class);

    /*@RequestMapping("/sendTopic")
    public String sendTopic(){
        //String message = "注册成功！手机号：17521046077，邮箱：tianxiaofeng@iyunbao.com";
        JSONObject json = new JSONObject();
        json.put("message","注册成功！");
        json.put("mobile","17521046077");
        json.put("email","tianxiaofeng@iyunbao.com");
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME,"topic.sms.email",json);
        return "发送成功！";
    }*/

    @RequestMapping("/sendFanout")
    public String sendFanout(@RequestBody JSONObject json){
        long id = SeqUtil.generateId();
        String topic = json.getString("topic");
        String email = json.getString("email");
        String text = json.getString("text");
        Mail mail = new Mail();
        mail.setId(id);
        mail.setTopic(topic);
        mail.setEamil(email);
        mail.setText(text);

        log.info("消息：" + mail.toString());

        String msg = JSONObject.toJSONString(mail);
        MsgLog msgLog = new MsgLog(id,msg,RabbitmqConfigFanout.EXCHANGE_NAME,"",StatusUtil.DELIVERING,0, DateUtil.dateToString(new Date(),4),null);
        //消息入库
        mongoTemplate.insert(msgLog);

        CorrelationData correlationData = new CorrelationData(id + "");
        rabbitTemplate.convertAndSend(RabbitmqConfigFanout.EXCHANGE_NAME,"", MessageUtil.objToMsg(mail),correlationData);
        //rabbitTemplate.convertAndSend("direct.exchange","", MessageUtil.objToMsg(mail),correlationData);
        return "发送成功！";
    }
}
