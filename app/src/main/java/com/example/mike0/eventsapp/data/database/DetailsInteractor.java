package com.example.mike0.eventsapp.data.database;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by mike0 on 12/8/2017.
 */

public class DetailsInteractor {

    private static final String TAG = "tag";

    private Context context;
    private OnDetailsResponseListener listener;

    SQLiteDatabase db;
    Cursor cursor;

    public DetailsInteractor(Context context) {
        this.context = context;
    }

    public interface OnDetailsResponseListener {
        void getEventsDone(Cursor cursor);
    }

    public void setDetailsResponseListener(OnDetailsResponseListener listener) {
        this.listener = listener;
    }

    public void init() {
        SQLiteDatabase.loadLibs(context);
    }

    public void readEvents() {
        db = EventReaderDBHelper.getInstance(context).getWritableDatabase("somePass");

        String[] projection = {
                EventReaderContract.EventEntry._ID,
                EventReaderContract.EventEntry.COLUMN_NAME_TITLE,
                EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION,
                EventReaderContract.EventEntry.COLUMN_NAME_TIME,
                EventReaderContract.EventEntry.COLUMN_NAME_URL
        };

        cursor = db.query(
                EventReaderContract.EventEntry.TABLE_NAME,        // TABLE
                projection,                  // Projection
                null,                        // Selection (WHERE)
                null,                        // Values for selection
                null,                        // Group by
                null,                        // Filters
                null                         // Sort order
        );

        while (cursor.moveToNext()) {
            long entryId = cursor.getLong(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry._ID));
            String entryTitle = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_TITLE));
            String entryDesc = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION));
            String entryTime = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_TIME));
            String entryUrl = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_URL));

            Log.d(TAG, "readRecord: id: " + entryId + " title: " + entryTitle + " description: " + entryDesc + " time: " + entryTime + " url: " + entryUrl);
        }

        //cursor.close();
        listener.getEventsDone(cursor);

        db.close();
    }
}
