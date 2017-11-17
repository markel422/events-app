
package com.example.mike0.eventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopLeft implements Parcelable
{

    @SerializedName("x")
    @Expose
    private Integer x;
    @SerializedName("y")
    @Expose
    private Integer y;
    public final static Parcelable.Creator<TopLeft> CREATOR = new Creator<TopLeft>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TopLeft createFromParcel(Parcel in) {
            return new TopLeft(in);
        }

        public TopLeft[] newArray(int size) {
            return (new TopLeft[size]);
        }

    }
    ;

    protected TopLeft(Parcel in) {
        this.x = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.y = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public TopLeft() {
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(x);
        dest.writeValue(y);
    }

    public int describeContents() {
        return  0;
    }

}
