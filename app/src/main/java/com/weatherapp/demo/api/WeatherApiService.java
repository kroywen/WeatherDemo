package com.weatherapp.demo.api;

import com.weatherapp.demo.model.WeatherData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WeatherApiService {

    public static final String API_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String ICON_URL = "http://openweathermap.org/img/w/%s.png";

    @GET("weather")
    Call<WeatherData> getWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude
    );

}