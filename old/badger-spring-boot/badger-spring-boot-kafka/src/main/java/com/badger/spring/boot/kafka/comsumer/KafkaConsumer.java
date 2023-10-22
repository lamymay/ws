package com.badger.spring.boot.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = { "badger_test" })
    public void consumer(ConsumerRecord<String, Object> consumerRecord) {
        System.out.println("消息消费--》" + consumerRecord.value().getClass().getName() + "数据 -->" + consumerRecord.value());
    }
}