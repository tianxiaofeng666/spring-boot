package com.example.autoconfigure.service;

import com.example.autoconfigure.properties.UniviewProperties;

public class UniviewService {

    UniviewProperties univiewProperties;

    public UniviewProperties getUniviewProperties() {
        return univiewProperties;
    }

    public void setUniviewProperties(UniviewProperties univiewProperties) {
        this.univiewProperties = univiewProperties;
    }

    /**
     * 测试,是否能接收到配置文件里面的信息
     */
    public String test(){
        String id = univiewProperties.getId();
        String password = univiewProperties.getPwd();
        String url = univiewProperties.getUrl();
        return "用户名：" + id + ",密码：" + password + ",url：" + url + "。。。。。。。";
    }
}
