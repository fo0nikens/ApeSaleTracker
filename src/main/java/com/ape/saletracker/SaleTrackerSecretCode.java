package com.ape.saletracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.internal.telephony.TelephonyIntents;

public class SaleTrackerSecretCode extends BroadcastReceiver {
    private static final String TAG = "SaleTracker";

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TelephonyIntents.SECRET_CODE_ACTION)) {
            Log.d(TAG, "SaleTrackerSecretCode start");
            try {
                Intent intent = new Intent();
                intent.setClassName(BuildConfig.APPLICATION_ID, "com.ape.saletracker.SaleTrackerActivity");
                intent.setFlags(268435456);
                context.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }
}
