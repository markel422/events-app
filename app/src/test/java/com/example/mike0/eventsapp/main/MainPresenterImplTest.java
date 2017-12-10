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
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyString;
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

        events = Collections.singletonList(new Event());
    }

    @Test
    public void getEvents_shouldShowEvents() {

        presenter.getEvents(anyString(), anyString(), anyString());
        verify(interactor).getEvents(anyString(), anyString(), anyString());

        presenter.onEventResponseDone(events);
        verify(view).showEvents(events);
    }

    @Test
    public void getEvents_shouldShowError() {

        presenter.getEvents(anyString(), anyString(), anyString());
        verify(interactor).getEvents(anyString(), anyString(), anyString());

        presenter.onEventResponseError();
        verify(view).showError();
    }

    @Test
    public void startDetailsActivity_shouldShowActivity() {

        presenter.startDetailsActivity();
        verify(view).showDetailsActivity();
    }
}
