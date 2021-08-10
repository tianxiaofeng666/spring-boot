package com.example.kafkaconsumer.common;

import com.alibaba.fastjson.JSONObject;
import com.example.kafkaconsumer.pojo.User;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * spring初始化bean的时候，如果bean实现了InitializingBean接口，会自动调用afterPropertiesSet方法
 */
/*@Component
public class CustomConsume implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("当前线程11：" + Thread.currentThread().getName());
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //每次poll返回的最多记录数
        props.put("max.poll.records",5);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("testDemo"));
        // 开启一个消费者线程
        new Thread(() ->{
            while(true){
                System.out.println("当前线程33：" + Thread.currentThread().getName());
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
                System.out.println("当前时间 " + LocalDateTime.now() + ",接收 " + records.count() + "条消息");
                if(records.count() > 0){
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println(String.format("topic = %s, partition = %s, offset = %d, " +
                                        "key = %s, value = %s",
                                record.topic(), record.partition(), record.offset(),
                                record.key(), record.value()));
                        //进行逻辑处理
                        String value = record.value();
                        User user = JSONObject.parseObject(value,User.class);
                        String email = user.getEmail();
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setFrom("1248083709@qq.com");
                        message.setTo(email);
                        message.setSubject("注册成功");
                        message.setText("欢迎使用i云服系统！" + user.getMobile());
                        //mailSender.send(message);
                        System.out.println(user.toString());
                    }
                    //同步提交
                    consumer.commitSync();
                }
            }
        }).start();
    }
}*/
