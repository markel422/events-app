package com.example.mike0.eventsapp.data.model;

/**
 * Created by mike0 on 11/20/2017.
 */

public class EventMarkerList {

    private double lat, lng;

    public EventMarkerList(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
