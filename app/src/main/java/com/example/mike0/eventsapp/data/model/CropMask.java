
package com.example.mike0.eventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropMask implements Parcelable
{

    @SerializedName("top_left")
    @Expose
    private TopLeft topLeft;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    public final static Creator<CropMask> CREATOR = new Creator<CropMask>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CropMask createFromParcel(Parcel in) {
            return new CropMask(in);
        }

        public CropMask[] newArray(int size) {
            return (new CropMask[size]);
        }

    }
    ;

    protected CropMask(Parcel in) {
        this.topLeft = ((TopLeft) in.readValue((TopLeft.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public CropMask() {
    }

    public TopLeft getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(TopLeft topLeft) {
        this.topLeft = topLeft;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(topLeft);
        dest.writeValue(width);
        dest.writeValue(height);
    }

    public int describeContents() {
        return  0;
    }

}
