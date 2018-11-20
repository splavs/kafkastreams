package com.splavs;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TextConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");

        KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("streams-wordcount-output"));

        //noinspection InfiniteLoopStatement
        while (true) {
            ConsumerRecords<String, Long> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Long> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}
