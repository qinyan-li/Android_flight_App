package com.glasses.flightapp.flightapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Adapter.FlightListAdapter;
import com.glasses.flightapp.flightapp.Database.FlightViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * display search results as a list of flights
 */
public class FlightListActivity extends NavigationActivity {
    private ArrayList<Flight> results;

    private RecyclerView resultList;

    private FlightViewModel viewModel;
    private FlightListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);

        initNavigation();

        getIntentExtra();
        if (results.size() == 0) {
            finish();
        }

        viewModel = ViewModelProviders.of(this).get(FlightViewModel.class);
        resultList = findViewById(R.id.list_view);

        initResultList();
    }

    private void getIntentExtra() {
        results = new ArrayList<>();

        Intent intent = getIntent();
        if (intent.getSerializableExtra(SearchActivity.FLIGHT_PARAM) instanceof Flight) {
            results.add((Flight) intent.getSerializableExtra(SearchActivity.FLIGHT_PARAM));
        } else if (intent.getSerializableExtra(SearchActivity.FLIGHT_LIST_PARAM) instanceof Flight[]) {
            results = new ArrayList<>(Arrays.asList((Flight[]) intent.getSerializableExtra(SearchActivity.FLIGHT_LIST_PARAM)));
        }
    }

    private void initResultList() {
        adapter = new FlightListAdapter(this, (view, flight) -> {
            Intent intent = new Intent(FlightListActivity.this, FlightDetailActivity.class);
            intent.putExtra(FlightDetailActivity.FLIGHT_DETAIL, flight);
            FlightListActivity.this.startActivity(intent);
        }, (view, item, flAdapter) -> {
            ImageButton btn = view.findViewById(R.id.favBtn);

            if (btn.getTag(R.id.favBtn) != null) {
                viewModel.delete(item);
                item.setSaved(false);
                btn.setImageResource(R.drawable.ic_star_border);
                btn.setTag(R.id.favBtn, null);

                Toast.makeText(FlightListActivity.this, R.string.removed_from_list, Toast.LENGTH_SHORT).show();
            } else {
                viewModel.insert(item);
                item.setSaved(true);
                btn.setImageResource(R.drawable.ic_star);
                btn.setTag(R.id.favBtn, "active");

                Toast.makeText(FlightListActivity.this, R.string.saved_to_list, Toast.LENGTH_SHORT).show();
            }
        });

        resultList.setAdapter(adapter);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.addItemDecoration(new DividerItemDecoration(resultList.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllFlights().observe(this, flights -> {
            if (flights != null) {
                results.forEach(flight -> {
                    if (flights.contains(flight)) {
                        flight.setSaved(true);
                        String startDate = DateTimeFormatter.BASIC_ISO_DATE.format(flight.getStart());
                        FirebaseMessaging.getInstance().subscribeToTopic(flight.getFlightNo() + '_' + startDate);
                    } else {
                        flight.setSaved(false);
                        String startDate = DateTimeFormatter.BASIC_ISO_DATE.format(flight.getStart());
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(flight.getFlightNo() + '_' + startDate);
                    }
                });
            }

            adapter.setElements(results);
        });
    }
}
