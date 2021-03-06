
package com.example.mike0.eventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Start implements Parcelable
{

    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("local")
    @Expose
    private String local;
    @SerializedName("utc")
    @Expose
    private String utc;
    public final static Creator<Start> CREATOR = new Creator<Start>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Start createFromParcel(Parcel in) {
            return new Start(in);
        }

        public Start[] newArray(int size) {
            return (new Start[size]);
        }

    }
    ;

    protected Start(Parcel in) {
        this.timezone = ((String) in.readValue((String.class.getClassLoader())));
        this.local = ((String) in.readValue((String.class.getClassLoader())));
        this.utc = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Start() {
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(timezone);
        dest.writeValue(local);
        dest.writeValue(utc);
    }

    public int describeContents() {
        return  0;
    }

}
