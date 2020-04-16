package com.example.covid_19tracker.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

public class CountryLocation implements Serializable, ClusterItem {
    @SerializedName("lat")
    @Expose
    private String countryLat;
    @SerializedName("long")
    @Expose
    private String countryLong;

    private String Title;
    private String countryName;

    public CountryLocation(String countryLat, String countryLong, String title, String countryName) {
        this.countryLat = countryLat;
        this.countryLong = countryLong;
        Title = title;
        this.countryName=countryName;
    }

    public String getCountryLat() {
        return countryLat;
    }

    public void setCountryLat(String countryLat) {
        this.countryLat = countryLat;
    }

    public String getCountryLong() {
        return countryLong;
    }

    public void setCountryLong(String countryLong) {
        this.countryLong = countryLong;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(Double.valueOf(countryLat), Double.valueOf(countryLong));
    }

    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
