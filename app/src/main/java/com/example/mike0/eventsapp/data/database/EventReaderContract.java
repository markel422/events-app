package com.example.mike0.eventsapp.data.database;

import android.provider.BaseColumns;

/**
 * Created by mike0 on 11/22/2017.
 */

public final class EventReaderContract {

    private EventReaderContract() {

    }

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_URL = "url";
    }
}
