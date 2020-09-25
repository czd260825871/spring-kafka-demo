package com.czd.demo;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author admin
 */
@SpringBootApplication
@EnableKafka
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        /*DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
        KafkaListenerEndpointRegistry bean = defaultListableBeanFactory.getBean(KafkaListenerEndpointRegistry.class);
        bean.start();*/
    }

}
