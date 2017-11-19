package com.example.mike0.eventsapp.data.api;

import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.EventsAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mike0 on 11/18/2017.
 */

public interface EventsService {

    //https://www.eventbriteapi.com/v3/events/search/?q=club&location.within=5mi&location.latitude=33.90173648&location.longitude=-84.44637612&token=XEJ7EQTKLAJUUC5LOOPS
    String BASE_URL = "https://www.eventbriteapi.com/v3/events/search/";

    @GET(" ")
    Call<EventsAPI> getEvents(@Query("q") String eventTitle, @Query("location.within") String distance, @Query("location.latitude") String lat, @Query("location.longitude") String lng, @Query("token") String key);
}
