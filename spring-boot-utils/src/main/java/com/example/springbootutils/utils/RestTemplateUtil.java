package com.example.springbootutils.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class RestTemplateUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    public static RestTemplate getRestemplate(){
        //RestTemplate设置编码
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     *  http请求远程服务器方法
     * @param url
     * @param params
     * @param method
     * @return
     */
    public static String httpRemotePost(String url, Map<String, Object> params,String token, HttpMethod method)
    {
        if(StringUtils.isEmpty(url)) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //如果传入token，则把token放入header
        if(!StringUtils.isEmpty(token)){
            headers.add("Authorization", token);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(params), headers);

        ResponseEntity<String> response = null;
        try
        {
            response = getRestemplate().exchange(url, method, requestEntity, String.class);
        }
        catch (RestClientException e)
        {
            log.error("请求接口失败，原因:" + e);
        }
        return response.getBody();
    }




    /**
     *  http请求远程服务器方法
     * @param url
     * @param params
     * @param method
     * @return
     */
    public static String httpRemote(String url, Map<String, Object> params,String token, HttpMethod method)
    {
        if(StringUtils.isEmpty(url)) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //如果传入token，则把token放入header
        if(!StringUtils.isEmpty(token)){
            headers.add("Authorization", "Bearer " + token);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(params), headers);

        ResponseEntity<String> response = null;
        try
        {
            response = getRestemplate().exchange(url, method, requestEntity, String.class);
        }
        catch (RestClientException e)
        {
            log.error("请求接口失败，原因:" + e);
        }
        return response.getBody();
    }

    /**
     *  http请求远程服务器方法
     * @param url
     * @param params
     * @param method
     * @return
     */
    public static String httpRemoteGet(String url, Map<String, Object> params, HttpMethod method) {
        if(StringUtils.isEmpty(url)) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        StringBuffer buffer = new StringBuffer(url);
        buffer.append("?");
        for(Map.Entry<String, Object> entry : params.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue());
            buffer.append("&");
        }
        int lastIndex = buffer.toString().lastIndexOf("&");
        String remoteUrl = buffer.toString().substring(0, lastIndex);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    remoteUrl,
                    method,
                    null,
                    String.class);
        } catch (RestClientException e) {
            log.error("请求接口失败，原因:" + e);
        }
        return response.getBody();
    }

    /**
     * http请求远程服务器方法(带token)
     * @param url
     * @param params
     * @param token
     * @param method
     * @return
     */
    public static String httpRemoteGetToken(String url, Map<String, Object> params, String token, HttpMethod method) {
        if(StringUtils.isEmpty(url)) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //如果传入token，则把token放入header
        if(!StringUtils.isEmpty(token)){
            headers.add("Authorization", token);
        }
        String remoteUrl = url;
        if(params != null){
            StringBuffer buffer = new StringBuffer(url);
            buffer.append("?");
            for(Map.Entry<String, Object> entry : params.entrySet()) {
                buffer.append(entry.getKey());
                buffer.append("=");
                buffer.append(entry.getValue());
                buffer.append("&");
            }
            int lastIndex = buffer.toString().lastIndexOf("&");
            remoteUrl = buffer.toString().substring(0, lastIndex);
        }
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    remoteUrl,
                    method,
                    null,
                    String.class);
        } catch (RestClientException e) {
            log.error("请求接口失败，原因:" + e);
        }
        return response.getBody();
    }
}