package com.badger.test;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;

public class ProducerTest {

    public static void main(String[] args) throws Exception {
        // 1、构造 Properties 对象
        Properties props = new Properties();
        props.put("bootstrap.servers", " localhost:9092");// 必填
        props.put("key.serializer", "org.apache.kafka.common.serialization. StringSerializer");// 必填
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");// 必填
        props.put("acks", "-1");
        props.put("retries", 3);
        props.put("batch.size", 323840);
        props.put("linger.ms", 10);
        props.put("buffer.memory", 33554432);
        props.put("max.block.ms", 3000);
        // 2、构造 Kafka Producer 对象
        Producer<String, String> producer = new KafkaProducer<>(props);
        // 3、 构造 ProducerRecord 对象
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("my-topic ", "我是第一条数据");
        // 4、 发送消息
        // producer.send(producerRecord);
        // 4、 发送消息
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                // 正常的业务
            } else {
                if (exception instanceof RetriableException) {
                    // 处理可重试瞬时异常
                } else {
                    // 处理不可重试异常
                }
            }
        });
        // 5、关闭连接
        producer.close();
    }
}