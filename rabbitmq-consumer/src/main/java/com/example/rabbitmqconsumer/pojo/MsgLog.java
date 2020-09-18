package com.example.rabbitmqconsumer.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "t_message")
public class MsgLog {
    @Id
    private long id;
    //消息体, json格式化
    private String msg;
    //交换机
    private String exchange;
    //路由键
    private String routingKey;
    //状态: 0投递中 1投递成功 2投递失败 3已消费
    private int status;
    //重试次数
    private int count;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;


    public MsgLog() {
    }

    public MsgLog(long id, String msg, String exchange, String routingKey, int status, int count, String createTime, String updateTime) {
        this.id = id;
        this.msg = msg;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.status = status;
        this.count = count;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
