package com.example.kafkaconsumer.common;

import com.alibaba.fastjson.JSONObject;
import com.example.kafkaconsumer.pojo.User;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现CommandLineRunner接口并重写run()方法
 * SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法
 */
@Component
public class AutoConsumer implements CommandLineRunner {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void run(String... args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //每次poll返回的最多记录数
        props.put("max.poll.records",5);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("testDemo"));

        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (true) {
            //如果broker有数据，立即返回（不用等待），最多返回max.poll.records条，不到5条有多少返回多少，
            // 超过5条，只返回5条，处理完之后，再次poll
            //如果broker没有数据，在等待2000ms时间后返回null
            //max.poll.interval.ms 两次poll之间的最大间隔时间，默认300s,超过这个时间，broker认为consumer已死，
            //触发重平衡rebalance,在多数情况下这个参数是导致rebalance消息重复消费的关键
            //两次poll间隔超过5分钟，是因为业务处理消息耗时过长，原因可能有二：1. 单次poll拉取的消息过多  2. 单条消息处理时间过长
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
            System.out.println("当前时间 " + LocalDateTime.now() + ",接收 " + records.count() + "条消息");
            if(records.count() > 0){
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic = %s, partition = %s, offset = %d, " +
                                    "key = %s, value = %s",
                            record.topic(), record.partition(), record.offset(),
                            record.key(), record.value()));
                    //进行逻辑处理
                    String value = record.value();
                    User user = JSONObject.parseObject(value,User.class);
                    String email = user.getEmail();
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom("1248083709@qq.com");
                    message.setTo(email);
                    message.setSubject("注册成功");
                    message.setText("欢迎使用i云服系统！" + user.getMobile());
                    //mailSender.send(message);
                    System.out.println(user.toString());
                }

                //在正常处理流程中，我们使用异步提交来提高性能,不阻塞线程，低延迟，
                // 但最后使用同步提交（重试机制，直到成功或者最后抛出异常给应用）来保证位移提交成功。
                //异步提交没有实现重试，因为如果同时存在多个异步提交，可能会导致offset覆盖，造成重复消费
                /**
                 * 异步提交想进行重试同时又保证提交顺序的话：使用单调递增的序列号。
                 * 每次提交时都增加序列号，并在提交时将序列号添加到commitAsync回调中。
                 * 当您准备发送重试时，请检查回调获得的提交序列号是否与实例变量相等；
                 * 如果是，则没有较新的提交，可以重试。
                 * 如果实例序列号更高，请不要重试，因为已经发送了新的提交。
                 */
                consumer.commitAsync(new OffsetCommitCallback() {
                    int marker = atomicInteger.incrementAndGet();
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                        System.out.println("异步提交：" + marker);
                        if(e != null){
                            e.printStackTrace();
                            if(marker == atomicInteger.get()){
                                consumer.commitAsync(this);
                            }
                        }
                    }
                });
            }
        }
    }
}
