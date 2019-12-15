package com.glasses.flightapp.flightapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.glasses.flightapp.flightapp.Fragments.SearchFragment;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * The main activity contains the search interface for flights.
 * To allow for different search options, i.e. flight nubmer and from to search,
 * Fragments are used to actually implement the content.
 */
public class SearchActivity extends NavigationActivity implements SearchFragment.OnFragmentInteractionListener {

    public static final String FLIGHT_PARAM = "flight_param";
    public static final String FLIGHT_LIST_PARAM = "flight_list_param";

    ViewPager    viewPager;
    TabLayout    tabLayout;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavigation();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);

        initTabs();
    }

    private void initTabs() {
        PagerAdapter adapter = new SearchActivityPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        joinNotificationTopic();
    }

    private void joinNotificationTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFragmentInteraction(View view) {

    }
}
