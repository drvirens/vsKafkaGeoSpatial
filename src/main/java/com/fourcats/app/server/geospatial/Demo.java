package com.fourcats.app.server.geospatial;

import scala.Int;

public class Demo {
    private final GeoProducer producer_;

    public Demo() {
        String topic = KafkaProperties.TOPIC;
        boolean isAsync = true;
        producer_ = new GeoProducer(topic, isAsync);
    }

    public void start() {
        producer_.run();
        try {
            producer_.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Demo d = new Demo();
        d.start();
    }
}
