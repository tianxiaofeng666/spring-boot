package com.example.springbootdelaytask.controller;

import com.example.springbootdelaytask.entity.Order;
import com.example.springbootdelaytask.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 下单
     * @param order
     * @return
     */
    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Order order){
        orderService.createOrder(order);
        return "订单创建成功";
    }

    /**
     * 支付
     */
    @GetMapping("/paymentOrder/{orderId}")
    public String paymentOrder(@PathVariable("orderId") String orderId){
        orderService.paymentOrder(orderId);
        return "支付成功";
    }

}
