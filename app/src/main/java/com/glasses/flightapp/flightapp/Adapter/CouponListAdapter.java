package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.view.View;
import com.glasses.flightapp.flightapp.Models.Coupon;
import com.glasses.flightapp.flightapp.Models.Poi;
import com.glasses.flightapp.flightapp.R;

/**
 * adapter class handling display and behaviour of list items in
 * a coupon list (RecyclerView)
 */
public class CouponListAdapter extends EntityListAdapter<Coupon, CouponViewHolder> {
    public CouponListAdapter(Context context, OnItemClickListener<Coupon> listener, OnItemButtonClickListener<Coupon, EntityListAdapter<Coupon, CouponViewHolder>> btnListener) {
        super(context, listener, btnListener);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_coupon_list_item;
    }

    @Override
    protected CouponViewHolder createNewViewHolder(Context context, View itemView) {
        return new CouponViewHolder(itemView);
    }
}
