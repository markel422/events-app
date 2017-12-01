
package com.example.mike0.eventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name implements Parcelable
{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("html")
    @Expose
    private String html;
    public final static Creator<Name> CREATOR = new Creator<Name>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Name createFromParcel(Parcel in) {
            return new Name(in);
        }

        public Name[] newArray(int size) {
            return (new Name[size]);
        }

    }
    ;

    protected Name(Parcel in) {
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.html = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Name() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(text);
        dest.writeValue(html);
    }

    public int describeContents() {
        return  0;
    }

}
