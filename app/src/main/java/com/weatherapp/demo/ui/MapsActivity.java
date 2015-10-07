package com.weatherapp.demo.ui;

import android.app.ActionBar;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weatherapp.demo.R;

public class MapsActivity extends BaseLocationActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private boolean mMapAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        setLocationMarker();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        setLocationMarker();
    }

    private void setLocationMarker() {
        if (mGoogleMap != null && mCurrentLocation != null) {
            if (mMarker != null) mMarker.remove();

            LatLng location = new LatLng(
                    mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMarker = mGoogleMap.addMarker(new MarkerOptions().position(location));

            if (!mMapAnimated) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(location).zoom(15).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMapAnimated = true;
            }
        }
    }

}