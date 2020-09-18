package com.example.rabbitmqproducer.configure;

import com.example.rabbitmqproducer.pojo.MsgLog;
import com.example.rabbitmqproducer.util.DateUtil;
import com.example.rabbitmqproducer.util.MessageUtil;
import com.example.rabbitmqproducer.util.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class ResendMsg {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private final static Logger log = LoggerFactory.getLogger(ResendMsg.class);

    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;

    /**
     * 每30s拉取投递失败的消息, 重新投递
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void resend() {
        log.info("开始执行定时任务(重新投递消息)");

        Query query = new Query(Criteria.where("status").is(0));
        List<MsgLog> msgLogs = mongoTemplate.find(query, MsgLog.class);
        msgLogs.forEach(msgLog -> {
            long id = msgLog.getId();
            if (msgLog.getCount() >= MAX_TRY_COUNT) {
                Query query1 = new Query(Criteria.where("id").is(id));
                Update update = new Update();
                update.set("status", StatusUtil.DELIVER_FAIL);
                update.set("updateTime", DateUtil.dateToString(new Date(),4));
                mongoTemplate.updateFirst(query1,update, MsgLog.class);
                log.info("超过最大重试次数, 消息投递失败, msgId: {}", id);
            } else {
                Query query2 = new Query();
                query2.addCriteria(Criteria.where("id").is(id));
                Update update = new Update();
                update.set("count",msgLog.getCount() + 1);
                update.set("updateTime", DateUtil.dateToString(new Date(),4));
                mongoTemplate.updateFirst(query2,update,MsgLog.class);// 投递次数+1

                CorrelationData correlationData = new CorrelationData(id + "");
                rabbitTemplate.convertAndSend(msgLog.getExchange(),"", MessageUtil.objToMsg(msgLog.getMsg()),correlationData);// 重新投递

                log.info("第 " + (msgLog.getCount() + 1) + " 次重新投递消息");
            }
        });

        log.info("定时任务执行结束(重新投递消息)");
    }


}
