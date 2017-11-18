package com.ape.saletracker;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.telephony.PhoneConstants;
import com.wrapper.stk.HideMethod.SubscriptionManager;

import java.util.List;
import java.util.Map;

public class SaleTrackerService extends Service {
    private static final String CLASS_NAME = "SaleTrackerService---->";
    private static final String CONFIG_CLIENT_NO = "client_no";
    private static final String CONFIG_HOST_URL = "host_url";
    private static final String CONFIG_NOTICE = "notice";
    private static final String CONFIG_SEND_TYPE = "send_type";
    private static final String CONFIG_SPACE_TIME = "space_time";
    private static final String CONFIG_START_TIME = "start_time";
    private static final String DEFAULT_VALUE = "defaultSet";

    private static String NUM_SMS = "18565857256";
    public static final int STS_CONFIG_TYPE = 3;
    private static final String TAG = "SaleTracker";
    private static boolean airplaneModeOn = false;
    private static String mClientNo = "0000001000";
    private static Context mContext = null;
    public static int mDefaultSendType = 1;
    private static int mDefaultSendTypeTmp = 1;
    private static String mHosturl = "http://eservice.tinno.com/eservice/stsReport?reptype=report";
    private static boolean mIsNeedNoticePop = false;
    private static boolean mIsSendOnNetConnected = false;
    private static boolean mIsSendSuccess = false;
    private static int mMsgSendNum = 0;
    private static boolean mNotifyFromTestActivity = false;
    private static int mSpaceTimeFromXML = Contant.SPACE_TIME;
    private static int mStartTimeFromXML = Contant.START_TIME;
    private static SaleTrackerConfigSP mStciSP = new SaleTrackerConfigSP();
    private static boolean mSwitchSendType = false;
    private static TelephonyManager mTm = null;
    private static String mTmeHosturl = "http://eservice.tinno.com/eservice/stsReport?reptype=report";
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY4gRmZHQimOWRr99Yi64jGDGMJSa7Awx05J9gpJuQz9tZPrP6QCWFJNpBxBxS_UMg-36FjFl_l8qLBWl-q7pVlyc4qdxq4HGQKJfdBm8aOFQ3Ekaylm1p2s5YKxvYTHDydKG72EXDdvbea8ZvXA1rKP-MpOWKA7XmkLpChQqrsQIDAQAB";

    @SuppressLint("HandlerLeak")
    private Handler MessageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.d(SaleTrackerService.TAG, "SaleTrackerService---->handleMessage() send type is  by SMS");
                    if (isSmsAvailable()) {
                        sendContentBySMS();
                        return;
                    }
                    return;
                case 1:
                    Log.d(SaleTrackerService.TAG, "SaleTrackerService---->handleMessage() send type is by NET");
                    if (isNetworkAvailable()) {
                        sendContentByNetwork();
                        return;
                    }
                    return;
                case 2:
                    Boolean val = msg.obj;
                    Log.d(SaleTrackerService.TAG, "SaleTrackerService---->handleMessage() sended by net and  return  =" + val);
                    if (val) {
                        mIsSendSuccess = true;
                        mIsSendOnNetConnected = false;
                        mStciSP.writeSendedResult(val.booleanValue());
                        mStciSP.writeConfigForTmeWapAddr(true);
                        refreshPanelStatus();
                        stopSelf();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    private final BroadcastReceiver mNetConnectReceiver = new StsNetConnectReceiver();
    private final BroadcastReceiver mSaleTrackerReceiver = new SaleTrackerReceiver();
    public int mSpaceTime = Contant.SPACE_TIME;
    public int mStartTime = Contant.START_TIME;
    private String mStrCountry = DEFAULT_VALUE;
    public String mStrIMEI = Contant.NULL_IMEI;
    private String mStrModel = DEFAULT_VALUE;
    private String mStrPhoneNo = DEFAULT_VALUE;
    private final BroadcastReceiver mStsAirplanReceiver = new StsAirplanReceiver();

    private Runnable runnable = new Runnable() {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r8 = this;
            r7 = 2;
            r4 = 0;
            r3 = java.lang.Boolean.valueOf(r4);
            r4 = "SaleTracker";
            r5 = "SaleTrackerService---->run()  Runnable-->start";
            android.util.Log.d(r4, r5);	 Catch:{ Exception -> 0x0083 }
            r2 = new com.ape.saletracker.HttpRequester;	 Catch:{ Exception -> 0x0083 }
            r2.<init>();	 Catch:{ Exception -> 0x0083 }
            r4 = com.ape.saletracker.SaleTrackerService.this;	 Catch:{ Exception -> 0x0083 }
            r4 = r4.url;	 Catch:{ Exception -> 0x0083 }
            r1 = r2.sendGet(r4);	 Catch:{ Exception -> 0x0083 }
            r4 = r1.getContentCollection();	 Catch:{ Exception -> 0x0083 }
            if (r4 == 0) goto L_0x0045;
        L_0x0022:
            r4 = r1.getContentCollection();	 Catch:{ Exception -> 0x0083 }
            r5 = 0;
            r4 = r4.get(r5);	 Catch:{ Exception -> 0x0083 }
            if (r4 == 0) goto L_0x0045;
        L_0x002d:
            r4 = r1.getContentCollection();	 Catch:{ Exception -> 0x0083 }
            r5 = 0;
            r4 = r4.get(r5);	 Catch:{ Exception -> 0x0083 }
            r4 = (java.lang.String) r4;	 Catch:{ Exception -> 0x0083 }
            r5 = "0";
            r4 = r4.equals(r5);	 Catch:{ Exception -> 0x0083 }
            if (r4 == 0) goto L_0x0045;
        L_0x0040:
            r4 = 1;
            r3 = java.lang.Boolean.valueOf(r4);	 Catch:{ Exception -> 0x0083 }
        L_0x0045:
            r4 = r1.getCode();	 Catch:{ Exception -> 0x0083 }
            r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r4 == r5) goto L_0x006e;
        L_0x004d:
            r4 = "SaleTracker";
            r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0083 }
            r5.<init>();	 Catch:{ Exception -> 0x0083 }
            r6 = "SaleTrackerService---->run()   Runnable--->hr.getCode() =";
            r5 = r5.append(r6);	 Catch:{ Exception -> 0x0083 }
            r6 = r1.getCode();	 Catch:{ Exception -> 0x0083 }
            r5 = r5.append(r6);	 Catch:{ Exception -> 0x0083 }
            r5 = r5.toString();	 Catch:{ Exception -> 0x0083 }
            android.util.Log.d(r4, r5);	 Catch:{ Exception -> 0x0083 }
            r4 = 0;
            r3 = java.lang.Boolean.valueOf(r4);	 Catch:{ Exception -> 0x0083 }
        L_0x006e:
            r4 = "SaleTracker";
            r5 = "SaleTrackerService---->run()   Runnable--->result";
            android.util.Log.d(r4, r5);
            r4 = com.ape.saletracker.SaleTrackerService.this;
            r4 = r4.MessageHandler;
            r4 = r4.obtainMessage(r7, r3);
            r4.sendToTarget();
        L_0x0082:
            return;
        L_0x0083:
            r0 = move-exception;
            r4 = "SaleTracker";
            r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ba }
            r5.<init>();	 Catch:{ all -> 0x00ba }
            r6 = "SaleTrackerService---->run()  Exception";
            r5 = r5.append(r6);	 Catch:{ all -> 0x00ba }
            r6 = r0.toString();	 Catch:{ all -> 0x00ba }
            r5 = r5.append(r6);	 Catch:{ all -> 0x00ba }
            r5 = r5.toString();	 Catch:{ all -> 0x00ba }
            android.util.Log.d(r4, r5);	 Catch:{ all -> 0x00ba }
            r4 = 0;
            r3 = java.lang.Boolean.valueOf(r4);	 Catch:{ all -> 0x00ba }
            r4 = "SaleTracker";
            r5 = "SaleTrackerService---->run()   Runnable--->result";
            android.util.Log.d(r4, r5);
            r4 = com.ape.saletracker.SaleTrackerService.this;
            r4 = r4.MessageHandler;
            r4 = r4.obtainMessage(r7, r3);
            r4.sendToTarget();
            goto L_0x0082;
        L_0x00ba:
            r4 = move-exception;
            r5 = "SaleTracker";
            r6 = "SaleTrackerService---->run()   Runnable--->result";
            android.util.Log.d(r5, r6);
            r5 = com.ape.saletracker.SaleTrackerService.this;
            r5 = r5.MessageHandler;
            r5 = r5.obtainMessage(r7, r3);
            r5.sendToTarget();
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ape.saletracker.SaleTrackerService.2.run():void");
        }
    };
    private String url;

    private class SaleTrackerReceiver extends BroadcastReceiver {

        private SaleTrackerReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() onReceive start: action = " + intent.getAction() + "; elapsed time = " + (SystemClock.elapsedRealtime() / 1000));
            if (intent.getAction().equals(Contant.STS_REFRESH)) {
                if (SaleTrackerService.mIsSendSuccess || SaleTrackerService.mMsgSendNum > Contant.MAX_SEND_CONUT_BY_NET) {
                    Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver()  The message is send success or the maximum sended number, stop SaleTrackerService");
                    stopSelf();
                    return;
                }

                SharedPreferences pre = getSharedPreferences(Contant.STSDATA_CONFIG, 0);
                mStartTime = pre.getInt(Contant.KEY_OPEN_TIME, SaleTrackerService.mStartTimeFromXML);
                mSpaceTime = pre.getInt(Contant.KEY_SPACE_TIME, SaleTrackerService.mSpaceTimeFromXML);
                SaleTrackerService.mNotifyFromTestActivity = pre.getBoolean(Contant.KEY_NOTIFY, getResources().getBoolean(R.bool.dialog_notify));
                SaleTrackerService.mSwitchSendType = pre.getBoolean(Contant.KEY_SWITCH_SENDTYPE, false);
                if (SaleTrackerService.mSwitchSendType) {
                    SaleTrackerService.mDefaultSendType = pre.getInt(Contant.KEY_SELECT_SEND_TYPE, 1);
                    Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() only for test ----- send type switch : " + SaleTrackerService.mDefaultSendType);
                }
                int MsgSendMode = -1;
                Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() mDefaultSendType= " + SaleTrackerService.mDefaultSendType);
                switch (SaleTrackerService.mDefaultSendType) {
                    case 0:
                        Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() send type by SMS  mMsgSendNum = " + SaleTrackerService.mMsgSendNum);
                        if (SaleTrackerService.mMsgSendNum % 24 == 0) {
                            MsgSendMode = 0;
                            break;
                        }
                        break;
                    case 1:
                        Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() send type by NET  mMsgSendNum = " + SaleTrackerService.mMsgSendNum);
                        MsgSendMode = 1;
                        SaleTrackerService.mIsSendOnNetConnected = true;
                        break;
                    case 2:
                        if (SaleTrackerService.mMsgSendNum > 0 && SaleTrackerService.mMsgSendNum % 24 == 0) {
                            Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() send type by NET_AND_SMS--sms  mMsgSendNum = " + SaleTrackerService.mMsgSendNum);
                            MsgSendMode = 0;
                            break;
                        }
                        Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() MSG_SEND_BY_NET_AND_SMS--net  mMsgSendNum = " + SaleTrackerService.mMsgSendNum);
                        MsgSendMode = 1;
                        SaleTrackerService.mIsSendOnNetConnected = true;
                        break;
                }
                popNotifyWindow(context);
                SaleTrackerService.mStciSP.writeSendedNumber(SaleTrackerService.access$604());
                Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() MsgSendMode = " + MsgSendMode);
                if (MsgSendMode != -1) {
                    if (MessageHandler.hasMessages(1)) {
                        MessageHandler.removeMessages(1);
                    }
                    MessageHandler.obtainMessage(MsgSendMode).sendToTarget();
                }
            } else if (intent.getAction().equals(Contant.ACTION_SMS_SEND)) {
                String type = intent.getStringExtra("send_by");
                Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() sended by SMS type and return  send by" + type);
                if ("TME".equals(type)) {
                    switch (getResultCode()) {
                        case -1:
                            Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() SMS is send OK ");
                            SaleTrackerService.mDefaultSendType = 1;
                            SaleTrackerService.mDefaultSendTypeTmp = 1;
                            SaleTrackerService.mHosturl = SaleTrackerService.mTmeHosturl;

                            if (SaleTrackerService.mSwitchSendType) {
                                getSharedPreferences(Contant.STSDATA_CONFIG, 0).edit().putBoolean(Contant.KEY_SWITCH_SENDTYPE, false).commit();
                                SaleTrackerService.mSwitchSendType = false;
                                Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() only for test -----open test send type switch  :   " + SaleTrackerService.mDefaultSendType);
                            }
                            SaleTrackerService.mStciSP.writeSendedResult(true);
                            refreshPanelStatus();
                            return;
                        default:
                            Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() SMS is send error ResultCode=" + getResultCode());
                            return;
                    }
                }
            } else if (intent.getAction().equals(Contant.ACTION_SMS_DELIVERED)) {
                Log.d(SaleTrackerService.TAG, "SaleTrackerService---->SaleTrackerReceiver() onReceive: ACTION_SMS_DELIVERED; ResultCode = " + getResultCode());
            }
        }
    }

    private class StsAirplanReceiver extends BroadcastReceiver {

        private StsAirplanReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Log.d(SaleTrackerService.TAG, "SaleTrackerService---->StsAirplanReceiver()  onReceive start");
            if (!intent.getAction().equals("android.intent.action.AIRPLANE_MODE")) {
                return;
            }

            if (intent.getBooleanExtra("state", false)) {
                Log.d(SaleTrackerService.TAG, "SaleTrackerService---->StsAirplanReceiver()  : ACTION_AIRPLANE_MODE_CHANGED in airplane mSaleTrackerReceiver = " + mSaleTrackerReceiver);
                try {
                    unregisterReceiver(mSaleTrackerReceiver);
                    unregisterReceiver(mNetConnectReceiver);
                    return;
                } catch (IllegalArgumentException e) {
                    Log.e(SaleTrackerService.TAG, "SaleTrackerService---->StsAirplanReceiver()   registerReceiverSafe(), FAIL!");
                    return;
                }
            }
            Log.d(SaleTrackerService.TAG, "SaleTrackerService---->StsAirplanReceiver() : ACTION_AIRPLANE_MODE_CHANGED out airplane");

            registerReceiver(mSaleTrackerReceiver, new IntentFilter(Contant.STS_REFRESH));
            registerReceiver(mSaleTrackerReceiver, new IntentFilter(Contant.ACTION_SMS_SEND));
            registerReceiver(mSaleTrackerReceiver, new IntentFilter(Contant.ACTION_SMS_DELIVERED));
            registerReceiver(mNetConnectReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    private class StsNetConnectReceiver extends BroadcastReceiver {

        private StsNetConnectReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Log.d(SaleTrackerService.TAG, "SaleTrackerService----> StsNetConnectReceiver() onReceive: start intent=" + intent.getAction());
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                boolean bMobile = SaleTrackerService.checkNetworkConnection(context);

                if (SaleTrackerService.mIsSendOnNetConnected && bMobile && !SaleTrackerService.mIsSendSuccess) {
                    Log.d(SaleTrackerService.TAG, "SaleTrackerService----> StsNetConnectReceiver()  net was connected and start to send ");
                    if (MessageHandler.hasMessages(1)) {
                        MessageHandler.removeMessages(1);
                    }
                    MessageHandler.obtainMessage(1).sendToTarget();
                    return;
                }
                Log.d(SaleTrackerService.TAG, "SaleTrackerService----> StsNetConnectReceiver()  net was connected");
            }
        }
    }

    static /* synthetic */ int access$604() {
        int i = mMsgSendNum + 1;
        mMsgSendNum = i;
        return i;
    }

    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Log.d(TAG, "SaleTrackerService---->init() start");
        mContext = getApplicationContext();
        mStciSP.init(mContext);
        mMsgSendNum = mStciSP.readSendedNumber();

        try {
            mTm = (TelephonyManager) mContext.getSystemService(PhoneConstants.PHONE_KEY);
        } catch (Exception e) {
            Log.d(TAG, "SaleTrackerService---->init() ********error******** TelephonyManager.getDefault()" +
                    " = null ********error********");
            e.printStackTrace();
        }

        pickCountryConfigs();

        registerReceiver(mStsAirplanReceiver, new IntentFilter("android.intent.action.AIRPLANE_MODE"));

        if (!airplaneModeOn) {
            Log.d(TAG, "SaleTrackerService---->init()   registerReceiver mSaleTrackerReceiver");
            registerReceiver(mSaleTrackerReceiver, new IntentFilter(Contant.STS_REFRESH));
            registerReceiver(mSaleTrackerReceiver, new IntentFilter(Contant.ACTION_SMS_SEND));
            registerReceiver(mSaleTrackerReceiver, new IntentFilter(Contant.ACTION_SMS_DELIVERED));
            registerReceiver(mNetConnectReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId) {
        if (intent == null) {
            Log.d(TAG, "SaleTrackerService---->onStart()  ******************* intent = null*********************");
            super.onStart(intent, startId);
            return;
        }

        String type = intent.getStringExtra(Contant.SEND_TO);
        Log.d(TAG, "SaleTrackerService---->onStart() this content sendto = " + type);
        if (Contant.SEND_TO_TME.equals(type)) {
            mDefaultSendType = 1;
            mDefaultSendTypeTmp = 1;
            mHosturl = mTmeHosturl;
        }
        super.onStart(intent, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "SaleTrackerService---->onDestroy() unregisterReceiver");
        airplaneModeOn = false;
        try {
            unregisterReceiver(mSaleTrackerReceiver);
            unregisterReceiver(mStsAirplanReceiver);
            unregisterReceiver(mNetConnectReceiver);
        } catch (Exception e) {
            Log.e(TAG, "SaleTrackerService---->onDestroy() Exception" + e.getMessage());
        }
        ((AlarmManager) mContext.getSystemService(ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(Contant.STS_REFRESH), 134217728));
    }

    public static void resetMsgSendNum() {
        mStciSP.writeSendedNumber(0);
    }

    private void sendContentBySMS() {
        Log.d(TAG, "SaleTrackerService---->sendContentBySMS()  start");

        String msg_contents = setSendContent();
        if ("".equals(msg_contents)) {
            Log.e(TAG, "SaleTrackerService---->sendContentBySMS()   GET msg_contents  faile");
            return;
        }

        Intent smsSend = new Intent(Contant.ACTION_SMS_SEND);
        smsSend.putExtra("send_by", "TME");
        PendingIntent sentPending = PendingIntent.getBroadcast(this, 0, smsSend, 0);
        PendingIntent deliverPending = PendingIntent.getBroadcast(this, 0, new Intent(Contant.ACTION_SMS_DELIVERED), 0);

        setDestNum();

        try {
            if (TextUtils.isEmpty(mStrPhoneNo)) {
                throw new IllegalArgumentException("Invalid destinationAddress");
            } else if (TextUtils.isEmpty(msg_contents)) {
                throw new IllegalArgumentException("Invalid message body");
            } else {
                int defaultSubId = SmsManager.getDefault().getSubscriptionId();
                Log.d(TAG, "SaleTrackerService---->sendContentBySMS() defaultSubId = " + defaultSubId);
                Context context = getApplicationContext();
                SubscriptionManager subscriptionManager = SubscriptionManager.getDefault();
                if (defaultSubId < 0) {
                    List<SubscriptionInfo> subInfoList = subscriptionManager.getActiveSubscriptionInfoList(context);
                    Log.d(TAG, "sendContentBySMS(): subInfoList = " + subInfoList);
                    if (subInfoList != null && subInfoList.size() >= 1) {
                        for (SubscriptionInfo subInfo : subInfoList) {
                            Log.d(TAG, "sendContentBySMS(): subInfo = " + subInfo);
                            int subId = subInfo.getSubscriptionId();
                            if (subscriptionManager.isActiveSubId(subId, context)) {
                                subscriptionManager.setDefaultSmsSubId(subId, context);
                                break;
                            }
                        }
                    }
                }
                SmsManager.getDefault().sendTextMessage(mStrPhoneNo, null, msg_contents, sentPending, deliverPending);
                if (defaultSubId < 0) {
                    subscriptionManager.setDefaultSmsSubId(defaultSubId, context);
                }
                Log.d(TAG, "SaleTrackerService---->sendContentBySMS()  end");
            }
        } catch (SecurityException e) {
            Log.d(TAG, "SaleTrackerService----> send sms fail");
        }
    }

    private void sendContentByNetwork() {
        String msg_contents = setSendContent();
        if ("".equals(msg_contents)) {
            Log.e(TAG, "SaleTrackerService---->sendContentByNetwork()  sendContentByNetwork--> send_sms GET msg_contents  faile");
            return;
        }

        try {
            int msgid = mMsgSendNum;
            url = mHosturl + "&msgid=" + msgid + "&repinfo=" + RSAHelper.encrypt(publicKey, msg_contents);
            new Thread(runnable).start();
        } catch (Exception e) {
            Log.d(TAG, "SaleTrackerService---->sendContentByNetwork()  **************** err****************");
        }
    }

    private void popNotifyWindow(Context context) {
        if ((mNotifyFromTestActivity || mIsNeedNoticePop) && mMsgSendNum == 0) {
            Log.d(TAG, "SaleTrackerService---->popNotifyWindow()  dialog start");
            Intent Dialog = new Intent(context, WIKOSTSScreen.class);
            Dialog.addFlags(268435456);
            context.startActivity(Dialog);
            Log.d(TAG, "SaleTrackerService---->popNotifyWindow()  dialog finish");
        }
    }

    private boolean isSmsAvailable() {
        int sim_state = mTm.getSimState();
        Log.d(TAG, "SaleTrackerService---->isSmsAvailable(): sim_state  " + sim_state);
        return 5 == sim_state;
    }

    public String getNetworkOperatorName() {
        return ((TelephonyManager) mContext.getSystemService(PhoneConstants.PHONE_KEY)).getNetworkOperatorName();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo state : info) {
                    if (state.getState() == State.CONNECTED) {
                        Log.d(TAG, "SaleTrackerService---->isNetworkAvailable()   return true");
                        return true;
                    }
                }
            }
        }
        Log.d(TAG, "SaleTrackerService---->isNetworkAvailable() return false");
        return false;
    }

    public static String getIMEI() {
        String deviceId = mTm.getDeviceId(0);
        Log.d(TAG, "SaleTrackerService---->getIMEI()   imei = " + deviceId);
        if (deviceId == null || deviceId.isEmpty()) {
            return new String(Contant.NULL_IMEI);
        }
        return deviceId;
    }

    public static String getIMEIPK() {
        String imei1 = mTm.getDeviceId(0);
        String imei2 = mTm.getDeviceId(1);
        if (imei1 == null || imei1.isEmpty()) {
            imei1 = Contant.NULL_IMEI;
        }
        if (imei2 == null || imei2.isEmpty()) {
            imei2 = Contant.NULL_IMEI;
        }
        Log.d(TAG, "SaleTrackerService---->getIMEI()   imei1 = " + imei1 + "; imei2 = " + imei2);
        return imei1 + " " + imei2;
    }

    private void setDestNum() {
        mStrPhoneNo = NUM_SMS;
        Log.d(TAG, "SaleTrackerService---->setDestNum() =" + mStrPhoneNo);
    }

    public String setSendContent() {
        StringBuffer smsContent = new StringBuffer();
        mStrIMEI = getIMEI();
        if (mStrIMEI.equals(Contant.NULL_IMEI)) {
            Log.d(TAG, "SaleTrackerService---->init()    ********error********getIMEI() = null ***********error******");
            return "";
        }

        String REG = mStrIMEI;
        String SAP_NO = mClientNo;

        StringBuffer PRODUCT_NO = new StringBuffer();
        if (!DEFAULT_VALUE.equals(mStrModel) && !"".equals(mStrModel)) {
            PRODUCT_NO.append(mStrModel);
        } else if (SaleTrackerUti.isQMobile()) {
            String model = SystemProperties.get("ro.product.model.pk", Build.MODEL);
            if (model.startsWith("QMobile ")) {
                PRODUCT_NO.append(model.substring("QMobile ".length()));
            } else {
                PRODUCT_NO.append(model);
            }
        } else {
            PRODUCT_NO.append(Build.MODEL);
        }

        String SN_NO = Build.SERIAL;

        StringBuffer SOFTWARE_NO = new StringBuffer();
        String customVersion = SystemProperties.get("ro.custom.build.version");
        if (SystemProperties.get("ro.project", "trunk").startsWith("wik_")) {
            customVersion = SystemProperties.get("ro.internal.build.version");
        }
        SOFTWARE_NO.append(customVersion);

        smsContent.append("TN:IMEI1," + mStrIMEI).append("," + SAP_NO).append("," + PRODUCT_NO).
                append("," + SOFTWARE_NO).append("," + SN_NO);
        Log.d(TAG, "SaleTrackerService---->setSendContent() SendString=" + smsContent.toString());
        return smsContent.toString();
    }

    private void pickCountryConfigs() {
        Log.d(TAG, "SaleTrackerService---->pickCountryConfigs: ");
        String projectName = SystemProperties.get("ro.project", "trunk");
        Map<String, String> configMap = SaleTrackerUti.readSendParamFromXml(mContext);
        if (configMap != null) {
            mClientNo = configMap.get(CONFIG_CLIENT_NO);
            mDefaultSendType = Integer.parseInt(configMap.get(CONFIG_SEND_TYPE));
            mIsNeedNoticePop = Boolean.parseBoolean(configMap.get(CONFIG_NOTICE));
            mHosturl = configMap.get(CONFIG_HOST_URL);
            mStartTimeFromXML = Integer.parseInt(configMap.get(CONFIG_START_TIME));
            mSpaceTimeFromXML = Integer.parseInt(configMap.get(CONFIG_SPACE_TIME));
            mDefaultSendTypeTmp = mDefaultSendType;
            Log.d(TAG, "SaleTrackerService----> pickCountryConfigs: projectName = " + projectName + "\n   mClientNo =" + mClientNo + "\n   mDefaultSendType =" + mDefaultSendType + "\n   mIsNeedNoticePop =" + mIsNeedNoticePop + "\n   mHosturl =" + mHosturl + "\n   mStartTimeFromXML =" + mStartTimeFromXML + "\n   mSpaceTimeFromXML =" + mSpaceTimeFromXML + "\n   mStrPhoneNo =" + mStrPhoneNo);
            return;
        }
        Log.d(TAG, "SaleTrackerService----> pickCountryConfigs: config doesn't exist");
    }

    public void refreshPanelStatus() {
        Log.d(TAG, "SaleTrackerService---->refreshPanelStatus: ");
        mContext.sendBroadcast(new Intent(Contant.ACTION_REFRESH_PANEL));
    }

    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(1);
        NetworkInfo mobile = connMgr.getNetworkInfo(0);
        Log.d(TAG, "SaleTrackerService----> checkNetworkConnection():  wifi.getState()=" +
                wifi.getState() + " wifi.getTypeName=" + wifi.getTypeName() + " mobile.getState()=" +
                mobile.getState() + " mobile.getTypeName=" + mobile.getTypeName());
        if (mobile.getState() == State.CONNECTED || wifi.getState() == State.CONNECTED) {
            return true;
        }
        return false;
    }
}
