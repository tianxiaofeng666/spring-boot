package com.example.springbootrabbitmqdelaytask.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shs-cyhlwzytxf
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfig {
    private String host;
    private int port;
    private String username;
    private String password;

    public static String DELAY_EXCHANGE = "delay.exchange";//死信Exchange
    public static String BUSS_EXCHANGE = "buss.exchange";//业务Exchange
    public static String DELAY_QUEUE = "delay.queue";//死信队列
    public static String BUSS_QUEUE = "buss.queue";//业务队列

    /**
     * 定义一个死信队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        Map<String,Object> args = new HashMap<String,Object>();
        //消息过期后转发的exchange
        args.put("x-dead-letter-exchange",BUSS_EXCHANGE);
        //消息过期后转发的routing-key
        args.put("x-dead-letter-routing-key","delay.msg");
        //队列中消息的过期时间（注意消息上也可以设置过期时间），两者若同时设置取其小
        args.put("x-message-ttl",20000);
        return QueueBuilder.durable(DELAY_QUEUE).withArguments(args).build();
    }


    /**
     * 定义普通的业务队列
     * @return
     */
    @Bean
    public Queue bussQueue(){
        return new Queue(BUSS_QUEUE,true);
    }

    /**
     * 死信Exchange
     * @return
     */
    @Bean
    public TopicExchange delayTopicExchange(){
        return new TopicExchange(DELAY_EXCHANGE);
    }

    /**
     * 业务Exchange
     * @return
     */
    @Bean
    public TopicExchange bussTopicExchange(){
        return new TopicExchange(BUSS_EXCHANGE);
    }

    /**
     * 绑定死信队列与死信Exchange,设置routing-key为queue.delay
     * @return
     */
    @Bean
    public Binding bindingDelayExchangeMessage(){
        return BindingBuilder.bind(delayQueue()).to(delayTopicExchange()).with("queue.delay");
    }

    /**
     * 绑定业务队列与业务Exchange，设置routing-key为delay.msg
     * 注意：此处的rounting-key与死信队列的x-dead-letter-routing-key保持一致，才能保证死信消息过期后可以转发到此队列中
     * @return
     */
    @Bean
    public Binding bindingDelayMessage(){
        return BindingBuilder.bind(bussQueue()).to(bussTopicExchange()).with("delay.msg");
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setAddresses(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
