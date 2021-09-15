package com.example.springbootdelaytask;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootdelaytask.mapper")
public class SpringBootDelayTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDelayTaskApplication.class, args);
    }

}
