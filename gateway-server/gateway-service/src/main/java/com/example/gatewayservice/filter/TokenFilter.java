package com.example.gatewayservice.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.gatewayservice.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
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
        ServerHttpRequest req = exchange.getRequest();
        ServerHttpResponse resp = exchange.getResponse();

        log.info("忽略url：{}",urlList);
        // 如果未启用网关验证，则跳过
        if(!enabled){
            return chain.filter(exchange);
        }
        //　如果在忽略的url里，则跳过
        String path = req.getURI().getPath();
        log.info("path:{}",path);
        if (ignore(path)) {
            log.info("msg:{}","该接口跳过token校验！");
            return chain.filter(exchange);
        }

        // 验证token是否有效
        String token = req.getHeaders().getFirst("token");
        log.info("token:{}",token);
        if(StringUtils.isEmpty(token)){//未登录返回
            return unauthorized(resp, "无token，或token已失效，请重新登录！");
        }
        //从redis里面解析token用户信息并设置到Header中去
        JSONObject obj = new JSONObject();
        obj.put("username","admin");
        obj.put("password","123456");
        ServerHttpRequest request = req.mutate().header("user", obj.toJSONString()).build();
        exchange.mutate().request(request).build();

        //Spring Cloud Gateway 读取、修改 Request Body
        /**
         * 参考：https://blog.csdn.net/yucaifu1989/article/details/105433864
         *      https://blog.csdn.net/yucaifu1989/article/details/105433195
         */
        HttpMethod method = req.getMethod();
        if (HttpMethod.POST.equals(method)) {
            //重新构造request，参考ModifyRequestBodyGatewayFilterFactory
            ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
            MediaType mediaType = req.getHeaders().getContentType();
            //重点
            Mono<Object> modifiedBody = serverRequest.bodyToMono(JSONObject.class).flatMap(body -> {
                if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || MediaType.APPLICATION_JSON_UTF8.isCompatibleWith(mediaType)) {
                    log.info("body:{}",body);
                    String paramStr = body.getString("name");
                    log.info("入参name:{}",paramStr);
                    JSONObject newBody = null;
                    try{
                        //实际使用过程中，网关可以对加密入参解密后再路由到后端微服务上
                        newBody = verifySignature(paramStr);
                    }catch (Exception e){
                        log.info("解析异常：{}",e.getMessage());
                        //return Mono.error(new Exception(e.getMessage()));
                        return unauthorized(resp,"解析异常");
                    }
                    return Mono.just(newBody);
                }
                return Mono.empty();
            });
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, Object.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(req.getHeaders());
            //猜测这个就是之前报400错误的元凶，之前修改了body但是没有重新写content length
            //headers.remove("Content-Length");
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequest decorator = this.decorate(exchange, headers, outputMessage);
                return chain.filter(exchange.mutate().request(decorator).build());
            }));
        }

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

    private JSONObject verifySignature(String paramStr) throws Exception{
        JSONObject obj = new JSONObject();
        obj.put("name",paramStr);
        obj.put("age",18);
        obj.put("sex","男");
        //int a = 1/0;
        return obj;
    }

    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0L) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set("Transfer-Encoding", "chunked");
                }
                return httpHeaders;
            }
            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }
}

