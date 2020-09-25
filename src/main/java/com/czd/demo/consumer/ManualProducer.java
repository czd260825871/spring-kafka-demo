package com.czd.demo.consumer;

import com.czd.demo.configuration.KafkaTemplateConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author admin
 */
@Component
public class ManualProducer {


    @Autowired
    private KafkaTemplateConfig kafkaTemplateConfig;

    private KafkaConsumer<String, String> getKafkaConsumer(){
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaTemplateConfig.consumerCacheConfigs());
        consumer.subscribe(Collections.singletonList("czd"));
        return consumer;
    }



    private void consumer(){
        KafkaConsumer<String, String> kafkaConsumer = getKafkaConsumer();
        try {
            while (true){
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                records.forEach(record ->{

                });

                Set<TopicPartition> partitionSet = kafkaConsumer.assignment();
                partitionSet.forEach(partition -> {
                    kafkaConsumer.seek(partition, 0);
                });
                //异步提交
                Map<TopicPartition, OffsetAndMetadata> commitOffset = new HashMap<>();
                kafkaConsumer.commitAsync();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                //同步提交
                kafkaConsumer.commitSync();
            }finally {
                kafkaConsumer.close();
            }
        }

    }

    @PostConstruct
    public void init(){
        new Thread(this::consumer).start();
    }
}
