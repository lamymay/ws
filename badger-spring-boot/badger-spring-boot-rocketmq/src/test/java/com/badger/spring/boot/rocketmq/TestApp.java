package com.badger.spring.boot.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import com.badger.spring.boot.rocketmq.RocketmqApplicaltion;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RocketmqApplicaltion.class })
public class TestApp {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void syncSend() {
        for (int i = 0; i < 100; i++) {
            // 同步消息
            Message<String> bashMessage = new GenericMessage<String>("test_producer" + i);
            SendResult syncSend = rocketMQTemplate.syncSend("test_producer", bashMessage);
            System.out.println(syncSend);
        }
    }

//    @Test
    public void asyncSend() {
        for (int i = 0; i < 100; i++) {
            // 异步消息
            Message<String> message = new GenericMessage<String>("test_producer" + i);
            rocketMQTemplate.asyncSend("test_producer", message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("发送失败");
                }
            });
        }
    }

//    @Test
    public void sendOneWay() {
        for (int i = 0; i < 100; i++) {
            // 单向发送消息
            Message<String> message = new GenericMessage<String>("test_producer" + i);
            rocketMQTemplate.sendOneWay("test_producer", message);
            System.out.println("只发送一次");
        }
    }

//    @Test
    public void syncSendOrder() {
        // 发送有序消息
        String[] tags = new String[] { "TagA", "TagC", "TagD" };
        for (int i = 0; i < 10; i++) {
            // 加个时间前缀
            Message<String> message = new GenericMessage<String>("我是顺序消息" + i);
            SendResult sendResult = rocketMQTemplate.syncSendOrderly("test_producer:" + tags[i % tags.length], message,
                    i + "");
            System.out.println(sendResult);
        }
    }
}
