package com.glasses.flightapp.flightapp.Adapter;

import android.view.View;
import android.widget.TextView;
import com.glasses.flightapp.flightapp.Models.Coupon;
import com.glasses.flightapp.flightapp.R;

/**
 * view holder mapping coupons to list item representations
 */
public class CouponViewHolder extends EntityViewHolder<Coupon, CouponListAdapter> {
    private TextView tvName;
    private TextView tvDescription;

    public CouponViewHolder(View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.name);
        tvDescription = itemView.findViewById(R.id.description);
    }

    public void bind(Coupon coupon, OnItemClickListener<Coupon> listener, OnItemButtonClickListener<Coupon, CouponListAdapter> btnListener) {
        tvName.setText(coupon.getName());
        tvDescription.setText(coupon.getDescription());

        if(listener != null)
            itemView.setOnClickListener(v -> listener.onItemClick(v, coupon));
    }
}
