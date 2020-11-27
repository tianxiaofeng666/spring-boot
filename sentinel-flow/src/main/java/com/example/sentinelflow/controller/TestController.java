package com.example.sentinelflow.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.example.sentinelflow.service.NacosRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Properties;

@RestController
public class TestController {

    @Autowired
    private NacosRemote nacosRemote;

    @PostMapping("/testNacos")
    public String testNacos(@RequestBody JSONObject json){
        return nacosRemote.testNacos(json);
    }

    @PostMapping("/testRemote")
    public String testRemote(@RequestBody JSONObject json){
        return json.getString("id");
    }

    public static void main(String[] args) {
        //String aa = getConfig();
        //JSONObject json = JSON.parseObject(aa);
        //System.out.println(aa);
        //System.out.println("姓名：" + json.getString("name"));
        boolean bb = publishConfigWithNamespace();
        System.out.println(bb);
    }

    /**
     * 获取配置           https://nacos.io/zh-cn/docs/sdk.html
     */
    public static String getConfig(){
        String serverAddr = "localhost:8848";
        String groupId = "DEFAULT_GROUP";
        String dataId = "user-info";
        ConfigService configService = null;
        String flowContent = "";
        try {
            configService = NacosFactory.createConfigService(serverAddr);
            flowContent = configService.getConfig(dataId, groupId, 5000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return flowContent;
    }

    /**
     *发布配置
     */
    public static boolean publishConfig(){
        String serverAddr = "localhost:8848";
        String groupId = "DEFAULT_GROUP";
        String dataId = "user-info";
        String content = "{\n" +
                "    \"name\":\"张三\",\n" +
                "    \"age\":23,\n" +
                "    \"gender\":\"男\"\n" +
                "}";
        ConfigService configService = null;
        boolean isPublishOk = false;
        try {
            configService = NacosFactory.createConfigService(serverAddr);
            isPublishOk = configService.publishConfig(dataId,groupId,content);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return isPublishOk;
    }

    /**
     *发布配置，带namespace命名空间
     */
    public static boolean publishConfigWithNamespace(){
        String serverAddr = "localhost:8848";
        String namespace = "82abeb09-7e1e-4ba9-a654-5cd2951db5b1";
        String groupId = "DEFAULT_GROUP";
        String dataId = "user-info";
        String content = "{\n" +
                "    \"name\":\"李四\",\n" +
                "    \"age\":23,\n" +
                "    \"gender\":\"女\"\n" +
                "}";
        ConfigService configService = null;
        boolean isPublishOk = false;
        try {
            Properties properits= new Properties();
            properits.put(PropertyKeyConst.SERVER_ADDR,serverAddr);
            properits.put(PropertyKeyConst.NAMESPACE,namespace);
            configService = NacosFactory.createConfigService(properits);
            isPublishOk = configService.publishConfig(dataId,groupId,content);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return isPublishOk;
    }
}
