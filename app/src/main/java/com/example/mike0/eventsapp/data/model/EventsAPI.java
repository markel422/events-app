
package com.example.mike0.eventsapp.data.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsAPI implements Parcelable
{

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    @SerializedName("location")
    @Expose
    private Location location;
    public final static Creator<EventsAPI> CREATOR = new Creator<EventsAPI>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EventsAPI createFromParcel(Parcel in) {
            return new EventsAPI(in);
        }

        public EventsAPI[] newArray(int size) {
            return (new EventsAPI[size]);
        }

    }
    ;

    protected EventsAPI(Parcel in) {
        this.pagination = ((Pagination) in.readValue((Pagination.class.getClassLoader())));
        in.readList(this.events, (com.example.mike0.eventsapp.data.model.Event.class.getClassLoader()));
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
    }

    public EventsAPI() {
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pagination);
        dest.writeList(events);
        dest.writeValue(location);
    }

    public int describeContents() {
        return  0;
    }

}
