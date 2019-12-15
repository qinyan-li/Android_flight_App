package com.glasses.flightapp.flightapp.Fragments;

import android.os.AsyncTask;
import com.glasses.flightapp.flightapp.AsyncTask.OnResultListener;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightFromToRequest;

import java.util.List;

public class MockFromToSearchFragment extends FromToSearchFragment {
    private List<Flight> flights;

    public MockFromToSearchFragment(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    AsyncTask<FlightFromToRequest, Void, List<Flight>> createAsyncTask() {
        return new AsyncTask<FlightFromToRequest, Void, List<Flight>>() {
            @Override
            protected List<Flight> doInBackground(FlightFromToRequest... flightNumberRequests) {
                OnResultListener<List<Flight>> listener = new OnSearchResultListener();
                listener.onSuccess(flights);

                return flights;
            }
        };
    }
}
