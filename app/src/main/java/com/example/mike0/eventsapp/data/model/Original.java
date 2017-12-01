
package com.example.mike0.eventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Original implements Parcelable
{

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Object width;
    @SerializedName("height")
    @Expose
    private Object height;
    public final static Creator<Original> CREATOR = new Creator<Original>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Original createFromParcel(Parcel in) {
            return new Original(in);
        }

        public Original[] newArray(int size) {
            return (new Original[size]);
        }

    }
    ;

    protected Original(Parcel in) {
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.width = ((Object) in.readValue((Object.class.getClassLoader())));
        this.height = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Original() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(url);
        dest.writeValue(width);
        dest.writeValue(height);
    }

    public int describeContents() {
        return  0;
    }

}
