package com.wrapper.stk;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.SubscriptionInfo;

import java.util.List;

public class HideMethod {

    public static class SubscriptionManager {
        public static SubscriptionManager sInstance = new SubscriptionManager();

        public static SubscriptionManager getDefault() {
            return sInstance;
        }

        public void setDefaultSmsSubId(int subId, Context context) {
            android.telephony.SubscriptionManager.from(context).setDefaultSmsSubId(subId);
        }

        public boolean isActiveSubId(int subId, Context context) {
            return android.telephony.SubscriptionManager.from(context).isActiveSubId(subId);
        }

        public List<SubscriptionInfo> getActiveSubscriptionInfoList(Context context) {
            return android.telephony.SubscriptionManager.from(context).getActiveSubscriptionInfoList();
        }
    }

    public static class TelephonyManager {
        public static final int SIM_STATE_ABSENT = 1;
        public static final int SIM_STATE_NETWORK_LOCKED = 4;
        public static final int SIM_STATE_PIN_REQUIRED = 2;
        public static final int SIM_STATE_PUK_REQUIRED = 3;
        public static final int SIM_STATE_READY = 5;
        public static final int SIM_STATE_UNKNOWN = 0;
        public static TelephonyManager sInstance = new TelephonyManager();

        public int getSimState() {
            return android.telephony.TelephonyManager.getDefault().getSimState();
        }

        public static TelephonyManager getDefault() {
            return sInstance;
        }

        public static String getNetworkOperatorName() {
            return android.telephony.TelephonyManager.getDefault().getNetworkOperatorName(android.telephony.SubscriptionManager.getDefaultSubId());
        }

        public String getNetworkOperatorName(int subId) {
            return android.telephony.TelephonyManager.getDefault().getNetworkOperatorName(subId);
        }

        public String getDeviceId(int slotId, Context context) {
            android.telephony.TelephonyManager.getDefault();
            return android.telephony.TelephonyManager.from(context).getDeviceId(slotId);
        }

        public String getSimOperator() {
            return android.telephony.TelephonyManager.getDefault().getSimOperator();
        }

        public CellLocation getCellLocation() {
            return android.telephony.TelephonyManager.getDefault().getCellLocation();
        }
    }
}
