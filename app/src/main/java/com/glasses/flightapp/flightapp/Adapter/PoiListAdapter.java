package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.view.View;
import com.glasses.flightapp.flightapp.Models.Poi;
import com.glasses.flightapp.flightapp.R;

/**
 * adapter class handling display and behaviour of list items in
 * a poi list (RecyclerView)
 */
public class PoiListAdapter extends EntityListAdapter<Poi, PoiViewHolder> {
    public PoiListAdapter(Context context, OnItemClickListener<Poi> listener, OnItemButtonClickListener<Poi, EntityListAdapter<Poi, PoiViewHolder>> btnListener) {
        super(context, listener, btnListener);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_poi_list_item;
    }

    @Override
    protected PoiViewHolder createNewViewHolder(Context context, View itemView) {
        return new PoiViewHolder(context, itemView, this);
    }
}
