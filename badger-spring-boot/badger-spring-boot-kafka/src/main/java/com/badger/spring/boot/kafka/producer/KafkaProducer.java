package com.badger.spring.boot.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@EnableScheduling
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void send() {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("badger_test",
                UUID.randomUUID().toString());
        future.addCallback(o -> System.out.println("send-消息发送成功"), throwable -> System.out.println("消息发送失败："));
    }
}