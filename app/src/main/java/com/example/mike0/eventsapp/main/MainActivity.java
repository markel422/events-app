package com.example.mike0.eventsapp.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike0.eventsapp.data.FingerprintHandler;
import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.adapter.EventsAdapter;
import com.example.mike0.eventsapp.data.api.EventsService;
import com.example.mike0.eventsapp.data.database.EventReaderContract.EventEntry;
import com.example.mike0.eventsapp.data.database.EventReaderDBHelper;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.EventMarkerList;
import com.example.mike0.eventsapp.data.model.EventsAPI;
import com.example.mike0.eventsapp.data.model.ItemClickListener;
import com.example.mike0.eventsapp.details.DetailsActivity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, ItemClickListener{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;
    private static final String TAG = "tag";

    final static String API_KEY = "XEJ7EQTKLAJUUC5LOOPS";

    EventsService service;

    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;

    LinearLayout eventLayoutList;

    TextView totalEvents;

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;

    String lat, lng;

    int eventSize;

    ArrayList<EventMarkerList> eventMarkList;
    ArrayList<Event> eventList;
    ArrayList<Integer> savedEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);

        /*File databasePath = getDatabasePath("myEventReader.db");
        databasePath.delete();*/

        eventLayoutList = (LinearLayout) findViewById(R.id.linear_events_list);

        totalEvents = (TextView) findViewById(R.id.event_results_total);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        init();
        setUpRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //database.close();
    }

    public void init() {
        service = new Retrofit.Builder()
                .baseUrl(EventsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EventsService.class);
    }

    private void setUpRecyclerView() {
        eventsRecyclerView = (RecyclerView) findViewById(R.id.recycler_events);
        eventsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        eventsRecyclerView.setLayoutManager(linearLayoutManager);

        eventsAdapter = new EventsAdapter(this, new ArrayList<Event>(0));
        eventsRecyclerView.setAdapter(eventsAdapter);

        eventsAdapter.setClickListener(this);
    }

    public void getEvents(String title, String lat, String lng) {
        service.getEvents(title, "5mi", lat, lng, API_KEY).enqueue(new Callback<EventsAPI>() {
            @Override
            public void onResponse(Call<EventsAPI> call, Response<EventsAPI> response) {
                if (response.isSuccessful()) {
                    totalEvents.setText("Total results near your location: " + response.body().getEvents().size());

                    eventsAdapter.updateDataSet(response.body().getEvents());
                    eventMarkList = new ArrayList<>(0);
                    eventList = new ArrayList<>(0);

                    Date date1 = null;
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
                        //Log.d(TAG, "onResponse: Description: " + response.body().getEvents().get(i).getDescription().getText());
                        String des1 = response.body().getEvents().get(i).getDescription().getText();

                        if (des1 != null) {
                            Log.d(TAG, "Short Description: " + des1.substring(0, 100) + "...");
                        }

                        Log.d(TAG, "Event Time: " + temp);
                        Log.d(TAG, "Event Webpage: " + response.body().getEvents().get(i).getUrl());

                        eventMarkList.add(new EventMarkerList(Double.parseDouble(response.body().getEvents().get(i).getVenue().getLatitude()), Double.parseDouble(response.body().getEvents().get(i).getVenue().getLongitude())));
                        eventList.add(response.body().getEvents().get(i));

                        LatLng eventsLocation = new LatLng(eventMarkList.get(i).getLat(), eventMarkList.get(i).getLng());
                        mMap.addMarker(new MarkerOptions().position(eventsLocation).title(response.body().getEvents().get(i).getName().getText()));
                    }



                    //Log.d(TAG, "Event Time: " + response.body().getEvents().get(0).getStart().getLocal());
                    //Log.d(TAG, "Time Converted: " + date1);

                    //Log.d(TAG, "onResponse: " + formatter.parse(response.body().getEvents().get(0).getStart().getLocal()));

                    Log.d(TAG, "Size: " + response.body().getEvents().size());
                    eventSize = response.body().getEvents().size();
                }
            }

            @Override
            public void onFailure(Call<EventsAPI> call, Throwable t) {
                Log.d(TAG, "Network Failed");
            }
        });
    }

    private void saveEvent(String title, String desc, String time, String url) {
        SQLiteDatabase db = EventReaderDBHelper.getInstance(this).getWritableDatabase("somePass");

        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME_TITLE, title);
        values.put(EventEntry.COLUMN_NAME_DESCRIPTION, desc);
        values.put(EventEntry.COLUMN_NAME_TIME, time);
        values.put(EventEntry.COLUMN_NAME_URL, url);

        db.insert(
                EventEntry.TABLE_NAME,
                null,
                values
        );

        Cursor cursor = db.rawQuery("SELECT * FROM '" + EventEntry.TABLE_NAME + "';", null);
        Log.d(MainActivity.class.getSimpleName(), "Rows count: " + cursor.getCount());
        cursor.close();
        db.close();

        // this will throw net.sqlcipher.database.SQLiteException: file is encrypted or is not a database: create locale table failed
        //db = FeedReaderDbHelper.getInstance(this).getWritableDatabase("");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());

            Log.d(TAG, "onSuccess: " + location.getLatitude());
            Log.d(TAG, "onSuccess: " + location.getLongitude());

            // Add a marker in Current Location and move the camera
            LatLng deviceLocation = new LatLng(location.getLatitude(), location.getLongitude());
            //LatLng eventLocation = new LatLng(33.885884, -84.40442999999999);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(deviceLocation, 12));

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates("gps", 5000, 100, (android.location.LocationListener) locationListener);
                }
            };


            getEvents("club", lat, lng);


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker arg0) {
                    if (arg0.getTitle().equals(arg0.getTitle())) { // if marker source is clicked
                        int eventPos = eventsAdapter.getItemNamePosition(arg0.getTitle());
                        eventsRecyclerView.smoothScrollToPosition(eventPos);
                    }

                    return false;
                }

            });

        } else {
            // Show rationale and request permission.
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void startDetailsActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

    public int getEventSaved(int position) {
        return position;
    }

    @Override
    public void onClick(View view, final int position) {
        Log.d(TAG, "onClick: " + position);
        savedEventList = new ArrayList<>(0);
        savedEventList.add(new Integer(position));
        new AlertDialog.Builder(this)
                .setTitle("\"" + eventList.get(position).getName().getText() + "\"")
                .setMessage("Would you like to save this event to your database?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Date date1 = null;
                        String stringDate = "";
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                            date1 = formatter.parse(eventList.get(position).getStart().getLocal());
                            SimpleDateFormat formatter2 = new SimpleDateFormat("M-dd-yyyy h:mm a");
                            stringDate = formatter2.format(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String des1 = eventList.get(position).getDescription().getText();

                        saveEvent(eventList.get(position).getName().getText(), eventList.get(position).getDescription().getText() , stringDate, eventList.get(position).getUrl());
                        Toast.makeText(MainActivity.this, "Event Saved.", Toast.LENGTH_SHORT).show();
                        /*for (int j = 0; j < savedEventList.size(); j++) {
                            Log.d(TAG, "savedEventList: " + savedEventList.get(j).toString());
                        }*/
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < savedEventList.size(); j++) {

                        }
                        Log.d(TAG, "savedEventList: " + savedEventList.size());
                        Toast.makeText(MainActivity.this, "Declined", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
