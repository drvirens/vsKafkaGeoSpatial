package com.fourcats.app.server.geospatial;

import com.fourcats.app.server.geospatial.test.BOLatLong;
import com.fourcats.app.server.geospatial.test.GeoDataGeneratedListener;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.DoubleSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

public class GeoProducer extends Thread implements GeoDataGeneratedListener {
    private final KafkaProducer<Double, Double> producer_;
    private final String topic_;
    private final boolean isAsync_;
    private SampleGeoLocationGenerator sampleGeoLocationGenerator_;
    private boolean shouldContinueLoop_;


    public GeoProducer(String topic, boolean isAsync) {
        topic_ = topic;
        isAsync_ = isAsync;

        Properties properties = new Properties();
            {
                properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
                properties.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaProperties.PRODUCER_CLIENT_ID);
                properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());
                properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());
            }

        producer_ = new KafkaProducer<>(properties);
        shouldContinueLoop_ = true;
        sampleGeoLocationGenerator_ = new SampleGeoLocationGenerator(this);
    }

    public void run() {
        int numberOfItemsToPut = 2;
        sampleGeoLocationGenerator_.readNext(numberOfItemsToPut);






//        int messageNo = 1;
//        while (true) {
//            Double key = Double.valueOf(messageNo);
//            Double value = 1.0;
//            ProducerRecord<Double, Double> record = new ProducerRecord<>(topic_, key, value);
//
//            GeoProducerCallBack callBack = new GeoProducerCallBack(key, value);
//
//            try {
//                Future<RecordMetadata> ret = producer_.send(record, callBack);
//                if (ret != null) {
//                    RecordMetadata metadata = ret.get();
//                    if (metadata != null) {
//                        //System.out.println("send returned non null: [" + metadata.toString() + "]");
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            ++messageNo;
//        }
    }

    private void fireProduceEvent(BOLatLong eventData) {
        Double key = eventData.longitude; //Double.valueOf(eventData.longitude);
        Double value = eventData.latitude; //1.0;
        ProducerRecord<Double, Double> record = new ProducerRecord<>(topic_, key, value);

        GeoProducerCallBack callBack = new GeoProducerCallBack(key, value);

        try {
            Future<RecordMetadata> ret = producer_.send(record, callBack);
            if (ret != null) {
                RecordMetadata metadata = ret.get();
                if (metadata != null) {
                    //System.out.println("send returned non null: [" + metadata.toString() + "]");
                    shouldContinueLoop_ = true;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            shouldContinueLoop_ = false;
        }
        shouldContinueLoop_ = false;
    }

    @Override
    public void didReadOneGeoEntryRecord(BOLatLong record) {
        System.out.println("read one record");
        fireProduceEvent(record);
    }
}

class GeoProducerCallBack implements Callback {
    private final long startTime_;
    private final Double key_;
    private final Double value_;

    public GeoProducerCallBack(Double key, Double value) {
        startTime_ = System.currentTimeMillis();
        key_ = key;
        value_ = value;
    }

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        long curr = System.currentTimeMillis();
        long elapsed = curr - startTime_;
        if (recordMetadata != null && e == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(">> SENT: Key: [");
            sb.append(key_);
            sb.append("], value=[");
            sb.append(value_);
            sb.append("], partition=[");
            sb.append(recordMetadata.partition());
            sb.append("] ,elapsed=[");
            sb.append(elapsed);
            sb.append("ms]");


            System.out.println(sb.toString());
        }

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ei) {
//            ei.printStackTrace();
//        }
    }
}
