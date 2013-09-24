package com.cita.coupons.utils;

import android.nfc.NdefRecord;
import android.util.Log;

import java.nio.charset.Charset;

/**
 * Created by Enrique Ramirez on 9/22/13.
 */
public class NFCUtils {

    public static NdefRecord createTextRecord(String payload) {

        byte[] textBytes = payload.getBytes(Charset.forName("US-ASCII"));



        Log.v("BYTES TO SEND", textBytes.toString() + "\n" + textBytes);

        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], textBytes);


        return record;
    }



}
