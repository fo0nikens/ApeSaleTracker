package com.ape.saletracker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.telephony.PhoneConstants;
import com.wrapper.stk.HideMethod.TelephonyManager;

import java.util.Map;

public class SaleTrackerActivity extends Activity {
    private static final String CLASS_NAME = "SaleTrackerActivity---->";
    private static final String CONFIG_SEND_TYPE = "send_type";
    private static final String CONFIG_SPACE_TIME = "space_time";
    private static final String CONFIG_START_TIME = "start_time";
    private static final String TAG = "SaleTracker";
    public static Editor ed;
    private static Context mContext;
    private static String mStrSendResult = "unknown";
    private static final String[] mStrings = new String[]{"sms", PhoneConstants.APN_TYPE_NET, "net and sms"};
    private static String mVersion;
    public static SharedPreferences pre;
    private static SaleTrackerConfigSP stciSP = new SaleTrackerConfigSP();
    private int DEFAULT_SEND_TYPE = 1;
    private int DEFAULT_SPACE_TIME = Contant.SPACE_TIME;
    private int DEFAULT_START_TIME = Contant.START_TIME;
    private EditText mDayTime;
    private CheckBox mNotify;
    private EditText mOpenTime;
    private EditText mSpaceTime;
    private Spinner mSpinner;
    private CheckBox mSwitchWhole;
    private TextView mTips;
    public BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(SaleTrackerActivity.TAG, "SaleTrackerActivity---->refreshReceiver onReceive: ");
            SaleTrackerActivity.this.updateUI();
        }
    };
    TextView sendTypeTextView;
    TextView setResutTextView;
    TextView showOpenFileTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SaleTrackerActivity---->onCreate  SaleTrackerActivity");
        setContentView(R.layout.main);
        mContext = getApplicationContext();
        stciSP.init(mContext);
        try {
            mVersion = "Version: " + mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            Log.d(TAG, "SaleTrackerActivity----> onCreate: mVersion = " + mVersion);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        pickTimeConfigs();
        init();
    }

    private void init() {
        boolean z;
        pre = getSharedPreferences(Contant.STSDATA_CONFIG, 0);
        ed = pre.edit();
        this.mTips = (TextView) findViewById(R.id.tvTips);
        this.showOpenFileTextView = (TextView) findViewById(R.id.tvShowOpenFile);
        this.setResutTextView = (TextView) findViewById(R.id.tvShowSendResult);
        this.sendTypeTextView = (TextView) findViewById(R.id.tvShowSendType);
        this.mOpenTime = (EditText) findViewById(R.id.editopentime);
        this.mSpaceTime = (EditText) findViewById(R.id.spacetime);
        this.mDayTime = (EditText) findViewById(R.id.daytime);
        this.mNotify = (CheckBox) findViewById(R.id.notify);
        this.mNotify.setChecked(pre.getBoolean(Contant.KEY_NOTIFY, getResources().getBoolean(R.bool.dialog_notify)));
        this.mNotify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SaleTrackerActivity.ed.putBoolean(Contant.KEY_NOTIFY, SaleTrackerActivity.this.mNotify.isChecked());
                SaleTrackerActivity.ed.commit();
            }
        });
        this.mSwitchWhole = (CheckBox) findViewById(R.id.switchSendType);
        if (SystemProperties.get("ro.project", "trunk").equals("oys_ru")) {
            this.mSwitchWhole.setVisibility(4);
        } else {
            this.mSwitchWhole.setVisibility(0);
        }
        this.mSwitchWhole.setChecked(pre.getBoolean(Contant.KEY_SWITCH_SENDTYPE, false));
        this.mSwitchWhole.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.d(SaleTrackerActivity.TAG, "SaleTrackerActivity---->mSwitchWhole onClick: isChecked = " + SaleTrackerActivity.this.mSwitchWhole.isChecked());
                SaleTrackerActivity.ed.putBoolean(Contant.KEY_SWITCH_SENDTYPE, SaleTrackerActivity.this.mSwitchWhole.isChecked());
                SaleTrackerActivity.ed.commit();
                if (SaleTrackerActivity.this.mSwitchWhole.isChecked()) {
                    SaleTrackerActivity.this.mSpinner.setEnabled(true);
                } else {
                    SaleTrackerActivity.this.mSpinner.setEnabled(false);
                }
            }
        });
        this.mSpinner = (Spinner) findViewById(R.id.spinnerSendType);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, 17367048, mStrings);
        adapter.setDropDownViewResource(17367049);
        this.mSpinner.setAdapter(adapter);
        this.mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                SaleTrackerActivity.ed.putInt(Contant.KEY_SELECT_SEND_TYPE, position);
                SaleTrackerActivity.ed.commit();
                SaleTrackerActivity.this.updateUI();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                SaleTrackerActivity.ed.putInt(Contant.KEY_SELECT_SEND_TYPE, -1);
                SaleTrackerActivity.ed.commit();
            }
        });
        Spinner spinner = this.mSpinner;
        if (this.mSwitchWhole.isChecked()) {
            z = true;
        } else {
            z = false;
        }
        spinner.setEnabled(z);
        this.mSpinner.setSelection(pre.getInt(Contant.KEY_SELECT_SEND_TYPE, this.DEFAULT_SEND_TYPE));
        ((TextView) findViewById(R.id.tvShowVersion)).setText(mVersion);
        Log.d(TAG, "onCreate: Button findViewById");
        ((Button) findViewById(R.id.btnSave)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (PhoneConstants.CFU_QUERY_TYPE_DEF_VALUE.equals(SaleTrackerActivity.this.mOpenTime.getText().toString()) || PhoneConstants.CFU_QUERY_TYPE_DEF_VALUE.equals(SaleTrackerActivity.this.mSpaceTime.getText().toString()) || "".equals(SaleTrackerActivity.this.mOpenTime.getText().toString()) || "".equals(SaleTrackerActivity.this.mSpaceTime.getText().toString())) {
                    SaleTrackerActivity.this.showToast(SaleTrackerActivity.this.getResources().getString(R.string.sts_invalid_value));
                    return;
                }
                SaleTrackerActivity.ed.putInt(Contant.KEY_OPEN_TIME, Integer.parseInt(SaleTrackerActivity.this.mOpenTime.getText().toString(), 10));
                SaleTrackerActivity.ed.putInt(Contant.KEY_SPACE_TIME, Integer.parseInt(SaleTrackerActivity.this.mSpaceTime.getText().toString(), 10));
                SaleTrackerActivity.ed.commit();
                SaleTrackerActivity.this.showToast("Save successful");
            }
        });
        ((Button) findViewById(R.id.btnclear)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.d(SaleTrackerActivity.TAG, "SaleTrackerActivity---->setOnClickListener");
                SaleTrackerActivity.stciSP.writeConfigForTmeWapAddr(false);
                SaleTrackerActivity.stciSP.writeSendedResult(false);
                SaleTrackerActivity.stciSP.writeSendedNumber(0);
                SaleTrackerActivity.this.mSwitchWhole.setChecked(false);
                SaleTrackerActivity.ed.putInt(Contant.KEY_OPEN_TIME, SaleTrackerActivity.this.DEFAULT_START_TIME);
                SaleTrackerActivity.ed.putInt(Contant.KEY_SPACE_TIME, SaleTrackerActivity.this.DEFAULT_SPACE_TIME);
                SaleTrackerActivity.ed.putBoolean(Contant.KEY_SWITCH_SENDTYPE, false);
                SaleTrackerActivity.ed.putInt(Contant.KEY_SELECT_SEND_TYPE, SaleTrackerActivity.this.DEFAULT_SEND_TYPE);
                SaleTrackerActivity.ed.commit();
                SaleTrackerActivity.this.updateUI();
                SaleTrackerActivity.this.showToast("Clear successful");
            }
        });
        registerReceiver(this.refreshReceiver, new IntentFilter(Contant.ACTION_REFRESH_PANEL));
    }

    private void showToast(CharSequence msg) {
        Toast.makeText(this, msg, 0).show();
    }

    protected void onDestroy() {
        Log.d(TAG, "SaleTrackerActivity---->onDestroy");
        if (this.refreshReceiver != null) {
            unregisterReceiver(this.refreshReceiver);
        }
        super.onDestroy();
    }

    protected void onResume() {
        Log.d(TAG, "SaleTrackerActivity---->onResume");
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        boolean z;
        String strTmp;
        Log.d(TAG, "SaleTrackerActivity---->updateUI: ");
        Spinner spinner = this.mSpinner;
        if (this.mSwitchWhole.isChecked()) {
            z = true;
        } else {
            z = false;
        }
        spinner.setEnabled(z);
        this.mSpinner.setSelection(pre.getInt(Contant.KEY_SELECT_SEND_TYPE, this.DEFAULT_SEND_TYPE));
        this.mOpenTime.setText("" + pre.getInt(Contant.KEY_OPEN_TIME, this.DEFAULT_START_TIME));
        this.mSpaceTime.setText("" + pre.getInt(Contant.KEY_SPACE_TIME, this.DEFAULT_SPACE_TIME));
        int iSendTypeTmp = pre.getInt(Contant.KEY_SELECT_SEND_TYPE, this.DEFAULT_SEND_TYPE);
        if (stciSP.readSendedResult()) {
            Log.d(TAG, "SaleTrackerActivity---->updateUI: send is OK, set mSwitchWhole unchecked");
            this.mSpinner.setEnabled(false);
            this.mSwitchWhole.setChecked(false);
        }
        if (this.mSwitchWhole.isChecked()) {
            strTmp = "SendType(from the Switch control) : ";
        } else {
            strTmp = "SendType : ";
        }
        switch (iSendTypeTmp) {
            case 0:
                this.sendTypeTextView.setText(strTmp + " sms");
                break;
            case 1:
                this.sendTypeTextView.setText(strTmp + " net");
                break;
            case 2:
                this.sendTypeTextView.setText(strTmp + " net and sms");
                break;
            default:
                this.sendTypeTextView.setText(strTmp + " unknown");
                break;
        }
        boolean bSendToTme = stciSP.readConfigForTmeWapAddr().booleanValue();
        this.showOpenFileTextView.setText("IMEI1 :  " + TelephonyManager.getDefault().getDeviceId(0, mContext));
        mStrSendResult = stciSP.readSendedResult() ? "  OK " : "  No ";
        if (bSendToTme) {
            this.setResutTextView.setText("Send result1 : " + mStrSendResult + "    result2:  OK");
        } else {
            this.setResutTextView.setText("Send result1 : " + mStrSendResult + "    result2:  No");
        }
    }

    private void pickTimeConfigs() {
        Log.d(TAG, "SaleTrackerActivity---->pickTimeConfigs: ");
        Map<String, String> configMap = SaleTrackerUti.readSendParamFromXml(getApplicationContext());
        if (configMap != null) {
            this.DEFAULT_SEND_TYPE = Integer.parseInt((String) configMap.get(CONFIG_SEND_TYPE));
            this.DEFAULT_START_TIME = Integer.parseInt((String) configMap.get(CONFIG_START_TIME));
            this.DEFAULT_SPACE_TIME = Integer.parseInt((String) configMap.get(CONFIG_SPACE_TIME));
            Log.w(TAG, "SaleTrackerActivity----> pickCountryConfigs: \n   DEFAULT_SEND_TYPE =" + this.DEFAULT_SEND_TYPE + "\n   DEFAULT_START_TIME =" + this.DEFAULT_START_TIME + "\n   DEFAULT_SPACE_TIME =" + this.DEFAULT_SPACE_TIME);
            return;
        }
        Log.d(TAG, "SaleTrackerActivity----> pickTimeConfigs: config doesn't exist");
    }
}
