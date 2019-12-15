package com.glasses.flightapp.flightapp.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.glasses.flightapp.flightapp.Converter.DateConverter;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.Poi;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class PoiRepository{
    private PoiDao mPoiDao;
    private LiveData<List<Poi>> mAllPois;

    PoiRepository(Application application) {
        FlightDatabase db = FlightDatabase.getDatabase(application);
        mPoiDao = db.poiDao();
        mAllPois = mPoiDao.getAllPois();
    }

    LiveData<List<Poi>> getAllPois() {
        return mAllPois;
    }

    Poi getPoi(String id) {
        try {
            return new getAsyncTask(mPoiDao).execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    void insert (Poi Poi) {
        new PoiRepository.insertAsyncTask(mPoiDao).execute(Poi);
    }

    void delete (Poi Poi) {
        new PoiRepository.deleteAsyncTask(mPoiDao).execute(Poi);
    }

    private static class insertAsyncTask extends AsyncTask<Poi, Void, Void> {

        private PoiDao mAsyncTaskDao;

        insertAsyncTask(PoiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Poi... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getAsyncTask extends AsyncTask<String, Void, Poi> {
        private PoiDao mAsyncTaskDao;

        getAsyncTask(PoiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Poi doInBackground(final String... params) {
            return mAsyncTaskDao.getPoi(params[0]);
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Poi, Void, Void> {

        private PoiDao mAsyncTaskDao;

        deleteAsyncTask(PoiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Poi... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}

