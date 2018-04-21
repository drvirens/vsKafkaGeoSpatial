package com.fourcats.app.server.geospatial;

import com.lambdaworks.redis.GeoArgs;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.api.sync.RedisCommands;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RedisDumper {
    private static final String REDIS_GEO_NEARME_OBJECT_KEY = "nearmeobjectkey";
    private RedisClient redisClient_;
    private RedisCommands<String, String> commands_;

    public RedisDumper() {
        redisClient_ = null;
    }

    public void startAsynch() {
        int defaultPort = 6379;
        redisClient_ = RedisClient.create(RedisURI.builder().redis("localhost", defaultPort).build());
        commands_ = redisClient_.connect().sync();
    }

    public void put(double longitutde, double latitude, RedisGeoModel model) {
        RedisFuture<Long> future = null;

        model.setLatitude(latitude);
        model.setLongitude(longitutde);

        commands_.geoadd(REDIS_GEO_NEARME_OBJECT_KEY, longitutde, latitude, model.getTransportRepresentation());

                //georadius(key, 8.6582861, 49.5285695, 1, GeoArgs.Unit.km);
        //assertThat(georadius).hasSize(1).contains("Weinheim");

//        try {
//            future = commands_.geoadd(REDIS_GEO_NEARME_OBJECT_KEY, longitutde, latitude, model.getTransportRepresentation());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (future != null) {
//            //if (!future.isDone()) {
//                Long ret = null;
//                try {
//                    ret = future.get();
//                } catch(ExecutionException ee) {
//                    ee.printStackTrace();
//                } catch (InterruptedException ie) {
//                    ie.printStackTrace();
//                }
//                if (ret != null) {
//
//                }
//            //}
//        }
    }
}
