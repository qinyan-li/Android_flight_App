package com.glasses.flightapp.flightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.glasses.flightapp.flightapp.Adapter.FlightViewHolder;
import com.glasses.flightapp.flightapp.Adapter.WeatherViewHolder;
import com.glasses.flightapp.flightapp.AsyncTask.OnResultListener;
import com.glasses.flightapp.flightapp.AsyncTask.WeatherAsyncTask;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.WeatherRequest;
import com.glasses.flightapp.flightapp.Weather.HeWeather6;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class FlightDetailActivity extends NavigationActivity implements OnMapReadyCallback {

    public static final String FLIGHT_DETAIL = "flight_detail";

    private Flight flight;
    private MapView mapView;
    private GoogleMap map;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        initNavigation();

        Intent intent = getIntent();
        if (intent.getSerializableExtra(FlightDetailActivity.FLIGHT_DETAIL) == null) {
            finish();
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        flight = (Flight) intent.getSerializableExtra(FlightDetailActivity.FLIGHT_DETAIL);

        FlightViewHolder holder = new FlightViewHolder(this, findViewById(R.id.flight_details), null);
        holder.bind(flight, null, null);

        new WeatherAsyncTask(new OnResultListener<HeWeather6>() {
            @Override
            public void onSuccess(HeWeather6 weather) {
                WeatherViewHolder weatherHolder = new WeatherViewHolder(getApplicationContext(), findViewById(R.id.weather));
                weatherHolder.bind(flight.getEndName(), flight.getStart(), weather);
                findViewById(R.id.weather).setVisibility(View.VISIBLE);
                findViewById(R.id.weather_progress).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Exception exception) {

            }
        }).execute(new WeatherRequest(flight.getWeatherName()));

        initMaps(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.safety_instruction:
                Intent intent = new Intent(this, SafetyInstructionActivity.class);
                intent.putExtra(SafetyInstructionActivity.EXTRA_AIRCRAFT, flight.getAircraft());
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        LatLng departure = flight.getStartLatLng();
        LatLng destination = flight.getEndLatLng();

        map.addMarker(new MarkerOptions().position(departure));
        map.addMarker(new MarkerOptions().position(destination));

        boundsBuilder.include(departure);
        boundsBuilder.include(destination);

        map.setOnMapLoadedCallback(() -> map.moveCamera(CameraUpdateFactory.newLatLngBounds(
                boundsBuilder.build(), 200
        )));

        map.addPolyline(new PolylineOptions()
            .add(departure)
            .add(destination)
            //take into account that our Earth is not as flat as it seems in Google Maps ;-)
            .geodesic(true));

        map.setOnMapClickListener(latLng -> {
            Intent intentPOI = new Intent(FlightDetailActivity.this, POIDetailsActivity.class);
            intentPOI.putExtra(POIDetailsActivity.POI_COORDS, flight.getLatLngBounds());
            startActivity(intentPOI);
        });
    }

    private void initMaps(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }
}
