package com.glasses.flightapp.flightapp.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.Poi;

import java.time.ZonedDateTime;
import java.util.List;

public class PoiViewModel extends AndroidViewModel {
    private PoiRepository mRepository;
    private LiveData<List<Poi>> mAllPois;

    public PoiViewModel (Application application) {
        super(application);
        mRepository = new PoiRepository(application);
        mAllPois = mRepository.getAllPois();
    }

    public LiveData<List<Poi>> getAllPois() { return mAllPois; }

    public Poi getPoi(String id) {
        return mRepository.getPoi(id);
    }

    public void insert(Poi Poi) { mRepository.insert(Poi); }

    public void delete(Poi Poi) {
        mRepository.delete(Poi);
    }
}

