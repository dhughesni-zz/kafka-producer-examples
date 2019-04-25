package com.dh.app;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class BasicProducer {
    public static void main(String[] args) {

        System.out.println("Start: basic-java-producer");

        Properties props = new Properties();
        props.put("client.id", "basic-java-producer");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        final String topic = "basic-java-producer-topic";

        for (int i = 1; i <= 5; i++) {
            final String key = "key-" + i;
            final String value = "value-" + i;
            final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
            producer.send(record);
            System.out.println("Produced: " + record.toString());
        }

        producer.close();

        System.out.println("End: basic-java-producer");
    }
}
