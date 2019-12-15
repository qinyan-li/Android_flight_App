package com.glasses.flightapp.flightapp.Weather;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class WeatherContainer {
    private HeWeather6[] elements;

    public WeatherContainer() {
    }

    @JsonGetter("HeWeather6")
    public HeWeather6[] getElements() {
        return elements;
    }

    @JsonSetter("HeWeather6")
    public void setElements(HeWeather6[] elements) {
        this.elements = elements;
    }
}
