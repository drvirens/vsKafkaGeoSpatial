package com.fourcats.app.server.geospatial;

public class RedisGeoModel {
    private final String key_;
    private final byte[] bytes_;

    public String getKey_() {
        return key_;
    }

    public byte[] getBytes_() {
        return bytes_;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private double latitude;
    private double longitude;
    private String description;

    public RedisGeoModel(String key, byte[] bytes) {
        key_ = key;
        bytes_ = bytes;
    }

    public String getTransportRepresentation() {
        //return new String("{\"itemID\":\"vsn123456789\"}");

        return "latitude:["+latitude+"],longitude:["+longitude+"],key:["+key_+"]";
    }
}
