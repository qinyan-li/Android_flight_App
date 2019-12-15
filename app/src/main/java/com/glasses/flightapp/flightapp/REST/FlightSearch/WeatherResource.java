package com.glasses.flightapp.flightapp.REST.FlightSearch;

import android.support.annotation.Nullable;
import com.glasses.flightapp.flightapp.Weather.WeatherContainer;
import org.restlet.resource.Get;

public interface WeatherResource {
    @Nullable
    @Get
    WeatherContainer findByLocation(String Location);
}
