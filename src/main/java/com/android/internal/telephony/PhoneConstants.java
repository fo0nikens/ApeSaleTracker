package com.android.internal.telephony;

public class PhoneConstants {
    public static final String ACTION_SUBSCRIPTION_PHONE_STATE_CHANGED = "android.intent.action.SUBSCRIPTION_PHONE_STATE";
    public static final int APN_ALREADY_ACTIVE = 0;
    public static final int APN_ALREADY_INACTIVE = 4;
    public static final int APN_REQUEST_FAILED = 3;
    public static final int APN_REQUEST_STARTED = 1;
    public static final String APN_TYPE_ALL = "*";
    public static final String APN_TYPE_CBS = "cbs";
    public static final String APN_TYPE_CMMAIL = "cmmail";
    public static final String APN_TYPE_DEFAULT = "default";
    public static final String APN_TYPE_DM = "dm";
    public static final String APN_TYPE_DUN = "dun";
    public static final String APN_TYPE_EMERGENCY = "emergency";
    public static final String APN_TYPE_FOTA = "fota";
    public static final String APN_TYPE_HIPRI = "hipri";
    public static final String APN_TYPE_IA = "ia";
    public static final String APN_TYPE_IMS = "ims";
    public static final String APN_TYPE_MMS = "mms";
    public static final String APN_TYPE_NET = "net";
    public static final int APN_TYPE_NOT_AVAILABLE = 2;
    public static final String APN_TYPE_RCS = "rcs";
    public static final String APN_TYPE_RCSE = "rcse";
    public static final String APN_TYPE_SUPL = "supl";
    public static final String APN_TYPE_TETHERING = "tethering";
    public static final String APN_TYPE_WAP = "wap";
    public static final String APN_TYPE_XCAP = "xcap";
    public static final int APPTYPE_CSIM = 4;
    public static final int APPTYPE_ISIM = 5;
    public static final int APPTYPE_RUIM = 3;
    public static final int APPTYPE_SIM = 1;
    public static final int APPTYPE_UNKNOWN = 0;
    public static final int APPTYPE_USIM = 2;
    public static final int AUDIO_OUTPUT_DEFAULT = 0;
    public static final int AUDIO_OUTPUT_DISABLE_SPEAKER = 1;
    public static final int AUDIO_OUTPUT_ENABLE_SPEAKER = 0;
    public static final int CAPABILITY_3G = 1;
    public static final String CFU_QUERY_TYPE_DEF_VALUE = "0";
    public static final String CFU_QUERY_TYPE_PROP = "persist.radio.cfu.querytype";
    public static final String DATA_APN_KEY = "apn";
    public static final String DATA_APN_TYPE_KEY = "apnType";
    public static final String DATA_FAILURE_CAUSE_KEY = "failCause";
    public static final String DATA_IFACE_NAME_KEY = "iface";
    public static final String DATA_LINK_PROPERTIES_KEY = "linkProperties";
    public static final String DATA_NETWORK_CAPABILITIES_KEY = "networkCapabilities";
    public static final String DATA_NETWORK_ROAMING_KEY = "networkRoaming";
    public static final String DATA_NETWORK_TYPE_KEY = "networkType";
    public static final int DEFAULT_CARD_INDEX = 0;
    public static final int EVENT_SUBSCRIPTION_ACTIVATED = 500;
    public static final int EVENT_SUBSCRIPTION_DEACTIVATED = 501;
    public static final String FAILURE_REASON_KEY = "reason";
    public static final int IMS_STATE_DISABLED = 0;
    public static final int IMS_STATE_DISABLING = 3;
    public static final int IMS_STATE_ENABLE = 1;
    public static final int IMS_STATE_ENABLING = 2;
    public static final String LTE_ACCESS_STRATUM_STATE_CONNECTED = "connected";
    public static final String LTE_ACCESS_STRATUM_STATE_IDLE = "idle";
    public static final String LTE_ACCESS_STRATUM_STATE_KEY = "lteAccessStratumState";
    public static final String LTE_ACCESS_STRATUM_STATE_UNKNOWN = "unknown";
    public static final int LTE_ON_CDMA_FALSE = 0;
    public static final int LTE_ON_CDMA_TRUE = 1;
    public static final int LTE_ON_CDMA_UNKNOWN = -1;
    public static final int MAX_PHONE_COUNT_DUAL_SIM = 2;
    public static final int MAX_PHONE_COUNT_SINGLE_SIM = 1;
    public static final int MAX_PHONE_COUNT_TRI_SIM = 3;
    public static final int MISC_FEATURE_CONFIG_MASK_AUTO_SWITCH_RAT = 1;
    public static final String MVNO_TYPE_GID = "gid";
    public static final String MVNO_TYPE_IMSI = "imsi";
    public static final String MVNO_TYPE_NONE = "";
    public static final String MVNO_TYPE_PNN = "pnn";
    public static final String MVNO_TYPE_SPN = "spn";
    public static final String NETWORK_UNAVAILABLE_KEY = "networkUnvailable";
    public static final String OLD_PHONE_KEY = "oldphone";
    public static final String OLD_SLOT_KEY = "oldslot";
    public static final String OLD_SUBSCRIPTION_KEY = "oldsubscription";
    public static final String PHONE_IN_ECM_STATE = "phoneinECMState";
    public static final String PHONE_KEY = "phone";
    public static final String PHONE_NAME_KEY = "phoneName";
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_IMS = 5;
    public static final String PHONE_TYPE_KEY = "phoneType";
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    public static final int PHONE_TYPE_THIRD_PARTY = 4;
    public static final int PIN_GENERAL_FAILURE = 2;
    public static final int PIN_PASSWORD_INCORRECT = 1;
    public static final int PIN_RESULT_SUCCESS = 0;
    public static final int PRESENTATION_ALLOWED = 1;
    public static final int PRESENTATION_PAYPHONE = 4;
    public static final int PRESENTATION_RESTRICTED = 2;
    public static final int PRESENTATION_UNKNOWN = 3;
    public static final String PROPERTY_CAPABILITY_SWITCH = "persist.radio.simswitch";
    public static final String PS_NETWORK_TYPE_KEY = "psNetworkType";
    public static final int RAT_MODE_C2K = 7;
    public static final int RAT_MODE_GSM = 0;
    public static final String REASON_LINK_PROPERTIES_CHANGED = "linkPropertiesChanged";
    public static final int RIL_CARD_MAX_APPS = 8;
    public static final String SHARED_DEFAULT_APN_KEY = "sharedDefaultApn";
    public static final int SIM_ID_1 = 0;
    public static final int SIM_ID_2 = 1;
    public static final int SIM_ID_3 = 2;
    public static final int SIM_ID_4 = 3;
    public static final String SLOT_KEY = "slot";
    public static final String STATE_CHANGE_REASON_KEY = "reason";
    public static final int STATE_CONNECTED = 1;
    public static final int STATE_DISCONNECTED = 0;
    public static final String STATE_KEY = "state";
    public static final int SUB1 = 0;
    public static final int SUB2 = 1;
    public static final int SUB3 = 2;
    public static final String SUBSCRIPTION_KEY = "subscription";
    public static final String SUB_SETTING = "subSettings";
    public static final int UNSET_MTU = 0;
    public static final int UT_CSFB_ONCE = 1;
    public static final int UT_CSFB_PS_PREFERRED = 0;
    public static final int UT_CSFB_UNTIL_NEXT_BOOT = 2;

    public enum CardUnavailableReason {
        REASON_CARD_REMOVED,
        REASON_RADIO_UNAVAILABLE,
        REASON_SIM_REFRESH_RESET
    }

    public enum DataState {
        CONNECTED,
        CONNECTING,
        DISCONNECTED,
        SUSPENDED
    }

    public enum State {
        IDLE,
        RINGING,
        OFFHOOK
    }
}
