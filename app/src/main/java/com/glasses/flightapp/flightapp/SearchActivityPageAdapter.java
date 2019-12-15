package com.glasses.flightapp.flightapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.glasses.flightapp.flightapp.Fragments.FlightNumberSearchFragment;
import com.glasses.flightapp.flightapp.Fragments.FromToSearchFragment;

/**
 * Assisting class to allow for the use of fragments in a tab layout.
 */
class SearchActivityPageAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    SearchActivityPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FlightNumberSearchFragment.newInstance();
            case 1:
                return FromToSearchFragment.newInstance();
            default:
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}