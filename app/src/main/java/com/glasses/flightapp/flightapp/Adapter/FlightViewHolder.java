package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.R;

import java.time.format.DateTimeFormatter;

/**
 * view holder mapping flights to list item representations
 */
public class FlightViewHolder extends EntityViewHolder<Flight, FlightListAdapter> {
    private TextView tvFlightNo;
    private TextView tvFlightDate;

    private TextView tvStartIATA;
    private TextView tvStartAirport;
    private TextView tvStartTime;

    private TextView tvEndIATA;
    private TextView tvEndAirport;
    private TextView tvEndTime;

    private ImageButton btnFav;
    private FlightListAdapter adapter;
    private Context context;

    public FlightViewHolder(Context context, View itemView, FlightListAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        this.context = context;

        tvFlightNo = itemView.findViewById(R.id.flightNo);
        tvFlightDate = itemView.findViewById(R.id.flightDate);

        tvStartIATA = itemView.findViewById(R.id.startIATA);
        tvStartAirport = itemView.findViewById(R.id.startAirport);
        tvStartTime = itemView.findViewById(R.id.startTime);

        tvEndIATA = itemView.findViewById(R.id.endIATA);
        tvEndAirport = itemView.findViewById(R.id.endAirport);
        tvEndTime = itemView.findViewById(R.id.endTime);

        btnFav = itemView.findViewById(R.id.favBtn);
    }

    public void bind(Flight flight, OnItemClickListener<Flight> listener, OnItemButtonClickListener<Flight, FlightListAdapter> btnListener) {
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter df = DateTimeFormatter.ofPattern(context.getString(R.string.date_format));

        tvFlightNo.setText(flight.getFlightNo());
        tvFlightDate.setText(df.format(flight.getStart()));
        tvStartIATA.setText(flight.getStartIATA());
        tvStartAirport.setText(flight.getStartName());
        tvStartTime.setText(tf.format(flight.getStart()));
        tvEndIATA.setText(flight.getEndIATA());
        tvEndAirport.setText(flight.getEndName());
        tvEndTime.setText(tf.format(flight.getEnd()));

        if(listener != null)
            itemView.setOnClickListener(v -> listener.onItemClick(v, flight));

        if(flight.isSaved()) {
            btnFav.setImageResource(R.drawable.ic_star);
            btnFav.setTag(R.id.favBtn, "active");
        } else {
            btnFav.setImageResource(R.drawable.ic_star_border);
            btnFav.setTag(R.id.favBtn, null);
        }

        if(btnListener != null)
            btnFav.setOnClickListener(v-> btnListener.onItemClick(v, flight, adapter));
        else {
            btnFav.setVisibility(View.GONE);
        }
    }
}
