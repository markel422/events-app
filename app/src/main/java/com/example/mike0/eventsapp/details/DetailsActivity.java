package com.example.mike0.eventsapp.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.database.EventReaderContract;
import com.example.mike0.eventsapp.data.database.EventReaderDBHelper;

import net.sqlcipher.database.SQLiteDatabase;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SQLiteDatabase.loadLibs(this);

        resultTV = (TextView) findViewById(R.id.event_results_total);
    }

    private void readEvents() {
        SQLiteDatabase db = EventReaderDBHelper.getInstance(this).getWritableDatabase("somePass");

        String[] projection = {
                EventReaderContract.EventEntry._ID,
                EventReaderContract.EventEntry.COLUMN_NAME_TITLE,
                EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION,
                EventReaderContract.EventEntry.COLUMN_NAME_TIME,
                EventReaderContract.EventEntry.COLUMN_NAME_URL
        };
        /*
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";


        String[] selectionArg = {
                "Record title"
        };
        String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + "DESC";
        */

        android.database.Cursor cursor = db.query(
                EventReaderContract.EventEntry.TABLE_NAME,        // TABLE
                projection,                  // Projection
                null,                        // Selection (WHERE)
                null,                        // Values for selection
                null,                        // Group by
                null,                        // Filters
                null                         // Sort order
        );
        while(cursor.moveToNext()) {
            StringBuilder dataResult = new StringBuilder(String.valueOf(resultTV.getText().toString()));
            long entryId = cursor.getLong(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry._ID));
            String entryTitle = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_TITLE));
            String entryDesc = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION));
            String entryTime = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_TIME));
            String entryUrl = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_URL));
            Log.d(TAG, "readRecord: id: " +  entryId + " title: " +  entryTitle + " description: " + entryDesc + " time: " + entryTime + " url: " + entryUrl);
            resultTV.setText(dataResult.append(String.format(getString(R.string.lbl_result), entryId, entryTitle, entryDesc, entryTime, entryUrl)));
        }
        cursor.close();
        db.close();
    }

    public void ShowEventList(View view) {
        readEvents();
    }
}
