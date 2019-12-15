package com.glasses.flightapp.flightapp.Fragments;

import android.os.AsyncTask;
import com.glasses.flightapp.flightapp.AsyncTask.OnResultListener;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightNumberRequest;

public class MockFlightNumberSearchFragment extends FlightNumberSearchFragment {
    private Flight flight;

    public MockFlightNumberSearchFragment(Flight flight) {
        this.flight = flight;
    }

    @Override
    AsyncTask<FlightNumberRequest, Void, Flight> createAsyncTask() {
        return new AsyncTask<FlightNumberRequest, Void, Flight>() {
            @Override
            protected Flight doInBackground(FlightNumberRequest... flightNumberRequests) {
                OnResultListener<Flight> listener = new SearchOnResultListener();
                listener.onSuccess(flight);

                return flight;
            }
        };
    }
}
