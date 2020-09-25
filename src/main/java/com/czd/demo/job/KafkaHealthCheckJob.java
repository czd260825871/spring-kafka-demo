package com.czd.demo.job;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author admin
 */
@Component
public class KafkaHealthCheckJob {

    @Resource
    private KafkaListenerEndpointRegistry registry;

    @Value("${kafka.exchange.center.ip.addr}")
    private String ipAddr;


    private static final Properties PROPERTIES = new Properties();


    private Properties init(){
        if (null != PROPERTIES.getProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG)) {
            return PROPERTIES;
        }
        PROPERTIES.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, ipAddr);
        return PROPERTIES;
    }


    @Scheduled(cron = "0 0/2 * * * ?")
    public void fiveMinuteRun(){
        Properties properties = init();
        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListTopicsResult listTopicsResult = adminClient.listTopics();
            Set<String> topics = listTopicsResult.names().get();
            if (topics.isEmpty()) {
                System.out.println("no topic");
            }
            if (!registry.isRunning()) {
                registry.start();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("Kafka is not available");
            if (registry.isRunning()) {
                registry.stop();
            }
        }
    }

}
