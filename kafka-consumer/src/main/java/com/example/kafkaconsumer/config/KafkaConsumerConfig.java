package com.example.kafkaconsumer.config;

import com.example.kafkaconsumer.deserializer.UserDeserializer;
import com.example.kafkaconsumer.pojo.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String servers;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean enableAutoCommit;
//    @Value("${spring.kafka.consumer.session.timeout}")
//    private String sessionTimeout;
    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.consumer.concurrency}")
    private int concurrency;

    /**
     * 一条一条消费，并自动提交offset
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(100);
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }


    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        //propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        /**
         * auto.offset.reset：消费规则，默认earliest
         * earliest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
         * latest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
         */
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }

    /**
     * 批量消费，使用Ack机制确认消费,手动提交
     * 使用Kafka的Ack机制比较简单，只需简单的三步即可：
     * 1.设置ENABLE_AUTO_COMMIT_CONFIG=false，禁止自动提交
     * 2.设置AckMode=MANUAL_IMMEDIATE
     * 3.监听方法加入Acknowledgment ack 参数
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerBatchFactory());
        //设置并发量，这个并发量根据分区数决定，必须小于等于分区数，否则会有线程一直处于空闲状态
        factory.setConcurrency(1);
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        //设置提交偏移量的方式，手动提交，当ack-mode为MANUAL_IMMEDIATE时，acknowledge()实际还是调用的commitSync或者commitAsync
        //https://blog.csdn.net/wy13523/article/details/104989815
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //true:同步提交   false:异步提交
        factory.getContainerProperties().setSyncCommits(true);
        /**
         * RECORD
         * 每处理一条commit一次
         * BATCH(默认)
         * 每次poll的时候批量提交一次，频率取决于每次poll的调用频率
         * TIME
         * 每次间隔ackTime的时间去commit
         * COUNT
         * 累积达到ackCount次的ack去commit
         * COUNT_TIME
         * ackTime或ackCount哪个条件先满足，就commit
         * MANUAL
         * listener负责ack，但是背后也是批量上去
         * MANUAL_IMMEDIATE
         * listner负责ack，每调用一次，就立即commit
         *
         */
        factory.getContainerProperties().setPollTimeout(100);
        return factory;
    }

    public ConsumerFactory<String, String> consumerBatchFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerBatchConfigs());
    }

    public Map<String, Object> consumerBatchConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //每一批数量
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }

}
