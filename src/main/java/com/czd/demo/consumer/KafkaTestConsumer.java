package com.czd.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
@Component
public class KafkaTestConsumer {

    @KafkaListener(topics = {"czd"}, id = "group1", containerFactory = "batchContainerFactory")
    public void consumer(List<ConsumerRecord<?, ?>> records){
        System.out.println(records.size());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
