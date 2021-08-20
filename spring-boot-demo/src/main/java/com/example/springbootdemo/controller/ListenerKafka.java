package com.example.springbootdemo.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ListenerKafka implements CommandLineRunner {

    static{
        System.out.println("ListenerKafka类静态代码块");
    }

    {
        System.out.println("ListenerKafka类构造代码块");
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            while (true){
                Random random = new Random();
                int num = random.nextInt(10);
                System.out.println("随机数：" + num);
                if(num%2 == 0){
                    int a = num / 0;
                }
            }
        }catch (Exception e){
            System.out.println("被除数不能为0");
        }finally {
            System.out.println("commitSync");
        }
    }
}
