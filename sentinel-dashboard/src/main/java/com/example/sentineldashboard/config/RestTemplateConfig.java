package com.example.sentineldashboard.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.ConnectionClosedException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    private int poolSize=8;

    private static final int MAX_RETRY_DEFAULT=3;
    /**
     * 最大重试次数，默认值为3，重试三次，需要needRetry为true才起作用
     */
    private int maxRetry=3;

    /*@Bean
    public RestTemplate restTemplate() {
        //默认重试3次
        this.maxRetry=this.maxRetry>0?this.maxRetry:MAX_RETRY_DEFAULT;
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(poolSize + 1);
        connMgr.setDefaultMaxPerRoute(poolSize);
        //设置请求和传输超时时间
        RequestConfig requestConfig = getRequestConfig();
        //设置重试次数
        HttpRequestRetryHandler httpRequestRetryHandler = getHttpRequestRetryHandler();
        //CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).setRetryHandler(httpRequestRetryHandler).build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        // template.setErrorHandler(new DefaultResponseErrorHandler(3, true));
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastjson.setFastJsonConfig(fastJsonConfig);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastjson.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(fastjson);
        template.setMessageConverters(converters);
        return template;
    }
    //设置请求和传输超时时间
    private RequestConfig getRequestConfig(){
        RequestConfig requestConfig =null;
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(300000)
                .setConnectTimeout(300000)
                .setConnectionRequestTimeout(300000).build();//连接池获取连接的超时时间
        return requestConfig;
    }

    //设置重试次数
    private HttpRequestRetryHandler getHttpRequestRetryHandler(){
        HttpRequestRetryHandler httpRequestRetryHandler =null;
        httpRequestRetryHandler = new HttpRequestRetryHandler(){
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
                if(executionCount>=maxRetry)
                    return false;
                if(exception instanceof ConnectTimeoutException) //socket连接超时
                    return true;
                if(exception instanceof ConnectionClosedException)//发送请求或者读取结果过程中连接被关闭
                    return true;
                if(exception instanceof SocketTimeoutException) //读取结果超时
                    return true;
                if(exception instanceof SocketException) //从连接池中拿到本来已经失效的连接
                    return true;
                if(exception instanceof UnknownHostException) //主机名无法解析
                    return false;
                return false;
            }
        };
        return httpRequestRetryHandler;
    }*/

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
}
