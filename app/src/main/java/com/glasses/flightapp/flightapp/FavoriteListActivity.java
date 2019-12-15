package com.glasses.flightapp.flightapp;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.glasses.flightapp.flightapp.Adapter.FlightListAdapter;
import com.glasses.flightapp.flightapp.Database.FlightViewModel;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.WeatherRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * display all flights that are saved as favorites by the user
 */
public class FavoriteListActivity extends NavigationActivity {
    private RecyclerView flightList;

    private FlightViewModel viewModel;
    private FlightListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        initNavigation();

        viewModel = ViewModelProviders.of(this).get(FlightViewModel.class);
        flightList = findViewById(R.id.recyclerview);

        initFlightList();
    }

    private void initFlightList() {
        adapter = new FlightListAdapter(this, (view, item) -> {
            Intent intent = new Intent(FavoriteListActivity.this, FlightDetailActivity.class);
            intent.putExtra(FlightDetailActivity.FLIGHT_DETAIL, item);
            FavoriteListActivity.this.startActivity(intent);
        }, (view, flight, flAdapter) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteListActivity.this);
            builder
                .setMessage(R.string.dialog_remove_flight)
                .setTitle(R.string.dialog_title_remove_flight)
                .setPositiveButton(R.string.button_ok, (dialog, which) -> {
                    viewModel.delete(flight);
                    List<Flight> flights = flAdapter.getElements();
                    flights.remove(flight);
                    String startDate = DateTimeFormatter.BASIC_ISO_DATE.format(flight.getStart());
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(flight.getFlightNo() + '_' + startDate);

                    flAdapter.setElements(flights);
                    flAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(R.string.button_cancel, null)
                .setCancelable(true);

            builder.create().show();
        });

        flightList.setAdapter(adapter);
        flightList.setLayoutManager(new LinearLayoutManager(this));
        flightList.addItemDecoration(new DividerItemDecoration(flightList.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllFlights().observe(this, flights -> {
            if(flights == null)
                return;

            flights.forEach(flight -> flight.setSaved(true));
            adapter.setElements(flights);
        });
    }
}
