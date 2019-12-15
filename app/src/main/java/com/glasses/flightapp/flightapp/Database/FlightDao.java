package com.glasses.flightapp.flightapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.glasses.flightapp.flightapp.Models.Flight;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Data access object (Dao) to control permitted database operations
 * for flight objects
 */
@Dao
public interface FlightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Flight flight);

    @Delete
    void delete(Flight flight);

    @Query("SELECT * from my_favorite ORDER BY start")
    LiveData<List<Flight>> getAllFlights();

    @Query("SELECT * from my_favorite WHERE flight_no = :flightNumber AND start = :date LIMIT 1")
    Flight getFlight(String flightNumber, String date);
}
