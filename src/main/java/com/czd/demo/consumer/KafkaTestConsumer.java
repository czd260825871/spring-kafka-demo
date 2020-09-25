package com.czd.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 */
@Component
public class KafkaTestConsumer {

    @KafkaListener(topics = {"czd"}, id = "group1", containerFactory = "batchContainerFactory",
            topicPartitions = {@TopicPartition(topic = "czd", partitions = {"0"})})
    public void consumer(List<ConsumerRecord<?, ?>> records){
        System.out.println(records);
        System.out.println(1);
    }
}
