package com.fourcats.app.server.geospatial;

import com.fourcats.app.server.geospatial.test.BOLatLong;
import com.fourcats.app.server.geospatial.test.GeoDataGeneratedListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SampleGeoLocationGenerator {
    private int numberOfItemsToPut_;
    private GeoDataGeneratedListener listener_;

    public SampleGeoLocationGenerator(GeoDataGeneratedListener listener) {
        listener_ = listener;
    }

    public BOLatLong readNext(int numberOfItemsToPut) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new FileReader("starbucks_us_locations.csv")
            );
            if (br != null) {
                String line = br.readLine();
                int i = 0;
                while ( i < numberOfItemsToPut && line != null ) {
                    BOLatLong ret = parse(line);
                    if (ret != null) {
                        listener_.didReadOneGeoEntryRecord(ret);
                        i++;
                    }
                    line = br.readLine();
                }

                System.out.println("Inserted " + i + " items in redis" );
            }
        } catch(FileNotFoundException fnf) {
            System.out.println("Error Message:    " + fnf.getMessage());
        } catch(IOException fnf) {
            System.out.println("Error Message:    " + fnf.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e){
                    System.out.println("Error Message:    " + e.getMessage());
                }
            }
        }

        return null;
    }


    private BOLatLong parse(String line) {
        //line looks like this:
        //note first entry is longitude and second is latitude
        // -149.8935557,61.21759217,Starbucks - AK - Anchorage  00001,"601 West Street_601 West 5th Avenue_Anchorage, Alaska 99501_907-277-2477"

        BOLatLong ret = null;
        if (line == null) {
            return ret;
        }

        String commaSeperator = ",";
        String[] items = line.split(commaSeperator);
        if (items != null && items.length > 2) {
            String strLongitude = items[0];
            String strLatitude = items[1];
            String strDescription = items[3];
            if (strLongitude.length() > 0 && strLatitude.length() > 0) {
                ret = new BOLatLong();
                ret.latitude = Double.parseDouble(strLatitude);
                ret.longitude = Double.parseDouble(strLongitude);
                ret.description = strDescription;
            }
        }

        return ret;
    }


}
