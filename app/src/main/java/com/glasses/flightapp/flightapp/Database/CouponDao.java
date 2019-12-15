package com.glasses.flightapp.flightapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.glasses.flightapp.flightapp.Models.Coupon;

import java.util.List;

@Dao
public interface CouponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Coupon coupon);

    @Delete
    void delete(Coupon coupon);

    @Query("SELECT * from coupon ORDER BY name")
    LiveData<List<Coupon>> getAll();
}
