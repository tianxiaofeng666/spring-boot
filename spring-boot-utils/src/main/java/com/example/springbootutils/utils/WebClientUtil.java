package com.example.springbootutils.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Slf4j
public class WebClientUtil {

    public static JSONObject postRequestForPathParameter(String url, String pathAndParameter) throws Exception {

        WebClient webClient = WebClient.create(url);
        JSONObject respJson;
        try {
            Mono<ClientResponse> monoResp = webClient.post()
                    .uri(pathAndParameter)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .exchange();
            ClientResponse clientResponse = monoResp.block();
            assert clientResponse != null;
            respJson = JSONObject.parseObject(clientResponse.bodyToMono(String.class).block());
        } catch (Exception e) {
            throw new Exception("发送postRequestForPathParameter请求失败：" + e.getMessage());
        }
        return respJson;
    }

    /**
     * gbk 方式解码外部接口数据
     * @param url
     * @param pathAndParameter
     * @param body
     * @return
     * @throws Exception
     */
    public static JSONObject postRequestForBodyParameterWithGbk(String url, String pathAndParameter, JSONObject body) throws Exception {
        WebClient webClient = WebClient.create(url);
        JSONObject respJson;
        try {
            // 首先接收外部接口返回结果的字节数组形式
            Mono<byte[]> bytes = webClient.post()
                    .uri(pathAndParameter)
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .body(BodyInserters.fromObject(body.toJSONString()))
                    .exchange()
                    .flatMap(response -> response.bodyToMono(ByteArrayResource.class))
                    .map(ByteArrayResource::getByteArray);
            // 然后以gbk方式解码
            respJson = JSONObject.parseObject(new String(bytes.block(), "GBK"));
        } catch (Exception e) {
            throw new Exception("发送postRequestForBodyParameter请求失败：" + e.getMessage());
        }
        return respJson;
    }

    public static JSONObject postRequestForBodyParameter(String url, String pathAndParameter, JSONObject body) throws Exception {
        log.info("调用接口：" + url + pathAndParameter + "入参：" + body);
        WebClient webClient = WebClient.create(url);
        JSONObject respJson;
        try {
            Mono<String> monoResp = webClient.post()
                        .uri(pathAndParameter)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(body.toJSONString()))
                        .retrieve()
                        .bodyToMono(String.class);
            respJson = JSONObject.parseObject(monoResp.block());
        } catch (Exception e) {
            log.error("发送postRequestForBodyParameter请求失败--请求路径--" + url + pathAndParameter + "--请求参数--" + body);
            throw new Exception("发送postRequestForBodyParameter请求失败：" + e.getMessage());
        }
        return respJson;
    }

    public static JSONObject httpsWithNotVerify(String url, JSONObject body, String token) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpPost httpPost = new HttpPost(url);
            // 构造消息头
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            if (!StringUtils.isEmpty(token)) {
                httpPost.setHeader("Authorization", "Bearer " + token);
            }
            // 构建消息实体
            StringEntity stringEntity = new StringEntity(body.toString(), Charset.forName("UTF-8"));
            stringEntity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                EntityUtils.consume(entity);

                return JSONObject.parseObject(result);
            }
        }
    }


    private static CloseableHttpClient createHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, (chain, authType) -> true)
                .build();

        SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslcontext, null, null,
                new NoopHostnameVerifier());

        return HttpClients.custom().setSSLSocketFactory(sslSf).build();
    }

    public static JSONObject postRequestWithTokenForBodyParameter(String url, String pathAndParameter, JSONObject body, String token, String sessionId) throws Exception {
        WebClient webClient = WebClient.create(url);
        JSONObject respJson;
        try {
            Mono<String> monoResp = webClient.post()
                    .uri(pathAndParameter)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header("Authorization", "Bearer " + token)
                    .header("SessionId", sessionId)
                    .body(BodyInserters.fromObject(body.toJSONString()))
                    .retrieve()
                    .bodyToMono(String.class);
            respJson = JSONObject.parseObject(monoResp.block());
        } catch (Exception e) {
            throw new Exception("发送postRequestWithTokenForBodyParameter请求失败：" + e.getMessage());
        }
        return respJson;
    }

    /**
     * 获取当前时间 年份后两位+月份+日期+小时+分钟+秒
     * 例如：2021-03-15 10:04:23------>210315100423
     * @return
     */
    public static String getCurrentTime() {
        String result = "";
        LocalDateTime currentTime = LocalDateTime.now();
        result = result + currentTime.getYear();
        result = result + createStringForNumber(currentTime.getMonthValue());
        result = result + createStringForNumber(currentTime.getDayOfMonth());
        result = result + createStringForNumber(currentTime.getHour());
        result = result + createStringForNumber(currentTime.getMinute());
        result = result + createStringForNumber(currentTime.getSecond());
        return result;
    }

    /**
     * 数字转为字符串
     * 个位数字补零
     * @param number
     * @return
     */
    private static String createStringForNumber(Integer number) {
        if ((number+"").length() == 1) {
            return "0"+number;
        }
        return number+"";
    }
}
