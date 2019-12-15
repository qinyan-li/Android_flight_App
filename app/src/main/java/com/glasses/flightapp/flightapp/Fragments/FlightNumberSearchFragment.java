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
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import com.glasses.flightapp.flightapp.AsyncTask.FlightNumberSearchAsyncTask;
import com.glasses.flightapp.flightapp.AsyncTask.LocalFlightNumberSearchAsyncTask;
import com.glasses.flightapp.flightapp.AsyncTask.OnResultListener;
import com.glasses.flightapp.flightapp.FlightListActivity;
import com.glasses.flightapp.flightapp.SearchActivity;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightNumberRequest;
import com.glasses.flightapp.flightapp.R;

/**
 * Flight search fragment where the flight number and the date are used as search parameters.
 */
public class FlightNumberSearchFragment extends SearchFragment {

    private EditText flightNo;

    public FlightNumberSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FlightNumberSearchFragment.
     */
    public static FlightNumberSearchFragment newInstance() {
        return new FlightNumberSearchFragment();
    }

    @Override
    View inflateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_flight_number, container, false);
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
        if (TextUtils.isEmpty(flightNo.getText())) {
            flightNo.setError(getResources().getString(R.string.error_flight_no));
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view == null)
            return null;

        flightNo = view.findViewById(R.id.flightNo);

        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void searchFlights(View view) {
        super.searchFlights(view);

        FlightNumberRequest numberRequest = new FlightNumberRequest(flightDate, flightNo.getText().toString());
        createAsyncTask().execute(numberRequest);
    }

    AsyncTask<FlightNumberRequest, Void, Flight> createAsyncTask() {
        return new LocalFlightNumberSearchAsyncTask(getContext(), new SearchOnResultListener());
    }

    class SearchOnResultListener implements OnResultListener<Flight> {
        @Override
        public void onSuccess(Flight flight) {
            Context context = getContext();
            if(context == null)
                return;

            if(flight == null) {
                Toast t = Toast.makeText(getContext(), R.string.no_results, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                t.show();
                return;
            }

            Intent intent = new Intent(context, FlightListActivity.class);
            intent.putExtra(SearchActivity.FLIGHT_PARAM, flight);
            context.startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            Log.e(SearchFragment.TAG, exception.toString());
        }
    }
}
