package com.example.mike0.eventsapp.main;

import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.EventMarkerList;

import java.util.List;

/**
 * Created by mike0 on 12/4/2017.
 */

public interface MainView {
    void showEvents(List<Event> list);

    void showError();

    void showMarkers(List<EventMarkerList> eventMarkerList);

    void showDetailsActivity();
}
