package com.glasses.flightapp.flightapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.glasses.flightapp.flightapp.Models.Coupon;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.Poi;

/**
 * database class handling access to local database
 */
@Database(entities = {Flight.class, Poi.class, Coupon.class}, version = 1, exportSchema = false)
public abstract class FlightDatabase extends RoomDatabase {
    private static FlightDatabase INSTANCE;

    public abstract FlightDao flightDao();

    public abstract PoiDao poiDao();

    public abstract CouponDao couponDao();

    static FlightDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FlightDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FlightDatabase.class, "my_favorite").build();

                }
            }
        }
        return INSTANCE;
    }
}
