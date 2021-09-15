package com.example.springbootrabbitmqdelaytask.producer;

import com.example.springbootrabbitmqdelaytask.config.RabbitMQConfig;
import com.example.springbootrabbitmqdelaytask.consume.Consumer;
import com.example.springbootrabbitmqdelaytask.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
@Slf4j
@ConfigurationProperties(prefix = "aa.bb")
public class RabbitDemoTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/test")
    public void send(){
        Message message = new Message();
        message.setKey("rabbit");
        message.setValue("Hello");
        System.out.println("消息发送时间：" + Consumer.getCurrentTime());
        rabbitTemplate.convertAndSend(RabbitMQConfig.DELAY_EXCHANGE, "queue.delay", message, new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                message.getMessageProperties().setContentEncoding("UTF-8");
                message.getMessageProperties().setExpiration("20000");
                return message;
            }
        });
    }
}
