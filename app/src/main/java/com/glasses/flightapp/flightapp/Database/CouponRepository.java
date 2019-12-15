package com.glasses.flightapp.flightapp.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.glasses.flightapp.flightapp.Models.Coupon;

import java.util.List;

class CouponRepository {
    private CouponDao couponDao;
    private LiveData<List<Coupon>> allCoupons;

    CouponRepository(Application application) {
        FlightDatabase db = FlightDatabase.getDatabase(application);
        couponDao = db.couponDao();
        allCoupons = couponDao.getAll();
    }

    LiveData<List<Coupon>> getAll() {
        return allCoupons;
    }

    void insert (Coupon coupon) {
        new CouponRepository.insertAsyncTask(couponDao).execute(coupon);
    }

    void delete (Coupon coupon) {
        new CouponRepository.deleteAsyncTask(couponDao).execute(coupon);
    }

    private static class insertAsyncTask extends AsyncTask<Coupon, Void, Void> {

        private CouponDao mAsyncTaskDao;

        insertAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Coupon... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Coupon, Void, Void> {

        private CouponDao mAsyncTaskDao;

        deleteAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Coupon... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}

