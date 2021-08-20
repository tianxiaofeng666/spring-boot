package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.pojo.HttpGetWithEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Slf4j
public class RPCTest {
    public final static Logger logger = LoggerFactory.getLogger(RPCTest.class);
    public static void main(String[] args) throws Exception {
//        String token = doGet();
//        log.info("token：{}",token);
        doGet();
    }

    //https  get拼参数请求
    public static String doGet(){
        String url = "https://58.19.177.202:9999/ipms/car/find/2";
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("accessToken", "99+qyLP/eTznVYNYkkFYVzMe5bgP6Xcxp5Vz+WhnpL8Y=");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpGet.setConfig(requestConfig);
        try{
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("结果：{}",result);
        return JSONObject.parseObject(result).getJSONObject("data").getString("accessToken");
    }

    //https get body请求
    public static void doGetWithBody(){
        String url = "https://58.19.177.202:9999/ipms/caraccess/find/his";
        String token = "f3z3mUVytOwwAJzxCkwvzgBLRZvFSTKoppx0s50ejfGc=";
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        //HttpGet httpGet = new HttpGet(url);
        HttpGetWithEntity httpGet = new HttpGetWithEntity(url);
        httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("accessToken", token);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpGet.setConfig(requestConfig);
        JSONObject obj = new JSONObject();
        obj.put("pageNum",1);
        obj.put("pageSize",10);
        obj.put("queryTimeBegin","2021-06-01 00:00:00");
        obj.put("queryTimeEnd","2021-06-02 00:00:00");
        httpGet.setEntity(new StringEntity(obj.toJSONString(),"UTF-8"));
        try{
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("结果：{}",result);
    }

    public static void doPost() throws Exception {
        String url = "https://58.19.177.202:9999/ipms/car/update";
        String token = "79xbylE1sxpw5PDBozCtq+mtEwUArBR6jB3hBj9ZfHoE=";
        // 通过址默认配置创建一个httpClient实例
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpGet远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头信息，鉴权
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("accessToken", token);
        // 设置配置请求参数
        // setConnectTimeout 连接主机服务超时时间
        // setConnectionRequestTimeout 请求超时时间
        // setSocketTimeout 数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        // 为httpGet实例设置配置
        httpPost.setConfig(requestConfig);
        JSONObject obj = new JSONObject();
        obj.put("id",1);
        obj.put("carColor",1);
        //obj.put("carNum","1111");
        httpPost.setEntity(new StringEntity(obj.toJSONString(),"UTF-8"));
        // 执行get请求得到返回对象
        httpResponse = httpClient.execute(httpPost);
        // 通过返回对象获取返回数据
        HttpEntity entity = httpResponse.getEntity();
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(entity, "UTF-8");
        closeResources(httpClient, httpResponse);
        log.info("结果：{}",result);
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


    public static CloseableHttpClient createSSLClientDefault(){
        try {
            //SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 在JSSE中，证书信任管理器类就是实现了接口X509TrustManager的类。我们可以自己实现该接口，让它信任我们指定的证书。
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            //信任所有
            X509TrustManager x509mgr = new X509TrustManager() {
                //该方法检查客户端的证书，若不信任该证书则抛出异常
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) {

                }
                //该方法检查服务端的证书，若不信任该证书则抛出异常
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) {

                }
                //返回受信任的X509证书数组。
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            //创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            //  HttpsURLConnection对象就可以正常连接HTTPS了，无论其证书是否经权威机构的验证，只要实现了接口X509TrustManager的类MyX509TrustManager信任该证书。
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建默认的httpClient实例.
        return  HttpClients.createDefault();
    }

}
