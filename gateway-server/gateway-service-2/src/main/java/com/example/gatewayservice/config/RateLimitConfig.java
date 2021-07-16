package com.example.gatewayservice.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfig {

    /**
     * 按照访问地址进行限流(也可以安装其他条件进行限流)，具体可以看exchange.getRequest()的方法和属性
     * @return   参考：https://blog.csdn.net/xgw1010/article/details/109403892
     */
    @Bean
    KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}
