package com.ape.saletracker;

import android.content.Context;
import android.util.Log;

public class SaleTrackerConfigSP {
    private static final String CLASS_NAME = "SaleTrackerConfigSP---->";
    public static final String KEY_SENDED_NUMBER = "KEY_SENDED_NUMBER";
    public static final String KEY_SENDED_SUCCESS = "KEY_SENDED_SUCCESS";
    private static final String KEY_TMEWAP_SENDED = "KEY_TMEWAP_SENDED";
    private static final String TAG = "SaleTracker";
    private static Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public Boolean readConfigForTmeWapAddr() {
        Context context = mContext;
        String str = Contant.STSDATA_CONFIG;
        Context context2 = mContext;
        boolean bRet = context.getSharedPreferences(str, 0).getBoolean(KEY_TMEWAP_SENDED, false);
        Log.d(TAG, "SaleTrackerConfigSP---->readConfigForTmeWapAddr() =" + bRet);
        return Boolean.valueOf(bRet);
    }

    public void writeConfigForTmeWapAddr(boolean flag) {
        Context context = mContext;
        String str = Contant.STSDATA_CONFIG;
        Context context2 = mContext;
        context.getSharedPreferences(str, 0).edit().putBoolean(KEY_TMEWAP_SENDED, flag).commit();
        Log.d(TAG, "SaleTrackerConfigSP----> writeConfigForTmeWapAddr() end flag=" + flag);
    }

    public boolean readSendedResult() {
        Context context = mContext;
        String str = Contant.STSDATA_CONFIG;
        Context context2 = mContext;
        boolean res = context.getSharedPreferences(str, 0).getBoolean(KEY_SENDED_SUCCESS, false);
        Log.d(TAG, "SaleTrackerConfigSP---->readSendedResult: res = " + res);
        return res;
    }

    public void writeSendedResult(boolean res) {
        Log.d(TAG, "SaleTrackerConfigSP---->writeSendedResult: res = " + res);
        Context context = mContext;
        String str = Contant.STSDATA_CONFIG;
        Context context2 = mContext;
        context.getSharedPreferences(str, 0).edit().putBoolean(KEY_SENDED_SUCCESS, res).commit();
    }

    public int readSendedNumber() {
        Context context = mContext;
        String str = Contant.STSDATA_CONFIG;
        Context context2 = mContext;
        int res = context.getSharedPreferences(str, 0).getInt(KEY_SENDED_NUMBER, 0);
        Log.d(TAG, "SaleTrackerConfigSP---->readSendedNumber: res = " + res);
        return res;
    }

    public void writeSendedNumber(int num) {
        Log.d(TAG, "SaleTrackerConfigSP---->writeSendedNumber: res = " + num);
        Context context = mContext;
        String str = Contant.STSDATA_CONFIG;
        Context context2 = mContext;
        context.getSharedPreferences(str, 0).edit().putInt(KEY_SENDED_NUMBER, num).commit();
    }
}
