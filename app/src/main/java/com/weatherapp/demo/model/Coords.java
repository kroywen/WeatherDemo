package com.weatherapp.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Coords implements Parcelable {

    @SerializedName("lon")
    private double longitude;

    @SerializedName("lat")
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(latitude);
        out.writeDouble(longitude);
    }

    public static final Parcelable.Creator<Coords> CREATOR = new Parcelable.Creator<Coords>() {

        public Coords createFromParcel(Parcel in) {
            return new Coords(in);
        }

        public Coords[] newArray(int size) {
            return new Coords[size];
        }

    };

    private Coords(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

}