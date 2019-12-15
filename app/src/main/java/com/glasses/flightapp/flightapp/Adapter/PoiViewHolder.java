package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.glasses.flightapp.flightapp.Models.Poi;
import com.glasses.flightapp.flightapp.R;

/**
 * view holder mapping POIs to list item representations
 */
public class PoiViewHolder extends EntityViewHolder<Poi, PoiListAdapter> {
    private TextView tvName;
    private TextView tvAddress;

    private ImageButton btnFav;
    private PoiListAdapter adapter;
    private Context context;

    public PoiViewHolder(Context context, View itemView, PoiListAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        this.context = context;

        tvName = itemView.findViewById(R.id.name);
        tvAddress = itemView.findViewById(R.id.address);

        btnFav = itemView.findViewById(R.id.favBtn);
    }

    public void bind(Poi poi, OnItemClickListener<Poi> listener, OnItemButtonClickListener<Poi, PoiListAdapter> btnListener) {
        tvName.setText(poi.getName());
        tvAddress.setText(poi.getAddress());

        btnFav.setImageResource(R.drawable.ic_star);

        if(listener != null)
            itemView.setOnClickListener(v -> listener.onItemClick(v, poi));

        if(btnListener != null)
            btnFav.setOnClickListener(v-> btnListener.onItemClick(v, poi, adapter));
        else {
            btnFav.setVisibility(View.GONE);
        }
    }
}
