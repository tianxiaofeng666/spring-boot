package com.example.springbootdelaytask.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author shs-cyhlwzytxf
 */
public class PubSubDemo {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "192.168.233.128", 6379);

        //订阅channel
        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.subscribe(new JedisSub(),"myChannel");
        }).start();

        //创建一个发布者
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Jedis jedis = jedisPool.getResource();
            while (true) {
                String line = null;
                try {
                    line = reader.readLine();
                    if (!"quit".equals(line)) {
                        jedis.publish("myChannel", line);   //从 mychannel 的频道上推送消息
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }




}
