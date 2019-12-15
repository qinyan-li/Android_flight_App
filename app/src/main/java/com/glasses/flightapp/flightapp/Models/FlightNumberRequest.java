package com.glasses.flightapp.flightapp.Models;

import java.time.LocalDate;

/**
 * model class representing a search request based on date and
 * flight number
 */
public class FlightNumberRequest extends FlightRequest {
    private String flightNo;

    public FlightNumberRequest(LocalDate date, String flightNo) {
        super(date);

        this.flightNo = flightNo;
    }

    public String getFlightNo() {
        return flightNo;
    }


    @Override
    public String getIdentifier() {
        return flightNo.toLowerCase();
    }
}
