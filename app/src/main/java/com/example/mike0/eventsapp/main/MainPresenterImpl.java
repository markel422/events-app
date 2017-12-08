package com.example.mike0.eventsapp.main;

import android.content.Context;

import com.example.mike0.eventsapp.data.api.MainInteractor;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.EventMarkerList;

import java.util.List;

/**
 * Created by mike0 on 12/4/2017.
 */

public class MainPresenterImpl implements MainPresenter, MainInteractor.OnEventResponseListener {

    private MainView mainView;

    private MainInteractor interactor;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.interactor = new MainInteractor(context);
    }

    @Override
    public void onEventResponseDone(List<Event> results) {
        mainView.showEvents(results);
    }

    @Override
    public void onEventResponseError() {
        mainView.showError();
    }

    @Override
    public void init() {
        interactor.setOnEventResponseListener(this);
        interactor.init();
    }

    @Override
    public void getEvents(String title, String lat, String lng) {
        interactor.getEvents(title, lat, lng);
    }

    @Override
    public void saveEvent(String title, String desc, String time, String url) {
        interactor.saveEvent(title, desc, time, url);
    }

    @Override
    public void getMarkers(List<EventMarkerList> eventMarkerList) {
        mainView.showMarkers(eventMarkerList);
    }

    @Override
    public void startDetailsActivity() {
        mainView.showDetailsActivity();
    }
}
