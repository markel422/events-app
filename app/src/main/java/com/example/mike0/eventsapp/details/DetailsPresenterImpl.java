package com.example.mike0.eventsapp.details;

import android.content.Context;

import com.example.mike0.eventsapp.data.database.DetailsInteractor;

import net.sqlcipher.Cursor;

/**
 * Created by mike0 on 12/5/2017.
 */

public class DetailsPresenterImpl implements DetailsPresenter, DetailsInteractor.OnDetailsResponseListener {

    DetailsView detailsView;

    DetailsInteractor interactor;

    public DetailsPresenterImpl(DetailsView detailsView, Context context) {
        this.detailsView = detailsView;
        interactor = new DetailsInteractor(context);
    }

    @Override
    public void init() {
        interactor.setDetailsResponseListener(this);
        interactor.init();
    }

    @Override
    public void getEvents() {
        interactor.readEvents();
    }

    @Override
    public void getEventsDone(Cursor cursor) {
        detailsView.showEvents(cursor);
    }
}
