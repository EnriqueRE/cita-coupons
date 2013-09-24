package com.cita.coupons.fragments;

import com.cita.coupons.R;
import com.cita.coupons.dummy.DummyContent;
import com.cita.coupons.models.Coupon;
import com.cita.coupons.utils.MimeType;
import com.cita.coupons.utils.NFCUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by enriqueramirez on 9/21/13.
 */

public class CouponFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COUPON = "param1";

    Coupon mCoupon;

    NfcAdapter mNfcAdapter;

    NdefMessage mMessage;

    private ImageView promoImg;

    private TextView article_title;

    private TextView article_byline;

    private TextView articleBody;

    private String promoIDNFC;

    private NfcAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private Coupon mParam1;

    private OnFragmentCouponInteractionListener mListener;

    public CouponFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param coupon The information from the coupon to display.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponFragment newInstance(Coupon coupon) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COUPON, coupon);
        fragment.setArguments(args);
        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_COUPON);
            mCoupon = mParam1;
        }

        // see if app was started from a tag and show game console
        Intent intent = getActivity().getIntent();
        if (intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
            Parcelable[] rawMsgs = getActivity().getIntent()
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            String dataFromTag = new String(cardRecord.getPayload());

            promoIDNFC = dataFromTag;

        }

        if (promoIDNFC != null) {

            if (promoIDNFC.equalsIgnoreCase("zara")) {

                mCoupon = DummyContent.ITEMS.get(0);

            } else if (promoIDNFC.equalsIgnoreCase("starbucks")) {

                mCoupon = DummyContent.ITEMS.get(1);

            } else if (promoIDNFC.equalsIgnoreCase("barnes")) {

                mCoupon = DummyContent.ITEMS.get(2);

            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root_view = inflater.inflate(R.layout.fragment_coupon_detail, container, false);

        promoImg = (ImageView) root_view.findViewById(R.id.photo);
        article_title = (TextView) root_view.findViewById(R.id.article_title);
        article_byline = (TextView) root_view.findViewById(R.id.article_byline);
        articleBody = (TextView) root_view.findViewById(R.id.article_body);

        promoImg.setImageDrawable(getActivity().getResources().getDrawable(mCoupon.getImage()));
        article_title.setText(mCoupon.getCompany());
        article_byline.setText(mCoupon.getPromoTitle());
        articleBody.setText(mCoupon.getDescription());

        String tagToSend = "";

        if (mCoupon.getId().equalsIgnoreCase("1")) {

            tagToSend = "zara";

        } else if (mCoupon.getId().equalsIgnoreCase("2")) {

            tagToSend = "starbucks";

        } else {

            tagToSend = "barnes";

        }

        mAdapter = NfcAdapter.getDefaultAdapter(getActivity());

        mMessage = new NdefMessage(NFCUtils.createTextRecord(tagToSend));

        mAdapter.setNdefPushMessage(mMessage, getActivity());

        return root_view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCouponInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentCouponInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity. <p> See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating
     * with Other Fragments</a> for more information.
     */
    public interface OnFragmentCouponInteractionListener {

        // TODO: Update argument type and name
        public void onCouponInteraction(Uri uri);
    }


}
