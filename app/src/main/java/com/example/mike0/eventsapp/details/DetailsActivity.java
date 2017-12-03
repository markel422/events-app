package com.example.mike0.eventsapp.details;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.adapter.DetailsAdapter;
import com.example.mike0.eventsapp.data.database.EventReaderContract.EventEntry;
import com.example.mike0.eventsapp.data.database.EventReaderDBHelper;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.ItemClickListener;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity implements ItemClickListener {

    private static final String TAG = "tag";

    private RecyclerView eventsRecyclerView;
    private DetailsAdapter detailsAdapter;

    SQLiteDatabase db;
    android.database.Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SQLiteDatabase.loadLibs(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void readEvents() {
        db = EventReaderDBHelper.getInstance(this).getWritableDatabase("somePass");

        eventsRecyclerView = (RecyclerView) findViewById(R.id.recycler_events);
        eventsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        eventsRecyclerView.setLayoutManager(linearLayoutManager);


        String[] projection = {
                EventEntry._ID,
                EventEntry.COLUMN_NAME_TITLE,
                EventEntry.COLUMN_NAME_DESCRIPTION,
                EventEntry.COLUMN_NAME_TIME,
                EventEntry.COLUMN_NAME_URL
        };
        /*
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";


        String[] selectionArg = {
                "Record title"
        };
        String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + "DESC";
        */

        cursor = db.query(
                EventEntry.TABLE_NAME,        // TABLE
                projection,                  // Projection
                null,                        // Selection (WHERE)
                null,                        // Values for selection
                null,                        // Group by
                null,                        // Filters
                null                         // Sort order
        );

        while (cursor.moveToNext()) {
            //StringBuilder dataResult = new StringBuilder(String.valueOf(resultTV.getText().toString()));
            long entryId = cursor.getLong(cursor.getColumnIndexOrThrow(EventEntry._ID));
            String entryTitle = cursor.getString(cursor.getColumnIndexOrThrow(EventEntry.COLUMN_NAME_TITLE));
            String entryDesc = cursor.getString(cursor.getColumnIndexOrThrow(EventEntry.COLUMN_NAME_DESCRIPTION));
            String entryTime = cursor.getString(cursor.getColumnIndexOrThrow(EventEntry.COLUMN_NAME_TIME));
            String entryUrl = cursor.getString(cursor.getColumnIndexOrThrow(EventEntry.COLUMN_NAME_URL));

            Log.d(TAG, "readRecord: id: " + entryId + " title: " + entryTitle + " description: " + entryDesc + " time: " + entryTime + " url: " + entryUrl);
            //resultTV.setText(dataResult.append(String.format(getString(R.string.lbl_result), entryId, entryTitle, entryDesc, entryTime, entryUrl)));
        }

        detailsAdapter = new DetailsAdapter(this, cursor);
        eventsRecyclerView.setAdapter(detailsAdapter);

        //cursor.close();
        db.close();
        //detailsAdapter.updateDataSet(eventList);
        detailsAdapter.setClickListener(this);
    }

    public void ShowEventList(View view) {
        readEvents();
    }

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "onClick: " + position);
    }
}
