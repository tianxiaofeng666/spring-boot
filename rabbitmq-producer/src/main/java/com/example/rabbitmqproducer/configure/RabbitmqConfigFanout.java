package com.example.rabbitmqproducer.configure;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfigFanout {
    public static final String EXCHANGE_NAME = "fanout.exchange";//指定交换机类型为fanout(广播)
    public static final String QUEUE_EMAIL = "quene_email";//eamil队列
    public static final String QUEUE_SMS = "quene_sms";//sms队列

    //声明交换机
    @Bean(EXCHANGE_NAME)
    public Exchange exchange(){
        return ExchangeBuilder.fanoutExchange(EXCHANGE_NAME).durable(true).build();
    }

    //声明email队列
    @Bean(QUEUE_EMAIL)
    public Queue emailQuene(){
        return new Queue(QUEUE_EMAIL);
    }

    //声明sms
    @Bean(QUEUE_SMS)
    public Queue smsQuene(){
        return new Queue(QUEUE_SMS);
    }

    //绑定email队列到交换机
    @Bean
    public Binding bindingEmailFanout(@Qualifier(QUEUE_EMAIL) Queue queue,@Qualifier(EXCHANGE_NAME) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    //绑定sms队列到交换机
    @Bean
    public Binding bindingSMSFanout(@Qualifier(QUEUE_SMS) Queue queue,@Qualifier(EXCHANGE_NAME) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}
