package com.example.mike0.eventsapp.details;

import com.example.mike0.eventsapp.data.adapter.DetailsAdapter;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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
    SQLiteDatabase db;
    Cursor cursor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDisplayAllEventLists() {
        verify(presenter).readEvents();
    }
}