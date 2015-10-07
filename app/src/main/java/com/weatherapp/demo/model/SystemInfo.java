package com.weatherapp.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SystemInfo implements Parcelable {

    @SerializedName("message")
    private double message;

    @SerializedName("country")
    private String country;

    @SerializedName("sunrise")
    private long sunrise;

    @SerializedName("sunset")
    private long sunset;

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(message);
        out.writeString(country);
        out.writeLong(sunrise);
        out.writeLong(sunset);
    }

    public static final Parcelable.Creator<SystemInfo> CREATOR = new Parcelable.Creator<SystemInfo>() {

        public SystemInfo createFromParcel(Parcel in) {
            return new SystemInfo(in);
        }

        public SystemInfo[] newArray(int size) {
            return new SystemInfo[size];
        }

    };

    private SystemInfo(Parcel in) {
        message = in.readDouble();
        country = in.readString();
        sunrise = in.readLong();
        sunset = in.readLong();
    }

}