package com.czd.demo.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@Configuration
public class KafkaTemplateConfig {


    @Value("${kafka.exchange.center.ip.addr}")
    private String ipAddr;

    @Value("${kafka.producer.retries}")
    private String retries;

    @Value("${kafka.producer.batch.size}")
    private String batchSize;

    @Value("${kafka.producer.linger.ms}")
    private String lingerMs;

    @Value("${kafka.producer.buffer.memory}")
    private String bufferMemory;

    @Value("${kafka.producer.max.in.flight.requests.per.connection}")
    private String requestsPerConnection;

    @Value("${kafka.producer.max.request.size}")
    private String maxRequestSize;

    @Value("${kafka.producer.request.timeout.ms}")
    private String requestTimeoutMs;


    public Map<String,Object> producerConfig(){
        Map<String, Object> props = new HashMap<>(32);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ipAddr);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, requestsPerConnection);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        return props;
    }


    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }


    @Bean
    public KafkaTemplate<String,String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }




    @Value("${kafka.consumer.enable.auto.commit}")
    private String enableAutoCommit;

    @Value("${kafka.consumer.session.timeout}")
    private String sessionTimeout;

    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;

    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;

    @Value("${kafka.consumer.concurrency}")
    private String concurrency;

    @Value("${kafka.consumer.max.poll}")
    private String maxPoll;

    @Value("${kafka.consumer.max.partition.fetch.bytes}")
    private String maxPartitionFetchBytes;

    @Value("${kafka.consumer.fetch.min.size}")
    private String fetchMinSize;



    public Map<String,Object> consumerCacheConfigs(){
        Map<String, Object> props = new HashMap<>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ipAddr);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group");
        //每一批数量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPoll);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinSize);
        return props;
    }


    @Bean("batchContainerFactory")
    public ConcurrentKafkaListenerContainerFactory listenerCacheContainer() {
        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
        container.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerCacheConfigs()));
        //设置并发量，小于或等于Topic的分区数
        container.setConcurrency(Integer.valueOf(concurrency));
        container.setAutoStartup(false);
        //设置为批量监听
        container.setBatchListener(true);
        return container;
    }
}
