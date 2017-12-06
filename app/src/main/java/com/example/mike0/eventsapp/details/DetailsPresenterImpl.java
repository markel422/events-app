package com.example.mike0.eventsapp.details;

import android.content.Context;
import android.util.Log;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.database.EventReaderContract;
import com.example.mike0.eventsapp.data.database.EventReaderDBHelper;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by mike0 on 12/5/2017.
 */

public class DetailsPresenterImpl implements DetailsPresenter {

    private static final String TAG = "tag";

    DetailsView detailsView;
    Context context;

    SQLiteDatabase db;
    Cursor cursor;

    public DetailsPresenterImpl(DetailsView detailsView) {
        this.detailsView = detailsView;
        this.context = (Context) detailsView;
    }

    @Override
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
        detailsView.showEvents(cursor);

        db.close();
    }
}
