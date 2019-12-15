package com.glasses.flightapp.flightapp.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.glasses.flightapp.flightapp.Converter.DateConverter;
import com.glasses.flightapp.flightapp.Models.Flight;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * repository class providing helper methods to access database operations
 */
class FlightRepository {
    private FlightDao mFlightDao;
    private LiveData<List<Flight>> mAllFlights;

    FlightRepository(Application application) {
        FlightDatabase db = FlightDatabase.getDatabase(application);
        mFlightDao = db.flightDao();
        mAllFlights = mFlightDao.getAllFlights();
    }

    LiveData<List<Flight>> getAllFlights() {
        return mAllFlights;
    }

    Flight getFlight(String flight_no, ZonedDateTime date) {
        return mFlightDao.getFlight(flight_no, DateConverter.dateToString(date));
    }

    void insert (Flight flight) {
        new insertAsyncTask(mFlightDao).execute(flight);
    }

    void delete (Flight flight) {
        new deleteAsyncTask(mFlightDao).execute(flight);
    }

    private static class insertAsyncTask extends AsyncTask<Flight, Void, Void> {

        private FlightDao mAsyncTaskDao;

        insertAsyncTask(FlightDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Flight... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Flight, Void, Void> {

        private FlightDao mAsyncTaskDao;

        deleteAsyncTask(FlightDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Flight... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
