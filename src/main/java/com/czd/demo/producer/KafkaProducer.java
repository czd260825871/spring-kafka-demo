package com.czd.demo.producer;

import com.alibaba.fastjson.JSON;
import com.czd.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author admin
 */
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    private static final String TOPIC = "czd";


    public void send(User user){
        String string = JSON.toJSONString(user);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, string);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("fail");
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("success");
            }
        });
    }
}
