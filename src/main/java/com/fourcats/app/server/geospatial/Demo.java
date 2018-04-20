package com.fourcats.app.server.geospatial;


public class Demo {
    private final GeoProducer producer_;
    private final GeoConsumer consumer_;

    public Demo() {
        String topic = KafkaProperties.TOPIC;
        boolean isAsync = true;
        producer_ = new GeoProducer(topic, isAsync);
        consumer_ = new GeoConsumer(topic);
    }

    public void start() {
        producer_.start();
        consumer_.start();
//        try {
//            producer_.join();
//            consumer_.join();
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        Demo d = new Demo();
        d.start();
    }
}
