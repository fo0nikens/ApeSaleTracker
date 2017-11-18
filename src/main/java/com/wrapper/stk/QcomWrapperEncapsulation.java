package com.wrapper.stk;

import android.content.Context;

import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.CatCmdMessage;
import com.android.internal.telephony.cat.CatResponseMessage;
import com.android.internal.telephony.cat.Menu;

public class QcomWrapperEncapsulation extends AbsWrapper {

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType = null;

        static {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.wrapper.stk.QcomWrapperEncapsulation.1.<clinit>():void
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
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0114 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_DTMF;	 Catch:{ NoSuchFieldError -> 0x0114 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0114 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0114 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0111 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_SMS;	 Catch:{ NoSuchFieldError -> 0x0111 }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0111 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0111 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x010e }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_SS;	 Catch:{ NoSuchFieldError -> 0x010e }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x010e }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x010e }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x010b }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_USSD;	 Catch:{ NoSuchFieldError -> 0x010b }
            r2 = 4;	 Catch:{ NoSuchFieldError -> 0x010b }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x010b }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0108 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_IDLE_MODE_TEXT;	 Catch:{ NoSuchFieldError -> 0x0108 }
            r2 = 5;	 Catch:{ NoSuchFieldError -> 0x0108 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0108 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0105 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_MENU;	 Catch:{ NoSuchFieldError -> 0x0105 }
            r2 = 6;	 Catch:{ NoSuchFieldError -> 0x0105 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0105 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x0102 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.REFRESH;	 Catch:{ NoSuchFieldError -> 0x0102 }
            r2 = 7;	 Catch:{ NoSuchFieldError -> 0x0102 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0102 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00ff }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.CLOSE_CHANNEL;	 Catch:{ NoSuchFieldError -> 0x00ff }
            r2 = 8;	 Catch:{ NoSuchFieldError -> 0x00ff }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00ff }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00fc }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.RECEIVE_DATA;	 Catch:{ NoSuchFieldError -> 0x00fc }
            r2 = 9;	 Catch:{ NoSuchFieldError -> 0x00fc }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00fc }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00f9 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SEND_DATA;	 Catch:{ NoSuchFieldError -> 0x00f9 }
            r2 = 10;	 Catch:{ NoSuchFieldError -> 0x00f9 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00f9 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00f6 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_EVENT_LIST;	 Catch:{ NoSuchFieldError -> 0x00f6 }
            r2 = 11;	 Catch:{ NoSuchFieldError -> 0x00f6 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00f6 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.ACTIVATE;	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r2 = 12;	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00f1 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.DISPLAY_TEXT;	 Catch:{ NoSuchFieldError -> 0x00f1 }
            r2 = 13;	 Catch:{ NoSuchFieldError -> 0x00f1 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00f1 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00ef }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_INKEY;	 Catch:{ NoSuchFieldError -> 0x00ef }
            r2 = 14;	 Catch:{ NoSuchFieldError -> 0x00ef }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00ef }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00ed }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_INPUT;	 Catch:{ NoSuchFieldError -> 0x00ed }
            r2 = 15;	 Catch:{ NoSuchFieldError -> 0x00ed }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00ed }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00eb }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.LAUNCH_BROWSER;	 Catch:{ NoSuchFieldError -> 0x00eb }
            r2 = 16;	 Catch:{ NoSuchFieldError -> 0x00eb }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00eb }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00e9 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.PLAY_TONE;	 Catch:{ NoSuchFieldError -> 0x00e9 }
            r2 = 17;	 Catch:{ NoSuchFieldError -> 0x00e9 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00e9 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00e7 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SELECT_ITEM;	 Catch:{ NoSuchFieldError -> 0x00e7 }
            r2 = 18;	 Catch:{ NoSuchFieldError -> 0x00e7 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00e7 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00e5 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.SET_UP_CALL;	 Catch:{ NoSuchFieldError -> 0x00e5 }
            r2 = 19;	 Catch:{ NoSuchFieldError -> 0x00e5 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00e5 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00e3 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.PROVIDE_LOCAL_INFORMATION;	 Catch:{ NoSuchFieldError -> 0x00e3 }
            r2 = 20;	 Catch:{ NoSuchFieldError -> 0x00e3 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00e3 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00e1 }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.OPEN_CHANNEL;	 Catch:{ NoSuchFieldError -> 0x00e1 }
            r2 = 21;	 Catch:{ NoSuchFieldError -> 0x00e1 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00e1 }
            r0 = $SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType;	 Catch:{ NoSuchFieldError -> 0x00df }
            r1 = com.android.internal.telephony.cat.AppInterface.CommandType.GET_CHANNEL_STATUS;	 Catch:{ NoSuchFieldError -> 0x00df }
            r2 = 22;	 Catch:{ NoSuchFieldError -> 0x00df }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00df }
        L_0x00df:
            r0 = move-exception;
            goto L_0x00de;
        L_0x00e1:
            r0 = move-exception;
            goto L_0x00d4;
        L_0x00e3:
            r0 = move-exception;
            goto L_0x00ca;
        L_0x00e5:
            r0 = move-exception;
            goto L_0x00c0;
        L_0x00e7:
            r0 = move-exception;
            goto L_0x00b6;
        L_0x00e9:
            r0 = move-exception;
            goto L_0x00ac;
        L_0x00eb:
            r0 = move-exception;
            goto L_0x00a2;
        L_0x00ed:
            r0 = move-exception;
            goto L_0x0098;
        L_0x00ef:
            r0 = move-exception;
            goto L_0x008e;
        L_0x00f1:
            r0 = move-exception;
            goto L_0x0084;
        L_0x00f3:
            r0 = move-exception;
            goto L_0x007a;
        L_0x00f6:
            r0 = move-exception;
            goto L_0x0070;
        L_0x00f9:
            r0 = move-exception;
            goto L_0x0066;
        L_0x00fc:
            r0 = move-exception;
            goto L_0x005c;
        L_0x00ff:
            r0 = move-exception;
            goto L_0x0052;
        L_0x0102:
            r0 = move-exception;
            goto L_0x0048;
        L_0x0105:
            r0 = move-exception;
            goto L_0x003f;
        L_0x0108:
            r0 = move-exception;
            goto L_0x0036;
        L_0x010b:
            r0 = move-exception;
            goto L_0x002d;
        L_0x010e:
            r0 = move-exception;
            goto L_0x0024;
        L_0x0111:
            r0 = move-exception;
            goto L_0x001b;
        L_0x0114:
            r0 = move-exception;
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.wrapper.stk.QcomWrapperEncapsulation.1.<clinit>():void");
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
            case 11:
            case 12:
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
                return 1;
            case 8:
                return 65;
            case 9:
                return 66;
            case 10:
                return 67;
            case 11:
                return 5;
            case 12:
                return AbsWrapper.CMD_ACTIVATE;
            case 13:
                return 33;
            case 14:
                return 34;
            case 15:
                return 35;
            case 16:
                return 21;
            case 17:
                return 32;
            case 18:
                return 36;
            case 19:
                return 16;
            case 20:
                return 38;
            case 21:
                return 64;
            case 22:
                return 68;
            default:
                return 0;
        }
    }

    public boolean isRefreshResetOrInit(CatCmdMessage cmd) {
        return cmd.isRefreshResetOrInit();
    }

    public boolean hasIconLoadFailed(CatCmdMessage cmd) {
        return cmd.hasIconLoadFailed();
    }

    public boolean isNormalRoamingMode(int slotId) {
        return true;
    }

    public String getIccCardType(Phone phone) {
        return "";
    }

    public String getCdmaRatModeKey(int subId) {
        return "";
    }

    public boolean isCdmaLteDcSupport() {
        return true;
    }

    public boolean isSvlteRatMode4gDataOnly(Context context, int slotId) {
        return true;
    }

    public boolean getSaveNewSetUpMenuFlag(int sim_id) {
        return true;
    }

    public boolean unInstallIfNoSim(Context context) {
        return true;
    }

    public boolean isFlightModePowerOffModemEnabled() {
        return true;
    }

    public void onDBHandler(AppInterface ai, int sim_id) {
    }

    public void setAllCallDisConn(AppInterface ai, boolean isDisConn) {
    }

    public void setAdditionalInfo(CatResponseMessage crm, byte[] additionalInfo) {
    }

    public void onEventDownload(AppInterface ai, CatResponseMessage resMsg) {
    }

    public void setOneShot(CatResponseMessage resMsg, boolean shot) {
    }

    public void setDestinationId(CatResponseMessage resMsg, int dId) {
    }

    public void setSourceId(CatResponseMessage resMsg, int sId) {
    }

    public CatResponseMessage createCatResponseMessage(int event) {
        return null;
    }

    public int getCmdQualifier(CatCmdMessage cmd) {
        return 0;
    }

    public void hangupAll(Phone phone) throws CallStateException {
    }

    public void updateMenuTitleFromEf(Context context, Menu menu, int slotId) {
    }

    public int getSetUpMenuFlag(CatCmdMessage cmdMsg) {
        return 0;
    }

    public void onLaunchCachedSetupMenu(AppInterface ai) {
    }

    public boolean isCallDisConnReceived(AppInterface ai) {
        return true;
    }
}
