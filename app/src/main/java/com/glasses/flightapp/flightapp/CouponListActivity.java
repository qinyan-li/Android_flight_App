package com.glasses.flightapp.flightapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.glasses.flightapp.flightapp.Adapter.CouponListAdapter;
import com.glasses.flightapp.flightapp.Database.CouponViewModel;

public class CouponListActivity extends NavigationActivity  {
    private RecyclerView couponList;

    private CouponViewModel viewModel;
    private CouponListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_list);

        initNavigation();

        viewModel = ViewModelProviders.of(this).get(CouponViewModel.class);
        couponList = findViewById(R.id.recyclerview);

        initList();
    }

    private void initList() {
        adapter = new CouponListAdapter(this, (view, item) -> {
            Intent intent = new Intent(CouponListActivity.this, CouponDetailActivity.class);
            intent.putExtra(CouponDetailActivity.COUPON_DETAIL, item);
            startActivity(intent);
        }, null);

        couponList.setAdapter(adapter);
        couponList.setLayoutManager(new LinearLayoutManager(this));
        couponList.addItemDecoration(new DividerItemDecoration(couponList.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAll().observe(this, coupons -> adapter.setElements(coupons));
    }
}
