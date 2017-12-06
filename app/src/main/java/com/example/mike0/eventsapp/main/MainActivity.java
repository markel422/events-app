package com.example.mike0.eventsapp.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, ItemClickListener, MainView {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;
    private static final String TAG = "tag";

    final static String API_KEY = "XEJ7EQTKLAJUUC5LOOPS";

    SupportMapFragment mapFragment;

    MainPresenterImpl presenter;

    SharedPreferences savedEventPref;

    EditText searchTV;

    Button detailsBtn;

    //EventsService service;

    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;

    LinearLayout eventLayoutList;

    TextView totalEvents;

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;

    String lat, lng, editTextSearch;

    int eventSize;

    List<EventMarkerList> eventMarkList;
    List<Event> eventList;
    List<String> savedEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this);

        savedEventPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        savedEventList = new ArrayList<>(0);

        /*File databasePath = getDatabasePath("myEventReader.db");
        databasePath.delete();*/

        eventLayoutList = (LinearLayout) findViewById(R.id.linear_events_list);

        totalEvents = (TextView) findViewById(R.id.event_results_total);

        detailsBtn = (Button) findViewById(R.id.event_btn_details);
        detailsBtn.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        presenter.init();
        getInitialEventSearch();

        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        Gson gson = new Gson();
        String response = savedEventPref.getString("savedEvents", null);

        /*SharedPreferences.Editor editor = savedEventPref.edit();

        editor.remove("savedEvents");
        editor.apply();*/

        if (savedEventList.isEmpty() && response != null) {
            savedEventList = gson.fromJson(response, new TypeToken<List<String>>() {
            }.getType());

            Log.d(TAG, "SAVED savedList size: " + savedEventList.size());
            for (int j = 0; j < savedEventList.size(); j++) {
                Log.d(TAG, "SAVED savedList: " + savedEventList.get(j));
            }
        }

        setUpRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = savedEventPref.edit();

        Gson gson = new Gson();

        String json = gson.toJson(savedEventList);

        editor.remove("savedEvents").apply();

        editor.putString("savedEvents", json);
        editor.apply();

        Log.d(TAG, "onPause saved info: " + json);
    }

    public void getInitialEventSearch() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Enter an Event Title");
        dialog.setContentView(R.layout.dialog_eventsearch);
        // Get the layout inflater
        dialog.show();

        searchTV = (EditText) dialog.findViewById(R.id.search_tv);
        Button okBtn = (Button) dialog.findViewById(R.id.btn_ok);
        final Button cancelBtn = (Button) dialog.findViewById(R.id.btn_cancel);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!searchTV.getText().toString().equals("")) {
                    editTextSearch = searchTV.getText().toString();
                    presenter.getEvents(editTextSearch, lat, lng);
                } else {
                    searchTV.setText("events");
                    presenter.getEvents(searchTV.getText().toString(), lat, lng);
                }
                dialog.cancel();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchTV.setText("events");
                presenter.getEvents(searchTV.getText().toString(), lat, lng);
                dialog.cancel();
            }
        });
    }

    private void setUpRecyclerView() {
        eventsRecyclerView = (RecyclerView) findViewById(R.id.recycler_events);
        eventsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        eventsRecyclerView.setLayoutManager(linearLayoutManager);

        eventsAdapter = new EventsAdapter(this, new ArrayList<Event>(0), savedEventList);
        eventsRecyclerView.setAdapter(eventsAdapter);

        eventsAdapter.setClickListener(this);
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

    @Override
    public void onClick(View view, final int position) {
        Log.d(TAG, "onClick: " + position);

        new AlertDialog.Builder(MainActivity.this)
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

                        if (savedEventList.isEmpty()) {
                            savedEventList.add(eventList.get(position).getUrl());
                            eventsAdapter.getSavedEventState(savedEventList);
                            eventsAdapter.notifyItemChanged(position);
                            presenter.saveEvent(eventList.get(position).getName().getText(), eventList.get(position).getDescription().getText(), stringDate, eventList.get(position).getUrl());
                            Toast.makeText(MainActivity.this, "Event Saved.", Toast.LENGTH_SHORT).show();

                        } else if (!savedEventList.isEmpty()) {
                            if (savedEventList.contains(eventList.get(position).getUrl())) {

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("\"" + eventList.get(position).getName().getText() + "\"")
                                        .setMessage("This event already exists in the database.")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .show();
                            } else {
                                savedEventList.add(eventList.get(position).getUrl());
                                eventsAdapter.getSavedEventState(savedEventList);
                                eventsAdapter.notifyItemChanged(position);
                                presenter.saveEvent(eventList.get(position).getName().getText(), eventList.get(position).getDescription().getText(), stringDate, eventList.get(position).getUrl());
                                Toast.makeText(MainActivity.this, "Event Saved.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.d(TAG, "savedEventList size: " + savedEventList.size());
                        for (int j = 0; j < savedEventList.size(); j++) {
                            Log.d(TAG, "savedEventList: " + savedEventList.get(j));
                        }

                        Gson gson = new Gson();
                        String response = savedEventPref.getString("savedEvents", null);

                        if (savedEventList.isEmpty() && !response.isEmpty()) {

                            savedEventList = gson.fromJson(response, new TypeToken<List<String>>() {
                            }.getType());
                            eventsAdapter.getSavedEventState(savedEventList);
                            Log.d(TAG, "SAVED savedList size: " + savedEventList.size());

                            for (int j = 0; j < savedEventList.size(); j++) {
                                Log.d(TAG, "SAVED savedList: " + savedEventList.get(j));
                            }
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Gson gson = new Gson();
                        String response = savedEventPref.getString("savedEvents", null);

                        if (savedEventList.isEmpty() && !response.isEmpty()) {
                            savedEventList = gson.fromJson(response, new TypeToken<List<String>>() {
                            }.getType());

                            Log.d(TAG, "SAVED savedList size: " + savedEventList.size());
                            for (int j = 0; j < savedEventList.size(); j++) {
                                Log.d(TAG, "SAVED savedList: " + savedEventList.get(j));
                            }
                        }
                        eventsAdapter.notifyItemChanged(position);

                        Log.d(TAG, "savedEventList size: " + savedEventList.size());
                        for (int j = 0; j < savedEventList.size(); j++) {
                            Log.d(TAG, "savedEventList: " + savedEventList.get(j));
                        }

                        //readEvents();

                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void showEvents(List<Event> list) {
        String title = searchTV.getText().toString();
        eventList = list;

        totalEvents.setText("Total results near your location: " + eventList.size() + " for " + "\"" + title + "\"");
        eventsAdapter.updateDataSet(eventList);
    }

    @Override
    public void showEventsError() {
        Toast.makeText(MainActivity.this, "Network Failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMarkers(List<EventMarkerList> eventMarkerList) {
        for (int i = 0; i < eventMarkerList.size(); i++) {
            LatLng eventsLocation = new LatLng(eventMarkerList.get(i).getLat(), eventMarkerList.get(i).getLng());
            mMap.addMarker(new MarkerOptions().position(eventsLocation).title(eventList.get(i).getName().getText()));
        }
    }

    @Override
    public void showDetailsActivity() {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_btn_details:
                presenter.startDetailsActivity();
                break;
        }
    }
}
