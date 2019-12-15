package com.glasses.flightapp.flightapp.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HeWeather6 implements Serializable {

    private now now;
    private String status;

    private daily_forecast[] daily_forecast;

    public HeWeather6(){

    }
    /*public HeWeather6(now now){
        this.now=now;
    }
    public HeWeather6(String location){
        this.location=location;


    }*/

    public void setNow(now now) {
        this.now = now;
    }

    public now getNow() {
        return now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public daily_forecast[] getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(daily_forecast[] daily_forecastr) {
        this.daily_forecast = daily_forecastr;
    }
}
