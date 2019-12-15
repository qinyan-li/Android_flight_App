package com.glasses.flightapp.flightapp.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.glasses.flightapp.flightapp.Models.Flight;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * public accessible  methods to modify local database
 */
public class FlightViewModel extends AndroidViewModel {
    private FlightRepository mRepository;
    private LiveData<List<Flight>> mAllFlights;

    public FlightViewModel (Application application) {
        super(application);
        mRepository = new FlightRepository(application);
        mAllFlights = mRepository.getAllFlights();
    }

    public LiveData<List<Flight>> getAllFlights() { return mAllFlights; }

    public Flight getFlight(String flight_no, ZonedDateTime date) {
        return mRepository.getFlight(flight_no, date);
    }

    public void insert(Flight flight) { mRepository.insert(flight); }

    public void delete(Flight flight) {
        mRepository.delete(flight);
    }
}
