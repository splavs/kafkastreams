package com.splavs;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class TextProducer {
    Random random = new Random();

    public void produceMessages() throws InterruptedException {
        Properties props = new Properties();

        props.put("bootstrap.servers", "kafka:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        //noinspection InfiniteLoopStatement
        while (true) {
            producer.send(new ProducerRecord<>("test", null, generateString()));
            Thread.sleep(10000);

        }

//        producer.close();
    }

    private String generateString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < random.nextInt(50); i++) {
            stringBuilder.append(generateWord()).append(" ");
        }
        return stringBuilder.toString();
    }

    private String generateWord() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < random.nextInt(20); i++) {
            int generatedChar = 'a' + random.nextInt(25);
            stringBuilder.append((char) generatedChar);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        new TextProducer().produceMessages();
    }

}
