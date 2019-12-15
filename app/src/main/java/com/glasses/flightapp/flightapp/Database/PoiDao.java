package com.glasses.flightapp.flightapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.Poi;

import java.util.List;

@Dao
public interface PoiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Poi poi);

    @Delete
    void delete(Poi poi);

    @Query("SELECT * from poi_favorite ORDER BY id")
    LiveData<List<Poi>> getAllPois();

    @Query("SELECT * from poi_favorite WHERE id = :id ")
    Poi getPoi(String id);
}
