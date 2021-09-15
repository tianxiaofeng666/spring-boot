package com.example.springbootdelaytask.service;

import com.example.springbootdelaytask.entity.Order;
import com.example.springbootdelaytask.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author shs-cyhlwzytxf
 */
@Service
@Slf4j
public class OrderService {

    @Resource
    OrderMapper orderMapper;

    @Autowired
    private JedisPool jedisPool;

    public void createOrder(Order order){
        //第一步，订单信息入库
        orderMapper.createOrder(order);
        //第二步，将订单信息存入redis，超过一定时间未支付，自动删除该订单并发送邮件给用户
        //利用redis的zset有序集合,将订单超时时间戳与订单号分别设置为score和member
        Jedis jedis = jedisPool.getResource();
        log.info("当前时间：{}",LocalDateTime.now());
        long millSecond = LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        jedis.zadd("orderIds",millSecond,order.getOrderId()+"");
    }

    public void paymentOrder(String orderId){
        orderMapper.paymentOrder(orderId);
        //如果在规定时间内完成支付，从redis里面删除该orderId
        Jedis jedis = jedisPool.getResource();
        jedis.zrem("orderIds",orderId);
    }
}
