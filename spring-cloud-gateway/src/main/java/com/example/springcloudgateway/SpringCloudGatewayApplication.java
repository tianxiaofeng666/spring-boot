package com.example.springcloudgateway;

import com.example.springcloudgateway.filter.TimeFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator cloudRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                            .route(r -> r.path("/cloud/**")
                                         .filters(f -> f.filter(new TimeFilter()))
                                         .uri("lb://EUREKA-PRODUCER")
                                         .id("cloud_service")
                            )
                            .build();
    }

}
