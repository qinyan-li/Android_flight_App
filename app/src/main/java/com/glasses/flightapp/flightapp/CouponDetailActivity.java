package com.glasses.flightapp.flightapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import android.widget.TextView;
import com.glasses.flightapp.flightapp.Models.Coupon;
import com.glasses.flightapp.flightapp.Util.QRCodeUtil;

public class CouponDetailActivity extends AppCompatActivity {
    public static final String COUPON_DETAIL = "coupon_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Coupon coupon = (Coupon) getIntent().getSerializableExtra(COUPON_DETAIL);

        TextView description = findViewById(R.id.coupon_description);
        description.setText(coupon.getDescription());

        ImageView mImageView = findViewById(R.id.iv);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(coupon.getCode(), 1024, 1024);
        mImageView.setImageBitmap(mBitmap);
    }
}
