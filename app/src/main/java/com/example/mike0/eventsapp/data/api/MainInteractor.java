package com.example.mike0.eventsapp.data.api;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.mike0.eventsapp.data.database.EventReaderContract;
import com.example.mike0.eventsapp.data.database.EventReaderDBHelper;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.EventMarkerList;
import com.example.mike0.eventsapp.data.model.EventsAPI;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mike0 on 12/5/2017.
 */

public class MainInteractor {

    private static final String TAG = "tag";

    final static String API_KEY = "XEJ7EQTKLAJUUC5LOOPS";

    private Context context;

    EventsService service;

    ArrayList<EventMarkerList> eventMarkList;
    ArrayList<Event> eventList;

    OnEventResponseListener listener;

    public MainInteractor(Context context) {
        this.context = context;
    }

    public interface OnEventResponseListener {
        void onEventResponseDone(List<Event> results);

        void onEventResponseError();

        void getMarkers(List<EventMarkerList> eventMarkerList);
    }

    public void setOnEventResponseListener(OnEventResponseListener listener) {
        this.listener = listener;
    }

    public void init() {
        SQLiteDatabase.loadLibs(context);

        service = new Retrofit.Builder()
                .baseUrl(EventsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EventsService.class);
    }

    public void getEvents(final String title, String lat, String lng) {
        service.getEvents(title, "5mi", lat, lng, API_KEY).enqueue(new Callback<EventsAPI>() {
            @Override
            public void onResponse(Call<EventsAPI> call, Response<EventsAPI> response) {
                if (response.isSuccessful()) {

                    eventMarkList = new ArrayList<>(0);
                    eventList = new ArrayList<>(0);
                    eventList.addAll(response.body().getEvents());

                    listener.onEventResponseDone(eventList);

                    Date date1;
                    String temp = "";
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    for (int i = 0; i < response.body().getEvents().size(); i++) {
                        try {
                            date1 = formatter.parse(response.body().getEvents().get(i).getStart().getLocal());
                            SimpleDateFormat formatter2 = new SimpleDateFormat("M-dd-yyyy h:mm a");
                            temp = formatter2.format(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "Title: " + response.body().getEvents().get(i).getName().getText());

                        String des1 = response.body().getEvents().get(i).getDescription().getText();

                        if (des1 != null && des1.length() > 100) {
                            Log.d(TAG, "Short Description: " + des1.substring(0, 100) + "...");
                        }

                        Log.d(TAG, "Event Time: " + temp);
                        Log.d(TAG, "Event Webpage: " + response.body().getEvents().get(i).getUrl());

                        eventMarkList.add(new EventMarkerList(Double.parseDouble(response.body().getEvents().get(i).getVenue().getLatitude()), Double.parseDouble(response.body().getEvents().get(i).getVenue().getLongitude())));
                    }
                    listener.getMarkers(eventMarkList);
                }
            }

            @Override
            public void onFailure(Call<EventsAPI> call, Throwable t) {
                listener.onEventResponseError();
            }
        });
    }

    public void saveEvent(String title, String desc, String time, String url) {
        SQLiteDatabase db = EventReaderDBHelper.getInstance(context).getWritableDatabase("somePass");

        ContentValues values = new ContentValues();
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_TITLE, title);
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION, desc);
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_TIME, time);
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_URL, url);

        db.insert(
                EventReaderContract.EventEntry.TABLE_NAME,
                null,
                values
        );

        Cursor cursor = db.rawQuery("SELECT * FROM '" + EventReaderContract.EventEntry.TABLE_NAME + "';", null);
        Log.d(TAG, "Rows count: " + cursor.getCount());
        cursor.close();
        db.close();

        // this will throw net.sqlcipher.database.SQLiteException: file is encrypted or is not a database: create locale table failed
        //db = FeedReaderDbHelper.getInstance(this).getWritableDatabase("");
    }
}
