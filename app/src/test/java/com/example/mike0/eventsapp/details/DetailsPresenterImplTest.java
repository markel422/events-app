package com.example.mike0.eventsapp.details;

import android.test.mock.MockCursor;

import com.example.mike0.eventsapp.data.database.DetailsInteractor;
import com.example.mike0.eventsapp.data.database.DetailsInteractor.OnDetailsResponseListener;
import com.example.mike0.eventsapp.data.model.Event;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mike0 on 12/7/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DetailsPresenterImplTest {

    @InjectMocks
    DetailsPresenterImpl presenter;

    @Mock
    DetailsView view;

    @Mock
    DetailsInteractor interactor;

    @Mock
    private Cursor cursor;

    @Captor
    private ArgumentCaptor<OnDetailsResponseListener> listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listener = ArgumentCaptor.forClass(OnDetailsResponseListener.class);
        presenter.init();
    }

    @Test
    public void getEvents_shouldShowEvents() {
        verify(interactor).setDetailsResponseListener(listener.capture());
        presenter.getEvents();
        listener.getValue().getEventsDone(cursor);
        verify(view).showEvents(cursor);
    }

    @After
    public void tearDown() {
        cursor.close();
    }
}