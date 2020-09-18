package com.example.rabbitmqproducer.util;

public class StatusUtil {

    public static final int DELIVERING = 0;// 消息投递中

    public static final int DELIVER_SUCCESS = 1;// 投递成功

    public static final int DELIVER_FAIL = 2;// 投递失败

    public static final int CONSUMED_SUCCESS = 3;// 已消费

}
