package com.cita.coupons;

import com.cita.coupons.dummy.DummyContent;
import com.cita.coupons.fragments.CouponFragment;
import com.cita.coupons.fragments.CouponsListFragment;
import com.cita.coupons.models.Coupon;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class CouponsActivity extends FragmentActivity implements
        CouponsListFragment.OnFragmentInteractionListener,
        CouponFragment.OnFragmentCouponInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                CouponsListFragment.newInstance()).commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    void processIntent(Intent intent) {

        Parcelable[] rawMsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present

        String promoIDNFC = new String(msg.getRecords()[0].getPayload());
        Log.v("MESSAGE", promoIDNFC);
        Coupon mCoupon;

        if (promoIDNFC != null) {

            if (promoIDNFC.equalsIgnoreCase("zara")) {

                mCoupon = DummyContent.ITEMS.get(0);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                        CouponFragment.newInstance(mCoupon)).commit();


            } else if (promoIDNFC.equalsIgnoreCase("starbucks")) {

                mCoupon = DummyContent.ITEMS.get(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                        CouponFragment.newInstance(mCoupon)).commit();

            } else if (promoIDNFC.equalsIgnoreCase("barnes")) {

                mCoupon = DummyContent.ITEMS.get(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                        CouponFragment.newInstance(mCoupon)).commit();

            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coupons, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Coupon coupon) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                CouponFragment.newInstance(coupon)).addToBackStack("coupon").commit();
    }

    @Override
    public void onCouponInteraction(Uri uri) {

    }
}
