package com.example.mike0.eventsapp.main;

/**
 * Created by mike0 on 12/4/2017.
 */

public interface MainPresenter {

    void init();

    void getEvents(String title, String lat, String lng);

    void saveEvent(String title, String desc, String time, String url);

    void startDetailsActivity();

}
