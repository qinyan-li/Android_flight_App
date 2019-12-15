package com.glasses.flightapp.flightapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.glasses.flightapp.flightapp.AsyncTask.LocalFlightFromToSearchAsyncTask;
import com.glasses.flightapp.flightapp.AsyncTask.OnResultListener;
import com.glasses.flightapp.flightapp.FlightListActivity;
import com.glasses.flightapp.flightapp.SearchActivity;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightFromToRequest;
import com.glasses.flightapp.flightapp.R;

import java.util.List;

/**
 * Flight search fragment for searches where the target, the destination and the date is
 * used as import.
 */
public class FromToSearchFragment extends SearchFragment {
    private AutoCompleteTextView flightFrom;
    private AutoCompleteTextView flightTo;

    public FromToSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FromToSearchFragment.
     */
    public static SearchFragment newInstance() {
        return new FromToSearchFragment();
    }

    @Override
    View inflateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_from_to, container, false);
    }

    @Override
    Button getSearchButton(View view) {
        return view.findViewById(R.id.SearchButton);
    }

    @Override
    EditText getDateInput(View view) {
        return view.findViewById(R.id.date);
    }

    @Override
    boolean validInput() {
        boolean result = true;

        if (TextUtils.isEmpty(flightFrom.getText())) {
            flightFrom.setError(getResources().getString(R.string.error_flight_from));
            result = false;
        }
        if (TextUtils.isEmpty(flightTo.getText())) {
            flightTo.setError(getResources().getString(R.string.error_flight_to));
            result = false;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view == null)
            return null;

        flightFrom = view.findViewById(R.id.From);
        flightTo = view.findViewById(R.id.To);

        Context context = getContext();
        if(context != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.airports));
            flightFrom.setAdapter(adapter);
            flightTo.setAdapter(adapter);
        }

        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void searchFlights(View view) {
        super.searchFlights(view);

        FlightFromToRequest fromToRequest = new FlightFromToRequest(flightDate, flightFrom.getText().toString(), flightTo.getText().toString());
        createAsyncTask().execute(fromToRequest);
    }

    AsyncTask<FlightFromToRequest, Void, List<Flight>> createAsyncTask() {
        return new LocalFlightFromToSearchAsyncTask(getContext(), new OnSearchResultListener());
    }

    class OnSearchResultListener implements OnResultListener<List<Flight>> {
        @Override
        public void onSuccess(List<Flight> flights) {
            Context context = getContext();
            if(context == null)
                return;

            if(flights == null || flights.size() == 0) {
                Toast t = Toast.makeText(getContext(), R.string.no_results, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                t.show();
                return;
            }

            Intent intent = new Intent(getContext(), FlightListActivity.class);
            intent.putExtra(SearchActivity.FLIGHT_LIST_PARAM, flights.toArray(new Flight[0]));
            getContext().startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            Log.e(SearchFragment.TAG, exception.toString());
        }
    }
}
