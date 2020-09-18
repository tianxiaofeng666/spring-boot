package com.example.rabbitmqproducer.configure;

import com.example.rabbitmqproducer.pojo.MsgLog;
import com.example.rabbitmqproducer.util.DateUtil;
import com.example.rabbitmqproducer.util.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

@Configuration
public class RabbitmqConfigFanout {
    public static final String EXCHANGE_NAME = "fanout.exchange";//指定交换机类型为fanout(广播)
    public static final String QUEUE_EMAIL = "quene_email1";//eamil队列
    public static final String QUEUE_SMS = "quene_sms1";//sms队列

    private final static Logger log = LoggerFactory.getLogger(RabbitmqConfigFanout.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CachingConnectionFactory connectionFactory;

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

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到Exchange");
                String msgId = correlationData.getId();
                //更新消息状态为投递成功
                Query query = new Query(Criteria.where("id").is(Long.parseLong(msgId)));
                Update update = new Update();
                update.set("status", StatusUtil.DELIVER_SUCCESS);
                update.set("updateTime", DateUtil.dateToString(new Date(),4));
                mongoTemplate.updateFirst(query,update, MsgLog.class);
            } else {
                log.info("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });

        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });
        return rabbitTemplate;
    }
}
