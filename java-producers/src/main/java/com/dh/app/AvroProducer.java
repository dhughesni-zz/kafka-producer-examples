package com.dh.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import io.confluent.kafka.serializers.subject.TopicNameStrategy;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;

public class AvroProducer {
    public static void main(String[] args) throws IOException, RestClientException {

        /**
         * This seems crazy but the KafkaAvroSerializer sepcified for the
         * VALUE_SERIALIZER will take care of adding the schema to the schema registry
         * based on the naming strategy config defined.
         * https://docs.confluent.io/current/schema-registry/serializer-formatter.html#
         * 
         */

        System.out.println("Start: basic-java-producer");

        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "basic-java-producer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(AbstractKafkaAvroSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());
        // props.put(AbstractKafkaAvroSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY,
        // TopicNameStrategy.class.getName());
        // props.put(AbstractKafkaAvroSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY,
        // TopicRecordNameStrategy.class.getName());

        final KafkaProducer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(props);

        final String topic = "avro-java-producer-topic";

        Schema schema = new Schema.Parser().parse("{ \"namespace\": \"single.avro\", " + "\"type\": \"record\", "
                + "\"name\": \"SingleAvro\"," + "\"fields\": " + "[{" + "\"name\": \"name\", \"type\": \"string\"}, "
                + "{ \"name\": \"type\", \"type\": \"string\"}" + "]}");

        for (int i = 1; i <= 5; i++) {
            final String key = "key-" + i;
            final String value = "value-" + i;

            // Since we're not using code generation, we use GenericRecords to represent the
            // record value
            GenericRecord avro_value = new GenericData.Record(schema);
            avro_value.put("name", value);
            avro_value.put("type", "single_avro");

            final ProducerRecord<String, GenericRecord> record = new ProducerRecord<String, GenericRecord>(topic, key,
                    avro_value);
            producer.send(record);
            System.out.println("Produced: " + record.toString());
        }

        producer.close();

        System.out.println("End: avro-java-producer");
    }
}
