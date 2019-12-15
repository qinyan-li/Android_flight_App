package com.glasses.flightapp.flightapp.Models;

import java.time.LocalDate;

/**
 * model class representing a generic search request based on date
 */
public abstract class FlightRequest {
    private LocalDate date;

    FlightRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public abstract String getIdentifier();
}
