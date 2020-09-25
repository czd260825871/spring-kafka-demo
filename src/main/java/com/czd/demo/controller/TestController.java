package com.czd.demo.controller;

import com.czd.demo.entity.User;
import com.czd.demo.producer.KafkaProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author admin
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private KafkaProducer kafkaProducer;

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
}
