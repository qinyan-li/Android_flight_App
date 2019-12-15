package com.glasses.flightapp.flightapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.glasses.flightapp.flightapp.AsyncTask.FlightNumberSearchAsyncTask;
import com.glasses.flightapp.flightapp.Fragments.MockFlightNumberSearchFragment;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightNumberRequest;
import com.google.firebase.FirebaseApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowJobScheduler;
import org.robolectric.shadows.ShadowLooper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class SearchActivityTest {
    private SearchActivity activity;

    @Before
    public void setUp() {
        FirebaseApp.initializeApp(RuntimeEnvironment.application);
        activity = Robolectric.setupActivity(SearchActivity.class);
    }

    @Test
    public void testSwitchBetweenTabs() {
        assertEquals(2, activity.tabLayout.getTabCount());

        //check first tab
        assertEquals(0, activity.tabLayout.getSelectedTabPosition());
        View currentView = activity.viewPager.getChildAt(activity.viewPager.getCurrentItem());
        assertNotNull(currentView.findViewById(R.id.flightNo));

        //switch to second tab
        activity.tabLayout.getTabAt(1).select();

        //check second tab
        assertEquals(1, activity.viewPager.getCurrentItem());
        currentView = activity.viewPager.getChildAt(activity.viewPager.getCurrentItem());
        assertNotNull(currentView.findViewById(R.id.To));
    }

    @Test
    public void testSubmitEmptyFirstTab() {
        View currentView = activity.viewPager.getChildAt(0);
        Button btnSubmit = currentView.findViewById(R.id.SearchButton);
        EditText flightNo = currentView.findViewById(R.id.flightNo);

        assertTrue(btnSubmit.performClick());
        assertEquals(activity.getString(R.string.error_flight_no), flightNo.getError().toString());
    }

    @Test
    public void testSubmitValidFirstTab() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Flight numberResult = new Flight();

        ShadowLooper.pauseMainLooper();
        activity.viewPager.setAdapter(new MockSearchActivityPageAdapter(
            activity.getSupportFragmentManager(), 2, numberResult, null
        ));
        Robolectric.getForegroundThreadScheduler().advanceToLastPostedRunnable();

        View currentView = activity.viewPager.getChildAt(0);
        Button btnSubmit = currentView.findViewById(R.id.SearchButton);
        EditText flightNo = currentView.findViewById(R.id.flightNo);
        flightNo.setText("LH2018");

        assertTrue(btnSubmit.performClick());
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertEquals(FlightListActivity.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(numberResult, startedIntent.getSerializableExtra("flight_param"));
    }

    @Test
    public void testSubmitEmptySecondTab() {
        activity.tabLayout.getTabAt(1).select();

        View currentView = activity.viewPager.getChildAt(1);
        Button btnSubmit = currentView.findViewById(R.id.SearchButton);
        EditText flightFrom = currentView.findViewById(R.id.From);
        EditText flightTo = currentView.findViewById(R.id.To);

        assertTrue(btnSubmit.performClick());
        assertEquals(activity.getString(R.string.error_flight_from), flightFrom.getError());
        assertEquals(activity.getString(R.string.error_flight_to), flightTo.getError());
    }

    @Test
    public void testSubmitValidSecondTab() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        List<Flight> fromToResult = new ArrayList<>();
        fromToResult.add(new Flight());

        ShadowLooper.pauseMainLooper();
        activity.viewPager.setAdapter(new MockSearchActivityPageAdapter(
                activity.getSupportFragmentManager(), 2, null, fromToResult
        ));
        Robolectric.getForegroundThreadScheduler().advanceToLastPostedRunnable();

        View currentView = activity.viewPager.getChildAt(1);
        Button btnSubmit = currentView.findViewById(R.id.SearchButton);
        EditText from = currentView.findViewById(R.id.From);
        EditText to = currentView.findViewById(R.id.To);
        from.setText("MUC");
        to.setText("DUS");

        assertTrue(btnSubmit.performClick());
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertEquals(FlightListActivity.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(fromToResult, new ArrayList<>(Arrays.asList((Flight[]) startedIntent.getSerializableExtra("flight_list_param"))));
    }
}