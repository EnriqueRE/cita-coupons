package com.cita.coupons;

import com.cita.coupons.utils.MimeType;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Retro console "collectable cards/top trumps" NFC demo
 *
 * @author richardleggett http://www.richardleggett.co.uk
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

    private NfcAdapter mAdapter;

    private boolean mInWriteMode;

    private Button mButtonZara;

    private Button mButtonStarbucks;

    private Button mButtonBN;

    private TextView mTextView;

    private String messageToWrite = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // grab our NFC Adapter
        mAdapter = NfcAdapter.getDefaultAdapter(this);

        // button that starts the tag-write procedure
        mButtonZara = (Button) findViewById(R.id.btn_zara);
        mButtonStarbucks = (Button) findViewById(R.id.btn_starbucks);
        mButtonBN = (Button) findViewById(R.id.btn_bn);

        mButtonZara.setOnClickListener(this);
        mButtonStarbucks.setOnClickListener(this);
        mButtonBN.setOnClickListener(this);

        // TextView that we'll use to output messages to screen
        mTextView = (TextView) findViewById(R.id.text_view);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_zara:
                displayMessage("Touch and hold tag against phone to write.");
                enableWriteMode();
                messageToWrite = "zara";
                break;

            case R.id.btn_starbucks:
                displayMessage("Touch and hold tag against phone to write.");
                enableWriteMode();
                messageToWrite = "starbucks";
                break;

            case R.id.btn_bn:
                displayMessage("Touch and hold tag against phone to write.");
                messageToWrite = "barnes";
                enableWriteMode();
                break;


        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        disableWriteMode();
    }

    /**
     * Called when our blank tag is scanned executing the PendingIntent
     */
    @Override
    public void onNewIntent(Intent intent) {
        if (mInWriteMode) {
            mInWriteMode = false;

            // write to newly scanned tag
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            writeTag(tag);
        }
    }

    /**
     * Force this Activity to get NFC events first
     */
    private void enableWriteMode() {
        mInWriteMode = true;

        // set up a PendingIntent to open the app when a tag is scanned
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[]{tagDetected};

        mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
    }

    private void disableWriteMode() {
        mAdapter.disableForegroundDispatch(this);
    }

    /**
     * Format a tag and write our NDEF message
     */
    private boolean writeTag(Tag tag) {
        // record to launch Play Store if app is not installed
        NdefRecord appRecord = NdefRecord.createApplicationRecord("com.cita.coupons");

        // record that contains our custom "retro console" game data, using custom MIME_TYPE
        byte[] payload = messageToWrite.getBytes();
        byte[] mimeBytes = MimeType.NFC_DEMO.getBytes(Charset.forName("US-ASCII"));
        NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes,
                new byte[0], payload);
        NdefMessage message = new NdefMessage(new NdefRecord[]{cardRecord, appRecord});

        try {
            // see if tag is already NDEF formatted
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                    displayMessage("Read-only tag.");
                    return false;
                }

                // work out how much space we need for the data
                int size = message.toByteArray().length;
                if (ndef.getMaxSize() < size) {
                    displayMessage("Tag doesn't have enough free space.");
                    return false;
                }

                ndef.writeNdefMessage(message);
                displayMessage("Tag written successfully.");
                return true;
            } else {
                // attempt to format tag
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        displayMessage("Tag written successfully!\nClose this app and scan tag.");
                        return true;
                    } catch (IOException e) {
                        displayMessage("Unable to format tag to NDEF.");
                        return false;
                    }
                } else {
                    displayMessage("Tag doesn't appear to support NDEF format.");
                    return false;
                }
            }
        } catch (Exception e) {
            displayMessage("Failed to write tag");
        }

        return false;
    }

    private void displayMessage(String message) {
        mTextView.setText(message);
    }


}