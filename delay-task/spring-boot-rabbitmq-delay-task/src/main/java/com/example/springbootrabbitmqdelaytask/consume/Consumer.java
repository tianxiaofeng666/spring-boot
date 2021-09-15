package com.example.springbootrabbitmqdelaytask.consume;

import com.example.springbootrabbitmqdelaytask.pojo.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shs-cyhlwzytxf
 */
@Component
@RabbitListener(queues = "buss.queue")
public class Consumer {

    @RabbitHandler
    public void consumerMessage(Message message){
        String key = message.getKey();
        String value = message.getValue();
        System.out.println("延迟队列消费时间" + getCurrentTime());
        System.out.println("消费的消息：" + message.getKey() + "---" + message.getValue());
    }

    public static String getCurrentTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
