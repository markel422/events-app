
package com.example.mike0.eventsapp.data.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable
{

    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("localized_address_display")
    @Expose
    private String localizedAddressDisplay;
    @SerializedName("localized_area_display")
    @Expose
    private String localizedAreaDisplay;
    @SerializedName("localized_multi_line_address_display")
    @Expose
    private List<String> localizedMultiLineAddressDisplay = null;
    public final static Creator<Address> CREATOR = new Creator<Address>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return (new Address[size]);
        }

    }
    ;

    protected Address(Parcel in) {
        this.address1 = ((String) in.readValue((String.class.getClassLoader())));
        this.address2 = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.region = ((String) in.readValue((String.class.getClassLoader())));
        this.postalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.localizedAddressDisplay = ((String) in.readValue((String.class.getClassLoader())));
        this.localizedAreaDisplay = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.localizedMultiLineAddressDisplay, (String.class.getClassLoader()));
    }

    public Address() {
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocalizedAddressDisplay() {
        return localizedAddressDisplay;
    }

    public void setLocalizedAddressDisplay(String localizedAddressDisplay) {
        this.localizedAddressDisplay = localizedAddressDisplay;
    }

    public String getLocalizedAreaDisplay() {
        return localizedAreaDisplay;
    }

    public void setLocalizedAreaDisplay(String localizedAreaDisplay) {
        this.localizedAreaDisplay = localizedAreaDisplay;
    }

    public List<String> getLocalizedMultiLineAddressDisplay() {
        return localizedMultiLineAddressDisplay;
    }

    public void setLocalizedMultiLineAddressDisplay(List<String> localizedMultiLineAddressDisplay) {
        this.localizedMultiLineAddressDisplay = localizedMultiLineAddressDisplay;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(address1);
        dest.writeValue(address2);
        dest.writeValue(city);
        dest.writeValue(region);
        dest.writeValue(postalCode);
        dest.writeValue(country);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(localizedAddressDisplay);
        dest.writeValue(localizedAreaDisplay);
        dest.writeList(localizedMultiLineAddressDisplay);
    }

    public int describeContents() {
        return  0;
    }

}
