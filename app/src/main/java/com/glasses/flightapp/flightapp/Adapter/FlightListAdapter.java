package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.view.View;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.R;

/**
 * adapter class handling display and behaviour of list items in
 * a flight list (RecyclerView)
 */
public class FlightListAdapter extends EntityListAdapter<Flight, FlightViewHolder> {
    public FlightListAdapter(Context context, OnItemClickListener<Flight> listener, OnItemButtonClickListener<Flight, EntityListAdapter<Flight, FlightViewHolder>> btnListener) {
        super(context, listener, btnListener);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_flight_list_item;
    }

    @Override
    protected FlightViewHolder createNewViewHolder(Context context, View itemView) {
        return new FlightViewHolder(context, itemView, this);
    }
}
