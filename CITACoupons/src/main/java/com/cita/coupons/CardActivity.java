package com.cita.coupons;

import com.cita.coupons.fragments.CouponFragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CardActivity extends FragmentActivity implements
        CouponFragment.OnFragmentCouponInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);


    }

    @Override
    public void onCouponInteraction(Uri uri) {

    }
}
