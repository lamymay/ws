package com.badger.spring.boot.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "test_producer", consumerGroup = "test_consumer-group")
public class DemoConsumer implements RocketMQListener<String> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(String message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}