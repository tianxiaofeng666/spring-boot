package com.example.gatewayservice.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.gatewayservice.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.*;

/**
 * 网关统一的token验证
 * 参考：https://blog.csdn.net/bufegar0/article/details/109399960
 * @author pangu
 * */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Value("${authority.enabled}")
    private Boolean enabled;

    @Value("${authority.ignoreUrl}")
    private String urlList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("忽略url：{}",urlList);
        // 如果未启用网关验证，则跳过
        if(!enabled){
            return chain.filter(exchange);
        }
        //　如果在忽略的url里，则跳过
        String path = exchange.getRequest().getURI().getPath();
        log.info("path:{}",path);
        if (ignore(path)) {
            log.info("msg:{}","该接口跳过token校验！");
            return chain.filter(exchange);
        }

        // 验证token是否有效
        ServerHttpResponse resp = exchange.getResponse();
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if(StringUtils.isEmpty(token)){//未登录返回
            return unauthorized(resp, "无token，或token已失效，请重新登录！");
        }
        //从redis里面解析token用户信息并设置到Header中去
        JSONObject obj = new JSONObject();
        obj.put("username","admin");
        obj.put("password","123456");
        ServerHttpRequest request = exchange.getRequest().mutate().header("user", obj.toJSONString()).build();
        exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    private boolean ignore(String path){
        String[] urlStr = urlList.split(",");
        List<String> list = Arrays.asList(urlStr);
        return list.contains(path);
    }

    private Mono<Void> unauthorized(ServerHttpResponse resp, String msg) {
        return ResponseUtil.webFluxResponseWriter(resp, "application/json;charset=UTF-8", HttpStatus.UNAUTHORIZED, msg);
    }

    /**
     * 指定全局过滤器的执行顺序，值越小 优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}

