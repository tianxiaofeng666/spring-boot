package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableHystrix
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    /*@Bean
    public RouteLocator testCloudUser(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/cloudUser/**")
                        .uri("lb://cloud-user")
                        .id("cloud-user")
                )
                .build();
    }

    @Bean
    public RouteLocator testUserCenter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/userCenter/**")
                        .uri("lb://user-center")
                        .id("user-center")
                )
                .build();
    }*/


}
