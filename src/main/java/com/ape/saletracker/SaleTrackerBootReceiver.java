package com.ape.saletracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class SaleTrackerBootReceiver extends BroadcastReceiver {
    private static final String CLASS_NAME = "SaleTrackerBootReceiver---->";
    private static final String CONFIG_SPACE_TIME = "space_time";
    private static final String CONFIG_START_TIME = "start_time";
    private static final String TAG = "SaleTracker";
    private static final String VERSION_NUMBER = "20170627";

    private static SaleTrackerConfigSP mStciSP = new SaleTrackerConfigSP();
    private int DEFAULT_SPACE_TIME = Contant.SPACE_TIME;
    private int DEFAULT_START_TIME = Contant.START_TIME;
    private int mSpaceTime = Contant.SPACE_TIME;
    private int mStartTime = Contant.START_TIME;

    public void onReceive(Context context, Intent intent) {
        boolean isSendFlag = false;
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.d(TAG, "SaleTrackerBootReceiver---->onReceive: ACTION_BOOT_COMPLETED ; VERSION_NUMBER = 20170627");
            mStciSP.init(context);

            Map<String, String> configMap = SaleTrackerUti.readSendParamFromXml(context);
            if (configMap != null) {
                DEFAULT_SPACE_TIME = Integer.parseInt(configMap.get(CONFIG_SPACE_TIME));
                DEFAULT_START_TIME = Integer.parseInt(configMap.get(CONFIG_START_TIME));
                SharedPreferences pre = context.getSharedPreferences(Contant.STSDATA_CONFIG, 0);
                mSpaceTime = pre.getInt(Contant.KEY_SPACE_TIME, DEFAULT_SPACE_TIME);
                mStartTime = pre.getInt(Contant.KEY_OPEN_TIME, DEFAULT_START_TIME);
                Log.d(TAG, "SaleTrackerBootReceiver---->onReceive: DEFAULT_SPACE_TIME = " +
                        DEFAULT_SPACE_TIME + "; DEFAULT_START_TIME = " + DEFAULT_START_TIME);
            }

            boolean isSendedToTmeNet = mStciSP.readConfigForTmeWapAddr();
            boolean isSended = mStciSP.readSendedResult();
            int sendedNum = mStciSP.readSendedNumber();
            if (sendedNum < Contant.MAX_SEND_CONUT_BY_NET) {
                isSendFlag = true;
            }

            Log.d(TAG, "SaleTrackerBootReceiver---->onReceive: isSended = " + isSended +
                    "; sendedNum = " + sendedNum + "; mSpaceTime = " + mSpaceTime + "; mStartTime " +
                    mStartTime + "; DEFAULT_SPACE_TIME = " + DEFAULT_SPACE_TIME + "; DEFAULT_START_TIME = " + DEFAULT_START_TIME);
            if (!isSended && isSendFlag) {
                sendPendingIntent(context, Contant.SEND_TO_CUSTOM);
            } else if (!isSendedToTmeNet && isSendFlag) {
                sendPendingIntent(context, Contant.SEND_TO_TME);
            }
        }
    }

    private void sendPendingIntent(Context context, String sendWho) {
        Log.d(TAG, "SaleTrackerBootReceiver---->sendPendingIntent: sendWho = " + sendWho);
        Intent newIntent = new Intent(context, SaleTrackerService.class);
        newIntent.putExtra(Contant.SEND_TO, sendWho);
        context.startService(newIntent);

        AlarmManager am = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, new Intent(Contant.STS_REFRESH), 134217728);
        am.cancel(alarmIntent);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, (long) ((mStartTime * 60) * 1000), (long) ((mSpaceTime * 60) * 1000), alarmIntent);
    }
}
