package com.fourcats.app.server.geospatial;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.DoubleDeserializer;


import java.util.Collections;
import java.util.Properties;

public class GeoConsumer extends Thread {

    private final String topic_;
    private final KafkaConsumer<Double, Double> consumer_;

    public GeoConsumer(String topic) {
        topic_ = topic;

        Properties properties = new Properties();
        {
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "GeoConsumer");
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DoubleDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DoubleDeserializer.class.getName());
        }
        consumer_ = new KafkaConsumer<>(properties);
        consumer_.subscribe(Collections.singletonList(topic_));
    }

    public void run() {
//        consumer_.subscribe(Collections.singletonList(topic_));
        while (true) {
            ConsumerRecords<Double, Double> records = consumer_.poll(1000);
            if (records.count() == 0) {
                System.out.println("Consumer Poll - no entries ");
                continue;
            }
            for (ConsumerRecord<Double, Double> record : records) {
                dumpRecrod(record);
            }
            consumer_.commitAsync();
        }
    }

    private void dumpRecrod(ConsumerRecord<Double, Double> record) {
        System.out.println("<< RECEIVED: [" + record.toString() + "]");
    }
}
