package com.glasses.flightapp.flightapp;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.glasses.flightapp.flightapp.Adapter.PoiListAdapter;
import com.glasses.flightapp.flightapp.Database.PoiViewModel;
import com.glasses.flightapp.flightapp.Models.Poi;

import java.util.List;

public class POIListActivity  extends NavigationActivity  {
    private RecyclerView poiList;

    private PoiViewModel viewModel;
    private PoiListAdapter adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_list);

        initNavigation();

        viewModel = ViewModelProviders.of(this).get(PoiViewModel.class);
        poiList = findViewById(R.id.recyclerview);

        initPoiList();
    }
    private void initPoiList() {
        adapter = new PoiListAdapter(this, (view, item) -> {
            Intent intent = new Intent(POIListActivity.this, POIDetailsActivity.class);
            intent.putExtra(POIDetailsActivity.POI_DETAIL_ID, item.getId());
            startActivity(intent);
        }, (view, flight, flAdapter) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(POIListActivity.this);
            builder
                    .setMessage(R.string.dialog_remove_flight)
                    .setTitle(R.string.dialog_title_remove_flight)
                    .setPositiveButton(R.string.button_ok, (dialog, which) -> {
                        viewModel.delete(flight);
                        List<Poi> pois = flAdapter.getElements();
                        pois.remove(flight);

                        flAdapter.setElements(pois);
                        flAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.button_cancel, null)
                    .setCancelable(true);

            builder.create().show();
        });

        poiList.setAdapter(adapter);
        poiList.setLayoutManager(new LinearLayoutManager(this));
        poiList.addItemDecoration(new DividerItemDecoration(poiList.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllPois().observe(this, pois -> {
            adapter.setElements(pois);
        });
    }
}
