package com.weatherapp.demo.ui;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weatherapp.demo.R;
import com.weatherapp.demo.api.WeatherApiService;
import com.weatherapp.demo.model.Coords;
import com.weatherapp.demo.model.MainData;
import com.weatherapp.demo.model.SystemInfo;
import com.weatherapp.demo.model.Weather;
import com.weatherapp.demo.model.WeatherData;
import com.weatherapp.demo.model.Wind;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class WeatherActivity extends BaseLocationActivity {

    public static final String WEATHER_DATA_KEY = "weather_data";

    @Bind(R.id.city) TextView mCityView;
    @Bind(R.id.icon) ImageView mIconView;
    @Bind(R.id.temperature) TextView mTemperatureView;
    @Bind(R.id.weather) TextView mWeatherView;
    @Bind(R.id.description) TextView mDescriptionView;
    @Bind(R.id.pressure) TextView mPressureView;
    @Bind(R.id.humidity) TextView mHumidityView;
    @Bind(R.id.wind) TextView mWindView;
    @Bind(R.id.sunrise) TextView mSunriseView;
    @Bind(R.id.sunset) TextView mSunsetView;
    @Bind(R.id.coords) TextView mCoordsView;

    private static SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm a", Locale.US);

    private WeatherApiService mWeatherApiService;
    private WeatherData mWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        buildWeatherApiService();
        updateValuesFromBundle(savedInstanceState);
    }

    private void buildWeatherApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mWeatherApiService = retrofit.create(WeatherApiService.class);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.keySet().contains(WEATHER_DATA_KEY)) {
            mWeatherData = savedInstanceState.getParcelable(WEATHER_DATA_KEY);
            updateUI();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(WEATHER_DATA_KEY, mWeatherData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        requestWeather();
    }

    private void requestWeather() {
        if (mCurrentLocation != null) {
            double latitude = mCurrentLocation.getLatitude();
            double longitude = mCurrentLocation.getLongitude();

            Call<WeatherData> call = mWeatherApiService.getWeather(latitude, longitude);
            call.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                    if (response != null && response.isSuccess()) {
                        mWeatherData = response.body();
                        updateUI();
                    } else {
                        Toast.makeText(WeatherActivity.this,
                                R.string.failed_load_data, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(WeatherActivity.this,
                            R.string.failed_load_data, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }

    private void updateUI() {
        if (mWeatherData != null) {
            SystemInfo sys = mWeatherData.getSystemInfo();
            Weather weather = mWeatherData.getWeather().get(0);
            MainData main = mWeatherData.getMainData();
            Wind wind = mWeatherData.getWind();
            Coords coords = mWeatherData.getCoords();

            String city = mWeatherData.getName() + ", " + sys.getCountry();
            mCityView.setText(city);

            String iconUrl = String.format(WeatherApiService.ICON_URL, weather.getIcon());
            Picasso.with(this).load(iconUrl).into(mIconView);

            double tempCelsius = main.getTemperature() - 273.15;
            mTemperatureView.setText(String.format("%.1f\u2103", tempCelsius));

            mWeatherView.setText(weather.getMain());
            mDescriptionView.setText(weather.getDescription());

            mPressureView.setText(Html.fromHtml(getString(R.string.pressure, main.getPressure())));
            mHumidityView.setText(Html.fromHtml(getString(R.string.humidity, main.getHumidity())));

            String windDirection = getWindDirection(wind.getDegree());
            String windSpeed = getWindSpeed(wind.getSpeed());
            mWindView.setText(Html.fromHtml(getString(R.string.wind,
                    windSpeed, wind.getSpeed(), windDirection)));

            String sunriseTime = mTimeFormat.format(new Date(sys.getSunrise() * 1000));
            mSunriseView.setText(Html.fromHtml(getString(R.string.sunrise, sunriseTime)));

            String sunsetTime = mTimeFormat.format(new Date(sys.getSunset() * 1000));
            mSunsetView.setText(Html.fromHtml(getString(R.string.sunset, sunsetTime)));

            mCoordsView.setText(Html.fromHtml(getString(R.string.coords,
                    coords.getLatitude(), coords.getLongitude())));
        }
    }

    @OnClick(R.id.location)
    public void showLocation(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private String getWindDirection(double degree) {
        if (degree > 348.75 && degree < 360 || degree >= 0 && degree <= 11.25) {
            return "North";
        } else if (degree > 11.25 && degree <= 33.75) {
            return "North-northeast";
        } else if (degree > 33.75 && degree <= 56.25) {
            return "Northeast";
        } else if (degree > 56.25 && degree <= 78.75) {
            return "East-northeast";
        } else if (degree > 78.75 && degree <= 101.25) {
            return "East";
        } else if (degree > 101.25 && degree <= 123.75) {
            return "East-southeast";
        } else if (degree > 123.75 && degree <= 146.25) {
            return "Southeast";
        } else if (degree > 146.25 && degree <= 168.75) {
            return "South-southeast";
        } else if (degree > 168.75 && degree <= 191.25) {
            return "South";
        } else if (degree > 191.25 && degree <= 213.75) {
            return "South-southwest";
        } else if (degree > 213.75 && degree <= 236.25) {
            return "Southwest";
        } else if (degree > 236.25 && degree <= 258.75) {
            return "West-southwest";
        } else if (degree > 258.75 && degree <= 281.25) {
            return "West";
        } else if (degree > 281.25 && degree <= 303.75) {
            return "West-northwest";
        } else if (degree > 303.75 && degree <= 326.25) {
            return "Northwest";
        } else if (degree > 326.25 && degree <= 348.75) {
            return "North-northwest";
        } else {
            return "";
        }
    }

    public String getWindSpeed(double speed) {
        if (speed >= 0 && speed < 0.3) {
            return "Calm";
        } else if (speed >= 0.3 && speed < 1.6) {
            return "Light air";
        } else if (speed >= 1.6 && speed < 3.4) {
            return "Light breeze";
        } else if (speed >= 3.4 && speed < 5.5) {
            return "Gentle breeze";
        } else if (speed >= 5.5 && speed < 8) {
            return "Moderate breeze";
        } else if (speed >= 8 && speed < 10.8) {
            return "Fresh breeze";
        } else if (speed >= 10.8 && speed < 13.9) {
            return "Strong breeze";
        } else if (speed >= 13.9 && speed < 17.2) {
            return "Near gale";
        } else if (speed >= 17.2 && speed < 20.8) {
            return "Gale";
        } else if (speed >= 20.8 && speed < 24.5) {
            return "Severe gale";
        } else if (speed >= 24.5 && speed < 28.5) {
            return "Storm";
        } else if (speed >= 28.5 && speed < 32.7) {
            return "Violent storm";
        } else if (speed >= 32.7){
            return "Hurricane";
        } else {
            return "";
        }
    }

}