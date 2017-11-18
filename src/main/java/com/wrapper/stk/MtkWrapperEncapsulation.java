package com.wrapper.stk;

import android.content.Context;
import android.provider.Settings.Global;
import android.telephony.SubscriptionManager;

import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.CatCmdMessage;
import com.android.internal.telephony.cat.CatResponseMessage;
import com.android.internal.telephony.cat.CatService;
import com.android.internal.telephony.cat.Menu;
import com.mediatek.common.MPlugin;
import com.mediatek.common.telephony.cat.ICatServiceExt;
import com.mediatek.internal.telephony.RadioManager;
import com.mediatek.internal.telephony.cdma.CdmaFeatureOptionUtils;
import com.mediatek.internal.telephony.ltedc.LteDcPhoneProxy;
import com.mediatek.internal.telephony.ltedc.svlte.SvlteRatController;
import com.mediatek.internal.telephony.ltedc.svlte.SvlteRatController.RoamingMode;
import com.mediatek.telephony.TelephonyManagerEx;

public class MtkWrapperEncapsulation extends AbsWrapper {

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType = null;

        static {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.wrapper.stk.MtkWrapperEncapsulation.1.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
            /*
            r0 = com.android.internal.telephony.cat.AppInterface.CommandType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType = r0;
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01f1 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_DTMF;	 Catch:{ NoSuchFieldError -> 0x01f1 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x01f1 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01f1 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01ee }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_SMS;	 Catch:{ NoSuchFieldError -> 0x01ee }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x01ee }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01ee }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01eb }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_SS;	 Catch:{ NoSuchFieldError -> 0x01eb }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x01eb }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01eb }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01e8 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_USSD;	 Catch:{ NoSuchFieldError -> 0x01e8 }
            r2 = 4;	 Catch:{ NoSuchFieldError -> 0x01e8 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01e8 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01e5 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_IDLE_MODE_TEXT;	 Catch:{ NoSuchFieldError -> 0x01e5 }
            r2 = 5;	 Catch:{ NoSuchFieldError -> 0x01e5 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01e5 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01e2 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_MENU;	 Catch:{ NoSuchFieldError -> 0x01e2 }
            r2 = 6;	 Catch:{ NoSuchFieldError -> 0x01e2 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01e2 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01df }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.CLOSE_CHANNEL;	 Catch:{ NoSuchFieldError -> 0x01df }
            r2 = 7;	 Catch:{ NoSuchFieldError -> 0x01df }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01df }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01dc }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.RECEIVE_DATA;	 Catch:{ NoSuchFieldError -> 0x01dc }
            r2 = 8;	 Catch:{ NoSuchFieldError -> 0x01dc }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01dc }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01d9 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_DATA;	 Catch:{ NoSuchFieldError -> 0x01d9 }
            r2 = 9;	 Catch:{ NoSuchFieldError -> 0x01d9 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01d9 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01d6 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_EVENT_LIST;	 Catch:{ NoSuchFieldError -> 0x01d6 }
            r2 = 10;	 Catch:{ NoSuchFieldError -> 0x01d6 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01d6 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.DISPLAY_TEXT;	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r2 = 11;	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01d0 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_INKEY;	 Catch:{ NoSuchFieldError -> 0x01d0 }
            r2 = 12;	 Catch:{ NoSuchFieldError -> 0x01d0 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01d0 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01cd }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_INPUT;	 Catch:{ NoSuchFieldError -> 0x01cd }
            r2 = 13;	 Catch:{ NoSuchFieldError -> 0x01cd }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01cd }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01ca }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.LAUNCH_BROWSER;	 Catch:{ NoSuchFieldError -> 0x01ca }
            r2 = 14;	 Catch:{ NoSuchFieldError -> 0x01ca }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01ca }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01c7 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.PLAY_TONE;	 Catch:{ NoSuchFieldError -> 0x01c7 }
            r2 = 15;	 Catch:{ NoSuchFieldError -> 0x01c7 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01c7 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01c4 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SELECT_ITEM;	 Catch:{ NoSuchFieldError -> 0x01c4 }
            r2 = 16;	 Catch:{ NoSuchFieldError -> 0x01c4 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01c4 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01c1 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_CALL;	 Catch:{ NoSuchFieldError -> 0x01c1 }
            r2 = 17;	 Catch:{ NoSuchFieldError -> 0x01c1 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01c1 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01be }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.PROVIDE_LOCAL_INFORMATION;	 Catch:{ NoSuchFieldError -> 0x01be }
            r2 = 18;	 Catch:{ NoSuchFieldError -> 0x01be }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01be }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01bb }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.OPEN_CHANNEL;	 Catch:{ NoSuchFieldError -> 0x01bb }
            r2 = 19;	 Catch:{ NoSuchFieldError -> 0x01bb }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01bb }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01b8 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_CHANNEL_STATUS;	 Catch:{ NoSuchFieldError -> 0x01b8 }
            r2 = 20;	 Catch:{ NoSuchFieldError -> 0x01b8 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01b8 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.MORE_TIME;	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r2 = 21;	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01b2 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.POLL_INTERVAL;	 Catch:{ NoSuchFieldError -> 0x01b2 }
            r2 = 22;	 Catch:{ NoSuchFieldError -> 0x01b2 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01b2 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01af }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.POLLING_OFF;	 Catch:{ NoSuchFieldError -> 0x01af }
            r2 = 23;	 Catch:{ NoSuchFieldError -> 0x01af }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01af }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01ac }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.TIMER_MANAGEMENT;	 Catch:{ NoSuchFieldError -> 0x01ac }
            r2 = 24;	 Catch:{ NoSuchFieldError -> 0x01ac }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01ac }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01a9 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.PERFORM_CARD_APDU;	 Catch:{ NoSuchFieldError -> 0x01a9 }
            r2 = 25;	 Catch:{ NoSuchFieldError -> 0x01a9 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01a9 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01a6 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.POWER_ON_CARD;	 Catch:{ NoSuchFieldError -> 0x01a6 }
            r2 = 26;	 Catch:{ NoSuchFieldError -> 0x01a6 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01a6 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01a3 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.POWER_OFF_CARD;	 Catch:{ NoSuchFieldError -> 0x01a3 }
            r2 = 27;	 Catch:{ NoSuchFieldError -> 0x01a3 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01a3 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x01a0 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_READER_STATUS;	 Catch:{ NoSuchFieldError -> 0x01a0 }
            r2 = 28;	 Catch:{ NoSuchFieldError -> 0x01a0 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x01a0 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x019d }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.RUN_AT_COMMAND;	 Catch:{ NoSuchFieldError -> 0x019d }
            r2 = 29;	 Catch:{ NoSuchFieldError -> 0x019d }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x019d }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x019b }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.LANGUAGE_NOTIFICATION;	 Catch:{ NoSuchFieldError -> 0x019b }
            r2 = 30;	 Catch:{ NoSuchFieldError -> 0x019b }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x019b }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0199 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SERVICE_SEARCH;	 Catch:{ NoSuchFieldError -> 0x0199 }
            r2 = 31;	 Catch:{ NoSuchFieldError -> 0x0199 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0199 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0197 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_SERVICE_INFORMATION;	 Catch:{ NoSuchFieldError -> 0x0197 }
            r2 = 32;	 Catch:{ NoSuchFieldError -> 0x0197 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0197 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0195 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.DECLARE_SERVICE;	 Catch:{ NoSuchFieldError -> 0x0195 }
            r2 = 33;	 Catch:{ NoSuchFieldError -> 0x0195 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0195 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0193 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_FRAME;	 Catch:{ NoSuchFieldError -> 0x0193 }
            r2 = 34;	 Catch:{ NoSuchFieldError -> 0x0193 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0193 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0191 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_FRAME_STATUS;	 Catch:{ NoSuchFieldError -> 0x0191 }
            r2 = 35;	 Catch:{ NoSuchFieldError -> 0x0191 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0191 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x018f }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.RETRIEVE_MULTIMEDIA_MESSAGE;	 Catch:{ NoSuchFieldError -> 0x018f }
            r2 = 36;	 Catch:{ NoSuchFieldError -> 0x018f }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x018f }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x018d }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SUBMIT_MULTIMEDIA_MESSAGE;	 Catch:{ NoSuchFieldError -> 0x018d }
            r2 = 37;	 Catch:{ NoSuchFieldError -> 0x018d }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x018d }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x018b }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.DISPLAY_MULTIMEDIA_MESSAGE;	 Catch:{ NoSuchFieldError -> 0x018b }
            r2 = 38;	 Catch:{ NoSuchFieldError -> 0x018b }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x018b }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0189 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.CALLCTRL_RSP_MSG;	 Catch:{ NoSuchFieldError -> 0x0189 }
            r2 = 39;	 Catch:{ NoSuchFieldError -> 0x0189 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0189 }
        L_0x0189:
            r0 = move-exception;
            goto L_0x0188;
        L_0x018b:
            r0 = move-exception;
            goto L_0x017e;
        L_0x018d:
            r0 = move-exception;
            goto L_0x0174;
        L_0x018f:
            r0 = move-exception;
            goto L_0x016a;
        L_0x0191:
            r0 = move-exception;
            goto L_0x0160;
        L_0x0193:
            r0 = move-exception;
            goto L_0x0156;
        L_0x0195:
            r0 = move-exception;
            goto L_0x014c;
        L_0x0197:
            r0 = move-exception;
            goto L_0x0142;
        L_0x0199:
            r0 = move-exception;
            goto L_0x0138;
        L_0x019b:
            r0 = move-exception;
            goto L_0x012e;
        L_0x019d:
            r0 = move-exception;
            goto L_0x0124;
        L_0x01a0:
            r0 = move-exception;
            goto L_0x011a;
        L_0x01a3:
            r0 = move-exception;
            goto L_0x0110;
        L_0x01a6:
            r0 = move-exception;
            goto L_0x0106;
        L_0x01a9:
            r0 = move-exception;
            goto L_0x00fc;
        L_0x01ac:
            r0 = move-exception;
            goto L_0x00f2;
        L_0x01af:
            r0 = move-exception;
            goto L_0x00e8;
        L_0x01b2:
            r0 = move-exception;
            goto L_0x00de;
        L_0x01b5:
            r0 = move-exception;
            goto L_0x00d4;
        L_0x01b8:
            r0 = move-exception;
            goto L_0x00ca;
        L_0x01bb:
            r0 = move-exception;
            goto L_0x00c0;
        L_0x01be:
            r0 = move-exception;
            goto L_0x00b6;
        L_0x01c1:
            r0 = move-exception;
            goto L_0x00ac;
        L_0x01c4:
            r0 = move-exception;
            goto L_0x00a2;
        L_0x01c7:
            r0 = move-exception;
            goto L_0x0098;
        L_0x01ca:
            r0 = move-exception;
            goto L_0x008e;
        L_0x01cd:
            r0 = move-exception;
            goto L_0x0084;
        L_0x01d0:
            r0 = move-exception;
            goto L_0x007a;
        L_0x01d3:
            r0 = move-exception;
            goto L_0x0070;
        L_0x01d6:
            r0 = move-exception;
            goto L_0x0066;
        L_0x01d9:
            r0 = move-exception;
            goto L_0x005c;
        L_0x01dc:
            r0 = move-exception;
            goto L_0x0052;
        L_0x01df:
            r0 = move-exception;
            goto L_0x0048;
        L_0x01e2:
            r0 = move-exception;
            goto L_0x003f;
        L_0x01e5:
            r0 = move-exception;
            goto L_0x0036;
        L_0x01e8:
            r0 = move-exception;
            goto L_0x002d;
        L_0x01eb:
            r0 = move-exception;
            goto L_0x0024;
        L_0x01ee:
            r0 = move-exception;
            goto L_0x001b;
        L_0x01f1:
            r0 = move-exception;
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.wrapper.stk.MtkWrapperEncapsulation.1.<clinit>():void");
        }
    }

    public boolean isCmdInteractive(CatCmdMessage cmd) {
        switch (AnonymousClass1.$SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType[cmd.getCmdType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return false;
            default:
                return true;
        }
    }

    public int translateCmd(CatCmdMessage cmd) {
        switch (AnonymousClass1.$SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType[cmd.getCmdType().ordinal()]) {
            case 1:
                return 20;
            case 2:
                return 19;
            case 3:
                return 17;
            case 4:
                return 18;
            case 5:
                return 40;
            case 6:
                return 37;
            case 7:
                return 65;
            case 8:
                return 66;
            case 9:
                return 67;
            case 10:
                return 5;
            case 11:
                return 33;
            case 12:
                return 34;
            case 13:
                return 35;
            case 14:
                return 21;
            case 15:
                return 32;
            case 16:
                return 36;
            case 17:
                return 16;
            case 18:
                return 38;
            case 19:
                return 64;
            case 20:
                return 68;
            case 21:
                return 2;
            case 22:
                return 3;
            case 23:
                return 4;
            case 24:
                return 39;
            case 25:
                return 48;
            case 26:
                return 49;
            case 27:
                return 50;
            case 28:
                return 51;
            case 29:
                return 52;
            case 30:
                return 53;
            case 31:
                return 69;
            case 32:
                return 70;
            case 33:
                return 71;
            case 34:
                return 80;
            case 35:
                return 81;
            case 36:
                return 96;
            case 37:
                return 97;
            case 38:
                return 98;
            case 39:
                return 255;
            default:
                return 0;
        }
    }

    public boolean isRefreshResetOrInit(CatCmdMessage cmd) {
        return false;
    }

    public boolean hasIconLoadFailed(CatCmdMessage cmd) {
        return false;
    }

    public boolean isNormalRoamingMode(int slotId) {
        return RoamingMode.ROAMING_MODE_NORMAL_ROAMING == getRoamingState(slotId);
    }

    public String getIccCardType(Phone phone) {
        return phone.getIccCard().getIccCardType();
    }

    public String getCdmaRatModeKey(int subId) {
        return TelephonyManagerEx.getDefault().getCdmaRatModeKey(subId);
    }

    public boolean isCdmaLteDcSupport() {
        return CdmaFeatureOptionUtils.isCdmaLteDcSupport();
    }

    public boolean isSvlteRatMode4gDataOnly(Context context, int slotId) {
        if (2 == Global.getInt(context.getContentResolver(), TelephonyManagerEx.getDefault().getCdmaRatModeKey(SubscriptionManager.getSubId(slotId)[0]), 0)) {
            return true;
        }
        return false;
    }

    public boolean getSaveNewSetUpMenuFlag(int sim_id) {
        return CatService.getSaveNewSetUpMenuFlag(sim_id);
    }

    public boolean unInstallIfNoSim(Context context) {
        try {
            return ((ICatServiceExt) MPlugin.createInstance(ICatServiceExt.class.getName(), context)).unInstallIfNoSim();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isFlightModePowerOffModemEnabled() {
        return RadioManager.isFlightModePowerOffModemEnabled();
    }

    public void onDBHandler(AppInterface ai, int sim_id) {
        ai.onDBHandler(sim_id);
    }

    public void setAllCallDisConn(AppInterface ai, boolean isDisConn) {
        ai.setAllCallDisConn(isDisConn);
    }

    public void setAdditionalInfo(CatResponseMessage crm, byte[] additionalInfo) {
        crm.setAdditionalInfo(additionalInfo);
    }

    public void onEventDownload(AppInterface ai, CatResponseMessage resMsg) {
        ai.onEventDownload(resMsg);
    }

    public void setOneShot(CatResponseMessage resMsg, boolean shot) {
        resMsg.setOneShot(shot);
    }

    public void setDestinationId(CatResponseMessage resMsg, int dId) {
        resMsg.setDestinationId(dId);
    }

    public void setSourceId(CatResponseMessage resMsg, int sId) {
        resMsg.setSourceId(sId);
    }

    public CatResponseMessage createCatResponseMessage(int event) {
        return new CatResponseMessage(event);
    }

    public int getCmdQualifier(CatCmdMessage cmd) {
        return cmd.getCmdQualifier();
    }

    public void hangupAll(Phone phone) throws CallStateException {
        phone.hangupAll();
    }

    public void updateMenuTitleFromEf(Context context, Menu menu, int slotId) {
        ICatServiceExt catServiceExt = null;
        try {
            catServiceExt = (ICatServiceExt) MPlugin.createInstance(ICatServiceExt.class.getName(), context);
        } catch (Exception e) {
        }
        if (catServiceExt != null && menu != null) {
            catServiceExt.init(slotId);
            catServiceExt.updateMenuTitleFromEf(menu.title);
        }
    }

    public int getSetUpMenuFlag(CatCmdMessage cmdMsg) {
        return cmdMsg.getMenu().getSetUpMenuFlag();
    }

    public void onLaunchCachedSetupMenu(AppInterface ai) {
        ai.onLaunchCachedSetupMenu();
    }

    public boolean isCallDisConnReceived(AppInterface ai) {
        return ai.isCallDisConnReceived();
    }

    private RoamingMode getRoamingState(int slotId) {
        LteDcPhoneProxy lteDcPhoneProxy = (LteDcPhoneProxy) PhoneFactory.getPhone(slotId);
        RoamingMode rm = RoamingMode.ROAMING_MODE_UNKNOWN;
        if (lteDcPhoneProxy == null) {
            return rm;
        }
        SvlteRatController lteRatController = lteDcPhoneProxy.getSvlteRatController();
        if (lteRatController != null) {
            return lteRatController.getRoamingMode();
        }
        return rm;
    }
}
