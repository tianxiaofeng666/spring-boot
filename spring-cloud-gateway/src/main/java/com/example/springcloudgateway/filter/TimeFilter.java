package com.example.springcloudgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Spring Cloud Gateway 的 Filter 分为两种
 *      GatewayFilter 与 GlobalFilter
 *      GlobalFilter 会应用到所有的路由上，而 GatewayFilter 将应用到单个路由或者一个分组的路由上
 */
public class TimeFilter implements GatewayFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(TimeFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //pre 请求被执行前调用
        exchange.getAttributes().put("timeBegin", System.currentTimeMillis());

        return chain.filter(exchange).then(
                //post 请求被执行后调用
                Mono.fromRunnable(() ->{
                    Long startTime = exchange.getAttribute("timeBegin");
                    if(startTime != null){
                        log.info(exchange.getRequest().getURI().getRawPath() + ": {}", (System.currentTimeMillis() - startTime) + "ms");
                    }
                })
        );
    }

    /**
     * 过滤器设定优先级别的，值越大则优先级越低
     * LOWEST_PRECEDENCE:表示级别最低，最后执行过滤操作
     * HIGHEST_PRECEDENCE:过滤器在众多过滤器中级别最高，也就是过滤的时候最先执行
     *
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
