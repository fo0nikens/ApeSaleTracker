package com.ape.saletracker;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.internal.telephony.PhoneConstants;

public class WIKOSTSScreen extends Activity {
    public static final int FLAG_HOMEKEY_DISPATCHED = Integer.MIN_VALUE;
    public static final int STATUS_BAR_DISABLE_BACK = 4194304;
    public static final int STATUS_BAR_DISABLE_HOME = 2097152;
    public static final int STATUS_BAR_DISABLE_RECENT = 16777216;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasNavBar = true;

        if ("1".equals(Boolean.TRUE)) {
            hasNavBar = false;
        } else if (PhoneConstants.CFU_QUERY_TYPE_DEF_VALUE.equals(Boolean.TRUE)) {
            hasNavBar = true;
        }
        Log.d("guchunhua", "onCreate hasNavBar = " + hasNavBar);

        getWindow().getDecorView().setSystemUiVisibility(23068672);
        if ("QMobile".equalsIgnoreCase(SystemProperties.get("ro.product.brand", "trunk"))) {
            setContentView(R.layout.activity_qmobilescreen);
        } else {
            setContentView(R.layout.activity_wikostsscreen);
        }

        findViewById(R.id.button_ok).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                WIKOSTSScreen.this.finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("guchunhua", "onKeyDown keyCode = " + keyCode);
        Log.d("guchunhua", "onKeyDown event = " + event);

        switch (keyCode) {
            case 4:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
