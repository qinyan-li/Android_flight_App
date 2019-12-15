package com.glasses.flightapp.flightapp.Models;

import java.time.LocalDate;

/**
 * model class representing a search request based on date and
 * departure + arrival airport
 */
public class FlightFromToRequest extends FlightRequest {
    private String startIATA;
    private String endIATA;

    public FlightFromToRequest(LocalDate date, String startIATA, String endIATA) {
        super(date);

        this.startIATA = startIATA;
        this.endIATA = endIATA;
    }

    public String getStartIATA() {
        return startIATA;
    }

    public String getEndIATA() {
        return endIATA;
    }

    @Override
    public String getIdentifier() {
        return startIATA.toLowerCase() + endIATA.toLowerCase();
    }
}
