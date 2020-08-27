package com.example.kafkaproducer.serializer;

import com.alibaba.fastjson.JSON;
import com.example.kafkaproducer.pojo.User;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserSerializer implements Serializer<User> {

    private final static Logger logger = LoggerFactory.getLogger(UserSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        logger.info("自定义序列化组件--configure");
    }

    @Override
    public byte[] serialize(String s, User user) {
        logger.info("自定义序列化组件--serialize");
        return JSON.toJSONBytes(user);
    }

    @Override
    public void close() {
        logger.info("自定义的序列化组件--close");
    }
}
