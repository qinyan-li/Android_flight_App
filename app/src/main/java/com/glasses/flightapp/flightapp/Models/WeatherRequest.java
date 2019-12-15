package com.glasses.flightapp.flightapp.Models;

public class WeatherRequest {
    private String location;

    public WeatherRequest(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

}
