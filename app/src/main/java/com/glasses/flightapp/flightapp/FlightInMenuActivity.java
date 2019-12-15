package com.glasses.flightapp.flightapp;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.glasses.flightapp.flightapp.Adapter.FlightViewHolder;
import com.glasses.flightapp.flightapp.Database.FlightViewModel;
import com.glasses.flightapp.flightapp.Models.Flight;

import java.time.ZonedDateTime;

public class FlightInMenuActivity extends NavigationActivity {
    private FlightViewModel viewModel;
    private Flight active;
    private FlightViewHolder holder;

    private View flightMenu;
    private TextView orderFood;
    private TextView watchSafety;
    private TextView callAttendant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_in_menu);

        initNavigation();

        viewModel = ViewModelProviders.of(this).get(FlightViewModel.class);

        flightMenu = findViewById(R.id.flight_menu);
        orderFood = findViewById(R.id.order_food);
        watchSafety = findViewById(R.id.watch_safety_instructions);
        callAttendant = findViewById(R.id.call_attendant);

        ButtonClickListener buttonClickListener = new ButtonClickListener();

        orderFood.setOnClickListener(buttonClickListener);
        watchSafety.setOnClickListener(buttonClickListener);
        callAttendant.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllFlights().observe(this, flights -> {
            active = null;

            if (flights != null) {
                for (Flight flight : flights) {
                    if (flight.getStart().isBefore(ZonedDateTime.now()) && flight.getEnd().isAfter(ZonedDateTime.now())) {
                        active = flight;
                        break;
                    }
                }
            }

            if(active==null) {
                flightMenu.setVisibility(View.INVISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.no_active_flight)
                        .setTitle(R.string.no_active_flight_title)
                        .setIcon(R.drawable.ic_flight_land)
                        .setNegativeButton(R.string.button_cancel, (dialog, which) -> {
                            finish();
                        })
                        .setPositiveButton(R.string.button_to_fav_list, (dialog, which) -> {
                            Intent intent = new Intent(this, FavoriteListActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .setCancelable(false);
                builder.create().show();
            } else {
                holder = new FlightViewHolder(this, findViewById(R.id.flight_details), null);
                holder.bind(active, null, null);

                flightMenu.setVisibility(View.VISIBLE);
            }
        });
    }

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.call_attendant:
                    Toast.makeText(getApplicationContext(), "Button <call flight attendant> clicked!!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.order_food:
                    Toast.makeText(getApplicationContext(), "Button <order food> clicked!!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.watch_safety_instructions:
                    Intent intent = new Intent(getApplicationContext(), SafetyInstructionActivity.class);
                    intent.putExtra(SafetyInstructionActivity.EXTRA_AIRCRAFT, active.getAircraft());
                    startActivity(intent);
                    break;
            }
        }
    }
}


