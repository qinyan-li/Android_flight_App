package com.glasses.flightapp.flightapp.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.glasses.flightapp.flightapp.Models.Coupon;

import java.util.List;

public class CouponViewModel extends AndroidViewModel {
    private CouponRepository mRepository;
    private LiveData<List<Coupon>> allCoupons;

    public CouponViewModel(Application application) {
        super(application);
        mRepository = new CouponRepository(application);
        allCoupons = mRepository.getAll();
    }

    public LiveData<List<Coupon>> getAll() {
        return allCoupons;
    }

    public void insert(Coupon coupon) {
        mRepository.insert(coupon);
    }

    public void delete(Coupon coupon) {
        mRepository.delete(coupon);
    }
}

