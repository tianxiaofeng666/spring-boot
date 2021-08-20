package com.example.springbootdemo.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.springbootdemo.config.CustomCellWriteHandler;
import com.example.springbootdemo.pojo.Article;
import com.example.springbootdemo.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

/**
 * 网络爬虫
 */
@Slf4j
public class NetworkWormStarter {

    private static List<Article> list = new ArrayList<>();

    /**
     * httpclient解析首页，获取首页内容
     */
    public static void parseHomePage(String url) {
        log.info("开始爬取首页：" + url);
        /*CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(8000).build();
        httpGet.setConfig(config);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response == null) {
                log.info(url + ":爬取无响应");
                return;
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String homePageContent = EntityUtils.toString(entity, "utf-8");
                parseHomePageContent(homePageContent);
            }
        } catch (ClientProtocolException e) {
            log.error(url + "-ClientProtocolException", e);
        } catch (IOException e) {
            log.error(url + "-IOException", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error(url + "-IOException", e);
            }
        }*/
        RestTemplate restTemplate = HttpRequestUtil.getRestemplate();
        String homePageContent = restTemplate.getForObject(url,String.class);
        parseHomePageContent(homePageContent);
        log.info("结束爬取首页：" + url);
    }

    /**
     * httpclient解析其他页数据，获取内容
     */
    public static void parseOtherPage(String url,Map<String,Object> map) throws Exception {
        log.info("开始爬取：" + url);
        /*CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置入参body
        List<NameValuePair> parameters = new ArrayList<>();
        if(map != null){
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                log.info("key:{},value:{}",entry.getKey(),entry.getValue());
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
            }
        }
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(8000).build();
        httpPost.setConfig(config);
        httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            log.info("响应：{}",response);
            if (response == null) {
                log.info(url + "爬取无响应");
                return;
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String homePageContent = EntityUtils.toString(entity, "utf-8");
                parseHomePageContent(homePageContent);
            }
        } catch (ClientProtocolException e) {
            log.error(url + "-ClientProtocolException", e);
        } catch (IOException e) {
            log.error(url + "-IOException", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error(url + "-IOException", e);
            }
        }*/
        RestTemplate restTemplate = HttpRequestUtil.getRestemplate();
        String otherPageContent = restTemplate.postForObject(url, map, String.class);
        parseOtherPageContent(otherPageContent,map.get("PageIndex"));
        log.info("结束爬取：" + url);
    }

    /**
     * 通过网络爬虫框架jsoup，解析网页内容，获取想要数据（博客的连接）
     * @param homePageContent
     */
    private static void parseHomePageContent(String homePageContent) {
        System.out.println("首页html网页数据:" + homePageContent);
        Document doc = Jsoup.parse(homePageContent);
        Element postList = doc.getElementById("post_list");
        Elements postItems = postList.getElementsByClass("post-item");
        log.info("元素个数：" + postItems.size());
        List<String> imgList = new ArrayList<>();
        //List<Article> list = new ArrayList();
        int i = 1;
        for (Element postItem : postItems) {
            Article article = new Article();
            System.out.println("首页，第" + i + "个节点数据：" + postItem);
            Elements titleElement = postItem.select(".post-item-title");
            System.out.println("文章标题:" + titleElement.text());;
            System.out.println("文章地址:" + titleElement.attr("href"));
            Elements summaryElement = postItem.select(".post-item-summary");
            System.out.println("文章简介：" + summaryElement.text());
            Elements avatarElement = postItem.select(".avatar");
            System.out.println("博主头像：" + avatarElement.attr("src"));
            imgList.add(avatarElement.attr("src"));
            Elements authorElement = postItem.select(".post-item-author");
            System.out.println("博主名称：" + authorElement.text());
            Elements dateElement = postItem.select("span[class=post-meta-item]");
            System.out.println("创建日期：" + dateElement.text());
            article.setTitle(titleElement.text());
            article.setContentUrl(titleElement.attr("href"));
            article.setContent(summaryElement.text());
            article.setPhotoUrl(avatarElement.attr("src"));
            article.setName(authorElement.text());
            article.setCreateTime(dateElement.text());
            list.add(article);
            i++;
        }
        downloadImgList(imgList);
        //downloadArticleList(list);
    }

    /**
     * 通过网络爬虫框架jsoup，解析网页内容，获取想要数据（博客的连接）
     * @param otherPageContent
     * @param pageIndex
     */
    private static void parseOtherPageContent(String otherPageContent, Object pageIndex) {
        System.out.println("第" + pageIndex +  "页html网页数据:" + otherPageContent);
        Document doc = Jsoup.parse(otherPageContent);
        Elements postItems = doc.getElementsByClass("post-item");
        log.info("元素个数：" + postItems.size());
        List<String> imgList = new ArrayList<>();
        //List<Article> list = new ArrayList();
        int i = 1;
        for (Element postItem : postItems) {
            Article article = new Article();
            System.out.println("第" + pageIndex + "页，第" + i + "个节点数据：" + postItem);
            Elements titleElement = postItem.select(".post-item-title");
            System.out.println("文章标题:" + titleElement.text());;
            System.out.println("文章地址:" + titleElement.attr("href"));
            Elements summaryElement = postItem.select(".post-item-summary");
            System.out.println("文章简介：" + summaryElement.text());
            Elements avatarElement = postItem.select(".avatar");
            System.out.println("博主头像：" + avatarElement.attr("src"));
            imgList.add(avatarElement.attr("src"));
            Elements authorElement = postItem.select(".post-item-author");
            System.out.println("博主名称：" + authorElement.text());
            Elements dateElement = postItem.select("span[class=post-meta-item]");
            System.out.println("创建日期：" + dateElement.text());
            article.setTitle(titleElement.text());
            article.setContentUrl(titleElement.attr("href"));
            article.setContent(summaryElement.text());
            article.setPhotoUrl(avatarElement.attr("src"));
            article.setName(authorElement.text());
            article.setCreateTime(dateElement.text());
            list.add(article);
            i++;
        }
        downloadImgList(imgList);
        //downloadArticleList(list);
    }

    /**
     * 别人服务器图片本地化
     * @return
     */
    private static void downloadImgList(List<String> imgUrlList) {
        for (String imgUrl : imgUrlList) {
            log.info("博主图片：" + imgUrl);
            if(StringUtils.isNotBlank(imgUrl)){
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(imgUrl);
                RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(8000).build();
                httpGet.setConfig(config);
                CloseableHttpResponse response = null;
                try {
                    response = httpClient.execute(httpGet);
                    log.info("图片请求响应：" + response);
                    if (response == null) {
                        log.info("爬取无响应");
                    }else {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity entity = response.getEntity();
                            String blogImagesPath = "F://photo/";
                            String dateDir = LocalDate.now() + "";
                            String uuid = UUID.randomUUID().toString();
                            log.info("&&&***:" + entity.getContentType().getValue());
                            String subfix = entity.getContentType().getValue().split("/")[1];
                            String fileName = blogImagesPath + dateDir + "/" + uuid + "." + subfix;
                            FileUtils.copyInputStreamToFile(entity.getContent(), new File(fileName));
                        }
                    }
                } catch (ClientProtocolException e) {
                    log.error(imgUrl + "-ClientProtocolException", e);
                } catch (IOException e) {
                    log.error(imgUrl + "-IOException", e);
                } catch (Exception e) {
                    log.error(imgUrl + "-Exception", e);
                } finally {
                    try {
                        if (response != null) {
                            response.close();
                        }
                        if (httpClient != null) {
                            httpClient.close();
                        }
                    } catch (IOException e) {
                        log.error(imgUrl + "-IOException", e);
                    }
                }
            }
        }
    }

    public static void downloadArticleList(List<Article> list) {
        String fileName = "F:\\excel\\" + System.currentTimeMillis() + ".xlsx";
        String modeName = "com.example.springbootdemo.pojo.Article";
        try{
            ExcelWriter excelWriter = EasyExcel.write(fileName, Class.forName(modeName))
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .build();
            WriteSheet writeSheet = EasyExcel.writerSheet("文章列表").build();
            excelWriter.write(list, writeSheet);
            excelWriter.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String homeUrl = "https://www.cnblogs.com/";
        String otherUrl = "https://www.cnblogs.com/AggSite/AggSitePostList";
        //网页数据分页，循环10次爬取前10页数据
        for(int i=1; i<=2; i++){
            if(i == 1){
                //首页get请求
                parseHomePage(homeUrl);
            }else{
                Map<String, Object> map = new HashMap<>();
                map.put("CategoryId",808);
                map.put("CategoryType","SiteHome");
                map.put("ItemListActionName","AggSitePostList");
                map.put("PageIndex",i);
                map.put("ParentCategoryId",0);
                map.put("TotalPostCount",4000);
                //其他页，post请求
                parseOtherPage(otherUrl,map);
            }
            Thread.sleep(5000);
        }
        System.out.println("文章总数：" + list.size());
        downloadArticleList(list);

        /*while(true) {
            parseHomePage(url);
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                log.error("主线程休眠异常-InterruptedException：",e);
            }
        }*/
    }
}
