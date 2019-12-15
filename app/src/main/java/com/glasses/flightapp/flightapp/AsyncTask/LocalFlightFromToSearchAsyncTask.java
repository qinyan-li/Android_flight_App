package com.glasses.flightapp.flightapp.AsyncTask;

import android.content.Context;

import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightRequest;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * search flights by date and departure and arrival airport and use local
 * REST dummy to retrieve information
 */
public class LocalFlightFromToSearchAsyncTask extends FlightFromToSearchAsyncTask implements LocalFlightAsyncTask {
    private WeakReference<Context> ctx;

    /**
     * Constructor.
     *
     * @param ctx application context (used for file handling)
     */
    public LocalFlightFromToSearchAsyncTask(Context ctx, OnResultListener<List<Flight>> listener) {
        super(listener);
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    public List<String> getValidConnections() {
        return Arrays.asList(
                "mucdus",
                "mucpek",
                "muclis"
        );
    }

    @Override
    protected List<Flight> postReceiveFilter(List<Flight> flights, FlightRequest request) {
        LocalDate currentDate = request.getDate();

        //set all found flights to match request's date (otherwise we would need large or
        //redundant files to get search results for multiple days)
        flights.forEach(flight -> flight.changeDate(currentDate));

        return flights;
    }

    @Override
    public String getURI(FlightRequest request) {
        return LocalFlightAsyncTask.super.getURI(ctx, request);
    }
}
