package com.fourcats.app.server.geospatial;

public class KafkaProperties {
    public static final String TOPIC = "geo";
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final String CLIENT_ID = "ConsumerDemoID";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int KAFKA_CONNECTION_TIMEOUT = 100000;

    private KafkaProperties() {}
}
