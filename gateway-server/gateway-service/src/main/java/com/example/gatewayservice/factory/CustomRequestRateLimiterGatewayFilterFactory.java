package com.example.gatewayservice.factory;

import com.example.gatewayservice.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * gateway限流后，默认返回429的改造：增加响应body
 * 参考：https://blog.csdn.net/youbl/article/details/115178343
 */
@Slf4j
@Component
public class CustomRequestRateLimiterGatewayFilterFactory extends RequestRateLimiterGatewayFilterFactory {

    private final RateLimiter defaultRateLimiter;

    private final KeyResolver defaultKeyResolver;

    public CustomRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter, @Qualifier("pathKeyResolver") KeyResolver defaultKeyResolver) {
        super(defaultRateLimiter, defaultKeyResolver);
        this.defaultRateLimiter = defaultRateLimiter;
        this.defaultKeyResolver = defaultKeyResolver;
    }

    @Override
    public GatewayFilter apply(Config config) {
        KeyResolver resolver = getOrDefault(config.getKeyResolver(), defaultKeyResolver);
        RateLimiter<Object> limiter = getOrDefault(config.getRateLimiter(), defaultRateLimiter);
        return (exchange, chain) -> resolver.resolve(exchange).flatMap(key -> {
//            if (EMPTY_KEY.equals(key)) {
//                if (denyEmpty) {
//                    setResponseStatus(exchange, emptyKeyStatus);
//                    return exchange.getResponse().setComplete();
//                }
//                return chain.filter(exchange);
//            }
            String routeId = config.getRouteId();
            if (routeId == null) {
                Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                routeId = route.getId();
            }

            String finalRouteId = routeId;
            return limiter.isAllowed(routeId, key).flatMap(response -> {

                for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                    exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
                }

                if (response.isAllowed()) {
                    return chain.filter(exchange);
                }

                log.warn("已限流: {}", finalRouteId);
                ServerHttpResponse httpResponse = exchange.getResponse();
                return ResponseUtil.webFluxResponseWriter(httpResponse, "application/json;charset=UTF-8", HttpStatus.TOO_MANY_REQUESTS, "访问已受限制，请稍候重试");

                /*httpResponse.setStatusCode(config.getStatusCode());
                if (!httpResponse.getHeaders().containsKey("Content-Type")) {
                    httpResponse.getHeaders().add("Content-Type", "application/json");
                }
                DataBuffer buffer = httpResponse.bufferFactory().wrap("{'msg':'访问已受限制，请稍候重试'}".getBytes(StandardCharsets.UTF_8));
                return httpResponse.writeWith(Mono.just(buffer));*/

                // return exchange.getResponse().setComplete();
            });
        });
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return (configValue != null) ? configValue : defaultValue;
    }

}
