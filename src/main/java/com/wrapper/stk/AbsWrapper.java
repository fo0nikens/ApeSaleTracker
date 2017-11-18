package com.wrapper.stk;

import android.content.Context;

import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.CatCmdMessage;
import com.android.internal.telephony.cat.CatResponseMessage;
import com.android.internal.telephony.cat.Menu;

public abstract class AbsWrapper {
    public static final int CMD_ACTIVATE = 112;
    public static final int CMD_CALLCTRL_RSP_MSG = 255;
    public static final int CMD_CLOSE_CHANNEL = 65;
    public static final int CMD_DECLARE_SERVICE = 71;
    public static final int CMD_DISPLAY_MULTIMEDIA_MESSAGE = 98;
    public static final int CMD_DISPLAY_TEXT = 33;
    public static final int CMD_GET_CHANNEL_STATUS = 68;
    public static final int CMD_GET_FRAME_STATUS = 81;
    public static final int CMD_GET_INKEY = 34;
    public static final int CMD_GET_INPUT = 35;
    public static final int CMD_GET_READER_STATUS = 51;
    public static final int CMD_GET_SERVICE_INFORMATION = 70;
    public static final int CMD_LANGUAGE_NOTIFICATION = 53;
    public static final int CMD_LAUNCH_BROWSER = 21;
    public static final int CMD_MORE_TIME = 2;
    public static final int CMD_OPEN_CHANNEL = 64;
    public static final int CMD_PERFORM_CARD_APDU = 48;
    public static final int CMD_PLAY_TONE = 32;
    public static final int CMD_POLLING_OFF = 4;
    public static final int CMD_POLL_INTERVAL = 3;
    public static final int CMD_POWER_OFF_CARD = 50;
    public static final int CMD_POWER_ON_CARD = 49;
    public static final int CMD_PROVIDE_LOCAL_INFORMATION = 38;
    public static final int CMD_RECEIVE_DATA = 66;
    public static final int CMD_REFRESH = 1;
    public static final int CMD_RETRIEVE_MULTIMEDIA_MESSAGE = 96;
    public static final int CMD_RUN_AT_COMMAND = 52;
    public static final int CMD_SELECT_ITEM = 36;
    public static final int CMD_SEND_DATA = 67;
    public static final int CMD_SEND_DTMF = 20;
    public static final int CMD_SEND_SMS = 19;
    public static final int CMD_SEND_SS = 17;
    public static final int CMD_SEND_USSD = 18;
    public static final int CMD_SERVICE_SEARCH = 69;
    public static final int CMD_SET_FRAME = 80;
    public static final int CMD_SET_UP_CALL = 16;
    public static final int CMD_SET_UP_EVENT_LIST = 5;
    public static final int CMD_SET_UP_IDLE_MODE_TEXT = 40;
    public static final int CMD_SET_UP_MENU = 37;
    public static final int CMD_SUBMIT_MULTIMEDIA_MESSAGE = 97;
    public static final int CMD_TIMER_MANAGEMENT = 39;

    public abstract CatResponseMessage createCatResponseMessage(int i);

    public abstract String getCdmaRatModeKey(int i);

    public abstract int getCmdQualifier(CatCmdMessage catCmdMessage);

    public abstract String getIccCardType(Phone phone);

    public abstract boolean getSaveNewSetUpMenuFlag(int i);

    public abstract int getSetUpMenuFlag(CatCmdMessage catCmdMessage);

    public abstract void hangupAll(Phone phone) throws CallStateException;

    public abstract boolean hasIconLoadFailed(CatCmdMessage catCmdMessage);

    public abstract boolean isCallDisConnReceived(AppInterface appInterface);

    public abstract boolean isCdmaLteDcSupport();

    public abstract boolean isCmdInteractive(CatCmdMessage catCmdMessage);

    public abstract boolean isFlightModePowerOffModemEnabled();

    public abstract boolean isNormalRoamingMode(int i);

    public abstract boolean isRefreshResetOrInit(CatCmdMessage catCmdMessage);

    public abstract boolean isSvlteRatMode4gDataOnly(Context context, int i);

    public abstract void onDBHandler(AppInterface appInterface, int i);

    public abstract void onEventDownload(AppInterface appInterface, CatResponseMessage catResponseMessage);

    public abstract void onLaunchCachedSetupMenu(AppInterface appInterface);

    public abstract void setAdditionalInfo(CatResponseMessage catResponseMessage, byte[] bArr);

    public abstract void setAllCallDisConn(AppInterface appInterface, boolean z);

    public abstract void setDestinationId(CatResponseMessage catResponseMessage, int i);

    public abstract void setOneShot(CatResponseMessage catResponseMessage, boolean z);

    public abstract void setSourceId(CatResponseMessage catResponseMessage, int i);

    public abstract int translateCmd(CatCmdMessage catCmdMessage);

    public abstract boolean unInstallIfNoSim(Context context);

    public abstract void updateMenuTitleFromEf(Context context, Menu menu, int i);
}
