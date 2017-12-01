
package com.example.mike0.eventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo implements Parcelable
{

    @SerializedName("crop_mask")
    @Expose
    private CropMask cropMask;
    @SerializedName("original")
    @Expose
    private Original original;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("aspect_ratio")
    @Expose
    private String aspectRatio;
    @SerializedName("edge_color")
    @Expose
    private String edgeColor;
    @SerializedName("edge_color_set")
    @Expose
    private Boolean edgeColorSet;
    public final static Creator<Logo> CREATOR = new Creator<Logo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Logo createFromParcel(Parcel in) {
            return new Logo(in);
        }

        public Logo[] newArray(int size) {
            return (new Logo[size]);
        }

    }
    ;

    protected Logo(Parcel in) {
        this.cropMask = ((CropMask) in.readValue((CropMask.class.getClassLoader())));
        this.original = ((Original) in.readValue((Original.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.aspectRatio = ((String) in.readValue((String.class.getClassLoader())));
        this.edgeColor = ((String) in.readValue((String.class.getClassLoader())));
        this.edgeColorSet = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Logo() {
    }

    public CropMask getCropMask() {
        return cropMask;
    }

    public void setCropMask(CropMask cropMask) {
        this.cropMask = cropMask;
    }

    public Original getOriginal() {
        return original;
    }

    public void setOriginal(Original original) {
        this.original = original;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getEdgeColor() {
        return edgeColor;
    }

    public void setEdgeColor(String edgeColor) {
        this.edgeColor = edgeColor;
    }

    public Boolean getEdgeColorSet() {
        return edgeColorSet;
    }

    public void setEdgeColorSet(Boolean edgeColorSet) {
        this.edgeColorSet = edgeColorSet;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cropMask);
        dest.writeValue(original);
        dest.writeValue(id);
        dest.writeValue(url);
        dest.writeValue(aspectRatio);
        dest.writeValue(edgeColor);
        dest.writeValue(edgeColorSet);
    }

    public int describeContents() {
        return  0;
    }

}
