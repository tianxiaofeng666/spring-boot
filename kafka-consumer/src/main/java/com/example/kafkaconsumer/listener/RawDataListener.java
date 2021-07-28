package com.example.kafkaconsumer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.kafkaconsumer.pojo.User;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RawDataListener {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)，发送邮件
     * 默认情况下，@KafkaListener都是一条一条消费
     * @throws IOException
     */
    /*@KafkaListener(topics = "${spring.kafka.consumer.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listenEmail(ConsumerRecord<?,?> record) throws IOException{
        System.out.println(String.format("topic = %s, partition = %s, offset = %d, " +
                        "key = %s, value = %s\n",
                record.topic(), record.partition(), record.offset(),
                record.key(), record.value()));
        String value = (String) record.value();
        JSONObject obj = JSON.parseObject(value);
        String email = obj.getString("email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1248083709@qq.com");
        message.setTo(email);
        message.setSubject("注册成功");
        message.setText("欢迎使用i云服系统！" + obj.getString("mobile"));
        mailSender.send(message);
        System.out.println(value);
    }*/

    /*@KafkaListener(topics = {"${spring.kafka.consumer.topic}"})
    public void listen(ConsumerRecord<?,?> record) throws IOException{
        String key = (String) record.key();
        System.out.println("key:" + key);
        User user = (User) record.value();
        String email = user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1248083709@qq.com");
        message.setTo(email);
        message.setSubject("注册成功");
        message.setText("欢迎使用i云服系统！");
        mailSender.send(message);
        System.out.println(user.toString());
    }*/

    /**
     * 批量消费，根据消费者的消费性能和消费策略来增大kafka的消费吞吐量，可以避免消息堆积，提升性能
     * @throws IOException
     */
    /*@KafkaListener(topics = "${spring.kafka.consumer.topic}", containerFactory = "batchFactory")
    public void listenBatch(List<ConsumerRecord<String,String>> records, Acknowledgment ack){
        System.out.println("当前时间 " + LocalDateTime.now() + ",接收 " + records.size() + "条消息");
        try{
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic = %s, partition = %s, offset = %d, " +
                                "key = %s, value = %s\n",
                        record.topic(), record.partition(), record.offset(),
                        record.key(), record.value()));
                String value = (String) record.value();
                JSONObject obj = JSON.parseObject(value);
                String email = obj.getString("email");
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("1248083709@qq.com");
                message.setTo(email);
                message.setSubject("注册成功");
                message.setText("欢迎使用i云服系统！" + obj.getString("mobile"));
                mailSender.send(message);
                System.out.println(value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("start commit offset");
            //手动提交偏移量
            ack.acknowledge();
            System.out.println("stop commit offset");
        }
    }*/

}
