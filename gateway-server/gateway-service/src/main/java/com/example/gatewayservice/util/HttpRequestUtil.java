package com.example.gatewayservice.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.*;

/**
 *  @author shs-cyhlwzydxz
 *  用于处理http请求
 */
public class HttpRequestUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static RestTemplate restTemplate = new RestTemplate();

    public static RestTemplate getRestemplate(){
        //RestTemplate设置编码
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    public static void main(String[] args) throws Exception{
        //自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));
        String url = "http://localhost:8080/cloudUser/api/role/query";
        RestTemplate restTemplate = getRestemplate();
        for (int i = 1; i<= 5; i++){
            try{
                Future<String> result = executor.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        String result = restTemplate.postForObject(url, null, String.class);
                        return result;
                    }
                });
                System.out.println("第" + i + "次结果：" + result.get());
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(i + "访问过于频繁！");
            }
        }
    }
}