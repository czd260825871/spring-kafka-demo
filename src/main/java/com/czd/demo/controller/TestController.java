package com.czd.demo.controller;

import com.czd.demo.entity.User;
import com.czd.demo.producer.KafkaProducer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author admin
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private KafkaProducer kafkaProducer;

    @Resource
    private KafkaListenerEndpointRegistry registry;


    @Autowired
    private ConfigurableApplicationContext context;

    @RequestMapping(value = "/send")
    public String success(){
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setName("John" + i);
            user.setGender(i % 2 == 0 ? 1 : 0);
            user.setBirthday(LocalDateTime.now());
            kafkaProducer.send(user);
        }
        return "success";
    }


    @RequestMapping(value = "/admin")
    public void admin(){
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "192.168.11.90:9092");
        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListTopicsResult listTopicsResult = adminClient.listTopics();
            Set<String> topics = listTopicsResult.names().get();
            if (topics.isEmpty()) {
                System.out.println("no topic");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            e.getMessage();
            System.err.println("Kafka is not available");
        }
        System.out.println(1);
    }


    @RequestMapping(value = "/start")
    public void start(){
        registry.start();
      /*  MessageListenerContainer container = registry.getListenerContainer("group1");
        boolean running = container.isRunning();
        container.start();
        container.resume();*/
    }


    @RequestMapping(value = "/stop")
    public void stop(){
        /*DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
        KafkaListenerEndpointRegistry bean = defaultListableBeanFactory.getBean(KafkaListenerEndpointRegistry.class);
        bean.stop();*/
        registry.stop();
       /* MessageListenerContainer container = registry.getListenerContainer("group1");
        boolean running = container.isRunning();
        container.stop();
        container.pause();*/
    }

}
