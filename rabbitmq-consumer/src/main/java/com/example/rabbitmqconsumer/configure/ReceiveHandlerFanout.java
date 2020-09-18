package com.example.rabbitmqconsumer.configure;

import com.example.rabbitmqconsumer.pojo.Mail;
import com.example.rabbitmqconsumer.pojo.MsgLog;
import com.example.rabbitmqconsumer.util.DateUtil;
import com.example.rabbitmqconsumer.util.MailUtil;
import com.example.rabbitmqconsumer.util.MessageUtil;
import com.example.rabbitmqconsumer.util.StatusUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReceiveHandlerFanout {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static Logger log = LoggerFactory.getLogger(ReceiveHandlerFanout.class);

    //监听email队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_email1", durable = "true"),
            exchange = @Exchange(value = "fanout.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.FANOUT)
            ))
    public void consume_email(Message message, Channel channel) throws Exception{
        Mail mail = MessageUtil.msgToObj(message,Mail.class);
        log.info("收到消息，有送邮件: {}", mail.toString());
        long id = mail.getId();

        Query query = new Query(Criteria.where("id").is(id));
        MsgLog msgLog = mongoTemplate.findOne(query, MsgLog.class);
        if (null == msgLog || msgLog.getStatus() == StatusUtil.CONSUMED_SUCCESS){
            log.info("重复消费, msgId: {}", id);
            return;
        }

        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();

        boolean flag = mailUtil.send(mail);
        if(flag){
            //消费成功
            Update update = new Update();
            update.set("status", StatusUtil.CONSUMED_SUCCESS);
            update.set("updateTime", DateUtil.dateToString(new Date(),4));
            mongoTemplate.updateFirst(query,update, MsgLog.class);
            //消费确认
            channel.basicAck(tag,false);
        }else{
            channel.basicNack(tag, false, true);
        }
    }

}
