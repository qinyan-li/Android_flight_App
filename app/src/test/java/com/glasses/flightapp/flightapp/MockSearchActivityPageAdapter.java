package com.glasses.flightapp.flightapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.glasses.flightapp.flightapp.AsyncTask.FlightFromToSearchAsyncTask;
import com.glasses.flightapp.flightapp.AsyncTask.FlightNumberSearchAsyncTask;
import com.glasses.flightapp.flightapp.Fragments.FlightNumberSearchFragment;
import com.glasses.flightapp.flightapp.Fragments.FromToSearchFragment;
import com.glasses.flightapp.flightapp.Fragments.MockFlightNumberSearchFragment;
import com.glasses.flightapp.flightapp.Fragments.MockFromToSearchFragment;
import com.glasses.flightapp.flightapp.Models.Flight;

import java.util.List;

class MockSearchActivityPageAdapter extends SearchActivityPageAdapter {
    private Flight numberResult;
    private List<Flight> fromToResult;

    MockSearchActivityPageAdapter(FragmentManager fm, int numOfTabs, Flight numberResult, List<Flight> fromToResult) {
        super(fm, numOfTabs);

        this.numberResult = numberResult;
        this.fromToResult = fromToResult;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MockFlightNumberSearchFragment(numberResult);
            case 1:
                return new MockFromToSearchFragment(fromToResult);
            default:
                return null;
        }
    }
}