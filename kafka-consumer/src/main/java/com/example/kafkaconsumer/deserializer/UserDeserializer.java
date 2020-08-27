package com.example.kafkaconsumer.deserializer;

import com.alibaba.fastjson.JSON;
import com.example.kafkaconsumer.pojo.User;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserDeserializer implements Deserializer<User> {

    private final static Logger logger = LoggerFactory.getLogger(UserDeserializer.class);

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public User deserialize(String s, byte[] bytes) {
        logger.info("自定义的反序列化-deserialize");
        return JSON.parseObject(bytes,User.class);
    }

    @Override
    public void close() {

    }
}
