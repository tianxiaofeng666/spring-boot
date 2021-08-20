package com.example.springbootdemo.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * spring初始化bean的时候，如果bean实现了InitializingBean接口，会自动调用afterPropertiesSet方法
 */
@Component
public class CustomConsume implements InitializingBean {

    static{
        System.out.println("CustomConsume类静态代码块");
    }

    {
        System.out.println("CustomConsume类构造代码块");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //System.out.println("hello,你好");
        System.out.println("当前线程11：" + Thread.currentThread().getName());

        new Thread(() ->{
            while(true){
                System.out.println("当前线程33：" + Thread.currentThread().getName());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
