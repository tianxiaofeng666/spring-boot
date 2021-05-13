package com.example.springbootutils.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpClientUtil {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String doGet(String url, Object param,String token) throws Exception {
        // 通过址默认配置创建一个httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpGet远程连接实例
        if (param != null) {
            url = url + "?" + param.toString();
        }
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头信息，鉴权
        httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization", token);
        // 设置配置请求参数
        // setConnectTimeout 连接主机服务超时时间
        // setConnectionRequestTimeout 请求超时时间
        // setSocketTimeout 数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);
        // 执行get请求得到返回对象
        httpResponse = httpClient.execute(httpGet);
        // 通过返回对象获取返回数据
        HttpEntity entity = httpResponse.getEntity();
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(entity, "UTF-8");
        closeResources(httpClient, httpResponse);
        return result;
    }

    /**
     * 向指定URL发送Post方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String doPost(String url, Object param,String token) throws Exception {
        // 通过址默认配置创建一个httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpGet远程连接实例
        if (param != null) {
            url = url + "?" + param.toString();
        }
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头信息，鉴权
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Authorization", token);
        // 设置配置请求参数
        // setConnectTimeout 连接主机服务超时时间
        // setConnectionRequestTimeout 请求超时时间
        // setSocketTimeout 数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        // 为httpGet实例设置配置
        httpPost.setConfig(requestConfig);
        // 执行get请求得到返回对象
        httpResponse = httpClient.execute(httpPost);
        // 通过返回对象获取返回数据
        HttpEntity entity = httpResponse.getEntity();
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(entity, "UTF-8");
        closeResources(httpClient, httpResponse);
        return result;
    }

    /**
     * 关闭资源
     *
     * @param httpClient
     * @param httpResponse
     */
    public static void closeResources(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) throws Exception {
        if (null != httpResponse) {
            httpResponse.close();
        }
        if (null != httpClient) {
            httpClient.close();
        }
    }

    /**
     * 发送 http put 请求，参数以原生字符串进行提交
     * @param url
     * @param encode
     * @return
     */
    public static String httpPutRaw(String url, String stringJson, Map<String,String> headers, String encode){
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpput.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch ( IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();  //关闭连接、释放资源
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 发送http delete请求
     */
    public static String httpDelete(String url,Map<String,String> headers,String encode){
        if(encode == null){
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpDelete httpdelete = new HttpDelete(url);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpdelete.setHeader(entry.getKey(),entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpdelete);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {   //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}