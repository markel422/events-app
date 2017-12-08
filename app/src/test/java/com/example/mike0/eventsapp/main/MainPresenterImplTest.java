package com.example.mike0.eventsapp.main;

import com.example.mike0.eventsapp.data.api.MainInteractor;
import com.example.mike0.eventsapp.data.model.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by mike0 on 12/6/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImplTest {

    @InjectMocks
    MainPresenterImpl presenter;

    @Mock
    MainView view;

    @Mock
    MainInteractor interactor;

    private List<Event> events;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        events = new ArrayList<>(0);
    }

    @Test
    public void getEvents_shouldShowEvents() {
        String title = "club";
        String lat = "33.9017";
        String lng = "-84.4463";

        presenter.getEvents(title, lat, lng);
        verify(interactor).getEvents(title, lat, lng);

        presenter.onEventResponseDone(events);
        verify(view).showEvents(events);
    }

    @Test
    public void getEvents_shouldShowError() {
        String title = "club";
        String lat = "33.9017";
        String lng = "-84.4463";

        presenter.getEvents(title, lat, lng);
        verify(interactor).getEvents(title, lat, lng);

        presenter.onEventResponseError();
        verify(view).showError();
    }
}
