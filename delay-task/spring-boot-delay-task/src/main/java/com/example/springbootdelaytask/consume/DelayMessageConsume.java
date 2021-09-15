package com.example.springbootdelaytask.consume;

import com.example.springbootdelaytask.entity.Order;
import com.example.springbootdelaytask.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

/**
 * @author shs-cyhlwzytxf
 */
@Component
@Slf4j
public class DelayMessageConsume implements CommandLineRunner {

    @Autowired
    private JedisPool jedisPool;

    @Resource
    OrderMapper orderMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void run(String... args) throws Exception {
        Jedis jedis = jedisPool.getResource();

        new Thread(() -> {
            while(true){
                Set<Tuple> item = jedis.zrangeWithScores("orderIds", 0, 1);
                if(item == null || item.isEmpty()){
                    //System.out.println("当前没有等待的任务");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                long  score = (long) ((Tuple)item.toArray()[0]).getScore();
                long currentTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                if(currentTime >= score){
                    //订单超过设置时长未支付，删除并发送邮件
                    String orderId = ((Tuple)item.toArray()[0]).getElement();
                    Long res = jedis.zrem("orderIds",orderId);
                    if(res != null && res > 0){
                        orderMapper.deleteOrderByOrderId(orderId);
                        Order order = orderMapper.getOrderByOrderId(orderId);
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setFrom("1248083709@qq.com");
                        message.setTo(order.getEmail());
                        message.setSubject("订单取消");
                        message.setText("你的订单" + orderId + ",因超时未支付，已取消");
                        mailSender.send(message);
                    }
                }
            }
        }).start();
    }
}
