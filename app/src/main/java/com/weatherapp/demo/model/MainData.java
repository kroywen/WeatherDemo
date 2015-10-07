package com.weatherapp.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MainData implements Parcelable {

    @SerializedName("temp")
    private double temperature;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("temp_min")
    private double minTemperature;

    @SerializedName("temp_max")
    private double maxTemperature;

    @SerializedName("sea_level")
    private double seaLevel;

    @SerializedName("grnd_level")
    private double groundLevel;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(double groundLevel) {
        this.groundLevel = groundLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(temperature);
        out.writeDouble(pressure);
        out.writeInt(humidity);
        out.writeDouble(minTemperature);
        out.writeDouble(maxTemperature);
        out.writeDouble(seaLevel);
        out.writeDouble(groundLevel);
    }

    public static final Parcelable.Creator<MainData> CREATOR = new Parcelable.Creator<MainData>() {

        public MainData createFromParcel(Parcel in) {
            return new MainData(in);
        }

        public MainData[] newArray(int size) {
            return new MainData[size];
        }

    };

    private MainData(Parcel in) {
        temperature = in.readDouble();
        pressure = in.readDouble();
        humidity = in.readInt();
        minTemperature = in.readDouble();
        maxTemperature = in.readDouble();
        seaLevel = in.readDouble();
        groundLevel = in.readDouble();
    }

}