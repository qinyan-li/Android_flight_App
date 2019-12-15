package com.glasses.flightapp.flightapp.AsyncTask;

import android.content.Context;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightRequest;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * search flights by date and flight number and use local
 * REST dummy to retrieve information
 */
public class LocalFlightNumberSearchAsyncTask extends FlightNumberSearchAsyncTask implements LocalFlightAsyncTask {
    private WeakReference<Context> ctx;

    /**
     * Constructor.
     *
     * @param ctx   application context (used for file handling)
     */
    public LocalFlightNumberSearchAsyncTask(Context ctx, OnResultListener<Flight> listener) {
        super(listener);
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    public List<String> getValidConnections() {
        return Arrays.asList(
                "ca962",
                "ew1912",
                "lh722",
                "lh2018",
                "lh1792",
                "lh2282"
        );
    }

    @Override
    public Flight postReceiveFilter(Flight flight, FlightRequest request) {
        if(flight.getFlightNo() == null)
            return null;

        LocalDate currentDate = request.getDate();

        //set all found flights to match request's date (otherwise we would need large or
        //redundant files to get search results for multiple days)
        return flight.changeDate(currentDate);
    }

    @Override
    public String getURI(FlightRequest request) {
        return LocalFlightAsyncTask.super.getURI(ctx, request);
    }
}
