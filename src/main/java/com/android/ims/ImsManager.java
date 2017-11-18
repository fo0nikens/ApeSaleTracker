package com.android.ims;

import android.app.PendingIntent;
import android.app.QueuedWork;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.util.Log;

import com.android.ims.ImsCall.Listener;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsRegistrationListener.Stub;
import com.android.ims.internal.IImsService;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.ImsCallSession;
import com.android.internal.telephony.PhoneConstants;

import java.util.HashMap;

import org.gsma.joyn.JoynServiceListener;
import org.gsma.joyn.capability.CapabilityService;
import org.gsma.joyn.chat.ChatService;
import org.gsma.joyn.contacts.ContactsService;
import org.gsma.joyn.ft.FileTransferService;
import org.gsma.joyn.gsh.GeolocSharingService;
import org.gsma.joyn.ish.ImageSharingService;
import org.gsma.joyn.vsh.VideoSharingService;

public class ImsManager {
    public static final String ACTION_IMS_INCOMING_CALL = "com.android.ims.IMS_INCOMING_CALL";
    public static final String ACTION_IMS_INCOMING_CALL_INDICATION = "com.android.ims.IMS_INCOMING_CALL_INDICATION";
    public static final String ACTION_IMS_REGISTRATION_ERROR = "com.android.ims.REGISTRATION_ERROR";
    public static final String ACTION_IMS_SERVICE_DOWN = "com.android.ims.IMS_SERVICE_DOWN";
    public static final String ACTION_IMS_SERVICE_UP = "com.android.ims.IMS_SERVICE_UP";
    public static final String ACTION_IMS_STATE_CHANGED = "com.android.ims.IMS_STATE_CHANGED";
    private static final boolean DBG = true;
    public static final String EXTRA_CALL_ID = "android:imsCallID";
    public static final String EXTRA_DIAL_STRING = "android:imsDialString";
    public static final String EXTRA_IMS_DISABLE_CAP_KEY = "android:disablecap";
    public static final String EXTRA_IMS_ENABLE_CAP_KEY = "android:enablecap";
    public static final String EXTRA_IMS_REG_ERROR_KEY = "android:regError";
    public static final String EXTRA_IMS_REG_STATE_KEY = "android:regState";
    public static final String EXTRA_PHONE_ID = "android:phone_id";
    public static final String EXTRA_SEQ_NUM = "android:imsSeqNum";
    public static final String EXTRA_SERVICE_ID = "android:imsServiceId";
    public static final String EXTRA_USSD = "android:ussd";
    public static final String IMS_SERVICE = "ims";
    public static final int INCOMING_CALL_RESULT_CODE = 101;
    public static final String PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE = "persist.dbg.volte_avail_ovr";
    public static final int PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE_DEFAULT = 0;
    public static final String PROPERTY_DBG_VT_AVAIL_OVERRIDE = "persist.dbg.vt_avail_ovr";
    public static final int PROPERTY_DBG_VT_AVAIL_OVERRIDE_DEFAULT = 0;
    public static final String PROPERTY_DBG_WFC_AVAIL_OVERRIDE = "persist.dbg.wfc_avail_ovr";
    public static final int PROPERTY_DBG_WFC_AVAIL_OVERRIDE_DEFAULT = 0;
    private static final String TAG = "ImsManager";
    private static HashMap<Integer, ImsManager> sImsManagerInstances = new HashMap();
    private CapabilityService mCapabilitiesApi;
    private ChatService mChatApi;
    private ImsConfig mConfig = null;
    private ContactsService mContactsApi;
    private Context mContext;
    private ImsServiceDeathRecipient mDeathRecipient = new ImsServiceDeathRecipient();
    private ImsEcbm mEcbm = null;
    private FileTransferService mFileTransferApi;
    private GeolocSharingService mGeolocSharingApi;
    private ImageSharingService mImageSharingApi;
    private IImsService mImsService = null;
    private int mPhoneId;
    private ImsUt mUt = null;
    private VideoSharingService mVideoSharingApi;

    private class ImsRegistrationListenerProxy extends Stub {
        private ImsConnectionStateListener mListener;
        private int mServiceClass;

        public ImsRegistrationListenerProxy(int serviceClass, ImsConnectionStateListener listener) {
            this.mServiceClass = serviceClass;
            this.mListener = listener;
        }

        public boolean isSameProxy(int serviceClass) {
            return this.mServiceClass == serviceClass ? ImsManager.DBG : false;
        }

        public void registrationConnected() {
            ImsManager.log("registrationConnected ::");
            if (this.mListener != null) {
                this.mListener.onImsConnected();
            }
        }

        public void registrationProgressing() {
            ImsManager.log("registrationProgressing ::");
            if (this.mListener != null) {
                this.mListener.onImsProgressing();
            }
        }

        public void registrationDisconnected(ImsReasonInfo imsReasonInfo) {
            ImsManager.log("registrationDisconnected :: imsReasonInfo" + imsReasonInfo);
            if (this.mListener != null) {
                this.mListener.onImsDisconnected(imsReasonInfo);
            }
        }

        public void registrationResumed() {
            ImsManager.log("registrationResumed ::");
            if (this.mListener != null) {
                this.mListener.onImsResumed();
            }
        }

        public void registrationSuspended() {
            ImsManager.log("registrationSuspended ::");
            if (this.mListener != null) {
                this.mListener.onImsSuspended();
            }
        }

        public void registrationServiceCapabilityChanged(int serviceClass, int event) {
            ImsManager.log("registrationServiceCapabilityChanged :: serviceClass=" + serviceClass + ", event=" + event);
            if (this.mListener != null) {
                this.mListener.onImsConnected();
            }
        }

        public void registrationFeatureCapabilityChanged(int serviceClass, int[] enabledFeatures, int[] disabledFeatures) {
            ImsManager.log("registrationFeatureCapabilityChanged :: serviceClass=" + serviceClass);
            if (this.mListener != null) {
                this.mListener.onFeatureCapabilityChanged(serviceClass, enabledFeatures, disabledFeatures);
            }
        }
    }

    private class ImsServiceDeathRecipient implements DeathRecipient {
        private ImsServiceDeathRecipient() {
        }

        public void binderDied() {
            ImsManager.this.mImsService = null;
            ImsManager.this.mUt = null;
            ImsManager.this.mConfig = null;
            ImsManager.this.mEcbm = null;
            if (ImsManager.this.mContext != null) {
                Intent intent = new Intent(ImsManager.ACTION_IMS_SERVICE_DOWN);
                intent.putExtra(ImsManager.EXTRA_PHONE_ID, ImsManager.this.mPhoneId);
                ImsManager.this.mContext.sendBroadcast(new Intent(intent));
            }
        }
    }

    public class MyServiceListener implements JoynServiceListener {
        public void onServiceConnected() {
            Log.d(ImsManager.TAG, "onServiceConnected entry ");
        }

        public void onServiceDisconnected(int error) {
            Log.d(ImsManager.TAG, "onServiceDisconnected entry " + error);
        }
    }

    public static ImsManager getInstance(Context context, int phoneId) {
        synchronized (sImsManagerInstances) {
            if (sImsManagerInstances.containsKey(Integer.valueOf(phoneId))) {
                ImsManager imsManager = (ImsManager) sImsManagerInstances.get(Integer.valueOf(phoneId));
                return imsManager;
            }
            ImsManager mgr = new ImsManager(context, phoneId);
            sImsManagerInstances.put(Integer.valueOf(phoneId), mgr);
            return mgr;
        }
    }

    public static boolean isEnhanced4gLteModeSettingEnabledByUser(Context context) {
        if (Global.getInt(context.getContentResolver(), "volte_vt_enabled", 1) == 1) {
            return DBG;
        }
        return false;
    }

    public static void setEnhanced4gLteModeSetting(Context context, boolean enabled) {
        Global.putInt(context.getContentResolver(), "volte_vt_enabled", enabled ? 1 : 0);
        if (isNonTtyOrTtyOnVolteEnabled(context)) {
            ImsManager imsManager = getInstance(context, SubscriptionManager.getDefaultVoicePhoneId());
            if (imsManager != null) {
                try {
                    imsManager.setAdvanced4GMode(enabled);
                } catch (ImsException e) {
                }
            }
        }
    }

    public static boolean isNonTtyOrTtyOnVolteEnabled(Context context) {
        if (getBooleanCarrierConfig(context, "carrier_volte_tty_supported_bool") || Secure.getInt(context.getContentResolver(), "preferred_tty_mode", 0) == 0) {
            return DBG;
        }
        return false;
    }

    public static boolean isVolteEnabledByPlatform(Context context) {
        if (SystemProperties.getInt(PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE, 0) == 1) {
            return DBG;
        }
        if (context.getResources().getBoolean(17956996) && getBooleanCarrierConfig(context, "carrier_volte_available_bool")) {
            return DBG;
        }
        return false;
    }

    public static boolean isVolteProvisionedOnDevice(Context context) {
        boolean isProvisioned = DBG;
        if (getBooleanCarrierConfig(context, "carrier_volte_provisioning_required_bool")) {
            isProvisioned = false;
            ImsManager mgr = getInstance(context, SubscriptionManager.getDefaultVoicePhoneId());
            if (mgr != null) {
                try {
                    ImsConfig config = mgr.getConfigInterface();
                    if (config != null) {
                        isProvisioned = config.getVolteProvisioned();
                    }
                } catch (ImsException e) {
                }
            }
        }
        return isProvisioned;
    }

    public static boolean isVtEnabledByPlatform(Context context) {
        if (SystemProperties.getInt(PROPERTY_DBG_VT_AVAIL_OVERRIDE, 0) == 1) {
            return DBG;
        }
        if (context.getResources().getBoolean(17957000) && getBooleanCarrierConfig(context, "carrier_vt_available_bool")) {
            return DBG;
        }
        return false;
    }

    public static boolean isWfcEnabledByUser(Context context) {
        if (Global.getInt(context.getContentResolver(), "wfc_ims_enabled", 0) == 1) {
            return DBG;
        }
        return false;
    }

    public static void setWfcSetting(Context context, boolean enabled) {
        int value;
        int i = 0;
        int i2 = 1;
        if (enabled) {
            value = 1;
        } else {
            value = 0;
        }
        Global.putInt(context.getContentResolver(), "wfc_ims_enabled", value);
        ImsManager imsManager = getInstance(context, SubscriptionManager.getDefaultVoicePhoneId());
        if (imsManager != null) {
            try {
                ImsConfig config = imsManager.getConfigInterface();
                if (enabled) {
                    i = 1;
                }
                config.setFeatureValue(2, 18, i, null);
                if (enabled) {
                    imsManager.turnOnIms();
                } else if (getBooleanCarrierConfig(context, "carrier_allow_turnoff_ims_bool") && !(isVolteEnabledByPlatform(context) && isEnhanced4gLteModeSettingEnabledByUser(context))) {
                    log("setWfcSetting() : imsServiceAllowTurnOff -> turnOffIms");
                    imsManager.turnOffIms();
                }
                if (enabled) {
                    i2 = getWfcMode(context);
                }
                setWfcModeInternal(context, i2);
            } catch (ImsException e) {
                loge("setWfcSetting(): " + e);
            }
        }
    }

    public static int getWfcMode(Context context) {
        int setting = Global.getInt(context.getContentResolver(), "wfc_ims_mode", 2);
        log("getWfcMode - setting=" + setting);
        return setting;
    }

    public static void setWfcMode(Context context, int wfcMode) {
        log("setWfcMode - setting=" + wfcMode);
        Global.putInt(context.getContentResolver(), "wfc_ims_mode", wfcMode);
        setWfcModeInternal(context, wfcMode);
    }

    private static void setWfcModeInternal(Context context, int wfcMode) {
        final ImsManager imsManager = getInstance(context, SubscriptionManager.getDefaultVoicePhoneId());
        if (imsManager != null) {
            final int value = wfcMode;
            QueuedWork.singleThreadExecutor().submit(new Runnable() {
                public void run() {
                    try {
                        imsManager.getConfigInterface().setProvisionedValue(26, value);
                    } catch (ImsException e) {
                    }
                }
            });
        }
    }

    public static boolean isWfcRoamingEnabledByUser(Context context) {
        if (Global.getInt(context.getContentResolver(), "wfc_ims_roaming_enabled", 0) == 1) {
            return DBG;
        }
        return false;
    }

    public static void setWfcRoamingSetting(Context context, boolean enabled) {
        final int value = enabled ? 1 : 0;
        Global.putInt(context.getContentResolver(), "wfc_ims_roaming_enabled", value);
        final ImsManager imsManager = getInstance(context, SubscriptionManager.getDefaultVoicePhoneId());
        if (imsManager != null) {
            QueuedWork.singleThreadExecutor().submit(new Runnable() {
                public void run() {
                    try {
                        imsManager.getConfigInterface().setProvisionedValue(25, value);
                    } catch (ImsException e) {
                    }
                }
            });
        }
    }

    public static boolean isWfcEnabledByPlatform(Context context) {
        if (SystemProperties.getInt(PROPERTY_DBG_WFC_AVAIL_OVERRIDE, 0) == 1) {
            return DBG;
        }
        if (context.getResources().getBoolean(17957002) && getBooleanCarrierConfig(context, "carrier_wfc_ims_available_bool")) {
            return DBG;
        }
        return false;
    }

    private ImsManager(Context context, int phoneId) {
        this.mContext = context;
        this.mPhoneId = phoneId;
        createImsService(DBG);
        createTerminalApiServices();
    }

    private void createTerminalApiServices() {
        Log.d(TAG, "createTerminalApiServices entry");
        this.mCapabilitiesApi = new CapabilityService(this.mContext, new MyServiceListener());
        this.mCapabilitiesApi.connect();
        this.mChatApi = new ChatService(this.mContext, new MyServiceListener());
        this.mChatApi.connect();
        this.mContactsApi = new ContactsService(this.mContext, new MyServiceListener());
        this.mContactsApi.connect();
        this.mFileTransferApi = new FileTransferService(this.mContext, new MyServiceListener());
        this.mFileTransferApi.connect();
        this.mGeolocSharingApi = new GeolocSharingService(this.mContext, new MyServiceListener());
        this.mGeolocSharingApi.connect();
        this.mImageSharingApi = new ImageSharingService(this.mContext, new MyServiceListener());
        this.mImageSharingApi.connect();
        this.mVideoSharingApi = new VideoSharingService(this.mContext, new MyServiceListener());
        this.mVideoSharingApi.connect();
    }

    public CapabilityService getCapabilitiesService() {
        return this.mCapabilitiesApi;
    }

    public ChatService getChatService() {
        return this.mChatApi;
    }

    public FileTransferService getFileTransferService() {
        return this.mFileTransferApi;
    }

    public ContactsService getContactsService() {
        return this.mContactsApi;
    }

    public GeolocSharingService getGeolocSharingService() {
        return this.mGeolocSharingApi;
    }

    public ImageSharingService getImageSharingService() {
        return this.mImageSharingApi;
    }

    public VideoSharingService getVideoSharingService() {
        return this.mVideoSharingApi;
    }

    public boolean isServiceAvailable() {
        if (this.mImsService == null && ServiceManager.checkService(getImsServiceName(this.mPhoneId)) == null) {
            return false;
        }
        return DBG;
    }

    public int open(int serviceClass, PendingIntent incomingCallPendingIntent, ImsConnectionStateListener listener) throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        log("ImsManager: open");
        if (incomingCallPendingIntent == null) {
            throw new NullPointerException("incomingCallPendingIntent can't be null");
        } else if (listener == null) {
            throw new NullPointerException("listener can't be null");
        } else {
            try {
                int result = this.mImsService.open(this.mPhoneId, serviceClass, incomingCallPendingIntent, createRegistrationListenerProxy(serviceClass, listener));
                if (result > 0) {
                    return result;
                }
                throw new ImsException("open()", result * -1);
            } catch (RemoteException e) {
                throw new ImsException("open()", e, 106);
            }
        }
    }

    public void close(int serviceId) throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        log("ImsManager: close");
        try {
            this.mImsService.close(serviceId);
            this.mUt = null;
            this.mConfig = null;
            this.mEcbm = null;
        } catch (RemoteException e) {
            throw new ImsException("close()", e, 106);
        } catch (Throwable th) {
            this.mUt = null;
            this.mConfig = null;
            this.mEcbm = null;
        }
    }

    public ImsUtInterface getSupplementaryServiceConfiguration(int serviceId) throws ImsException {
        if (this.mUt == null) {
            checkAndThrowExceptionIfServiceUnavailable();
            try {
                IImsUt iUt = this.mImsService.getUtInterface(serviceId);
                if (iUt == null) {
                    throw new ImsException("getSupplementaryServiceConfiguration()", 801);
                }
                this.mUt = new ImsUt(iUt);
            } catch (RemoteException e) {
                throw new ImsException("getSupplementaryServiceConfiguration()", e, 106);
            }
        }
        return this.mUt;
    }

    public boolean isConnected(int serviceId, int serviceType, int callType) throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            return this.mImsService.isConnected(serviceId, serviceType, callType);
        } catch (RemoteException e) {
            throw new ImsException("isServiceConnected()", e, 106);
        }
    }

    public boolean isOpened(int serviceId) throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            return this.mImsService.isOpened(serviceId);
        } catch (RemoteException e) {
            throw new ImsException("isOpened()", e, 106);
        }
    }

    public ImsCallProfile createCallProfile(int serviceId, int serviceType, int callType) throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            return this.mImsService.createCallProfile(serviceId, serviceType, callType);
        } catch (RemoteException e) {
            throw new ImsException("createCallProfile()", e, 106);
        }
    }

    public ImsCall makeCall(int serviceId, ImsCallProfile profile, String[] callees, Listener listener) throws ImsException {
        log("makeCall :: serviceId=" + serviceId + ", profile=" + profile + ", callees=" + callees);
        checkAndThrowExceptionIfServiceUnavailable();
        ImsCall call = new ImsCall(this.mContext, profile);
        call.setListener(listener);
        ImsCallSession session = createCallSession(serviceId, profile);
        if (callees == null || callees.length != 1 || profile.getCallExtraBoolean("conference")) {
            call.start(session, callees);
        } else {
            call.start(session, callees[0]);
        }
        return call;
    }

    public ImsCall takeCall(int serviceId, Intent incomingCallIntent, Listener listener) throws ImsException {
        log("takeCall :: serviceId=" + serviceId + ", incomingCall=" + incomingCallIntent);
        checkAndThrowExceptionIfServiceUnavailable();
        if (incomingCallIntent == null) {
            throw new ImsException("Can't retrieve session with null intent", 101);
        } else if (serviceId != getServiceId(incomingCallIntent)) {
            throw new ImsException("Service id is mismatched in the incoming call intent", 101);
        } else {
            String callId = getCallId(incomingCallIntent);
            if (callId == null) {
                throw new ImsException("Call ID missing in the incoming call intent", 101);
            }
            try {
                IImsCallSession session = this.mImsService.getPendingCallSession(serviceId, callId);
                if (session == null) {
                    throw new ImsException("No pending session for the call", 107);
                }
                ImsCall call = new ImsCall(this.mContext, session.getCallProfile());
                call.attachSession(new ImsCallSession(session));
                call.setListener(listener);
                return call;
            } catch (Throwable t) {
                ImsException imsException = new ImsException("takeCall()", t, 0);
            }
        }
    }

    public ImsConfig getConfigInterface() throws ImsException {
        if (this.mConfig == null) {
            checkAndThrowExceptionIfServiceUnavailable();
            try {
                IImsConfig config = this.mImsService.getConfigInterface(this.mPhoneId);
                if (config == null) {
                    throw new ImsException("getConfigInterface()", 131);
                }
                this.mConfig = new ImsConfig(config, this.mContext);
            } catch (RemoteException e) {
                throw new ImsException("getConfigInterface()", e, 106);
            }
        }
        log("getConfigInterface(), mConfig= " + this.mConfig);
        return this.mConfig;
    }

    public void setUiTTYMode(Context context, int serviceId, int uiTtyMode, Message onComplete) throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            this.mImsService.setUiTTYMode(serviceId, uiTtyMode, onComplete);
            if (!getBooleanCarrierConfig(context, "carrier_volte_tty_supported_bool")) {
                boolean z = (uiTtyMode == 0 && isEnhanced4gLteModeSettingEnabledByUser(context)) ? DBG : false;
                setAdvanced4GMode(z);
            }
        } catch (RemoteException e) {
            throw new ImsException("setTTYMode()", e, 106);
        }
    }

    private static boolean getBooleanCarrierConfig(Context context, String key) {
        CarrierConfigManager configManager = (CarrierConfigManager) context.getSystemService("carrier_config");
        PersistableBundle b = null;
        if (configManager != null) {
            b = configManager.getConfig();
        }
        if (b != null) {
            return b.getBoolean(key);
        }
        return CarrierConfigManager.getDefaultConfig().getBoolean(key);
    }

    private static String getCallId(Intent incomingCallIntent) {
        if (incomingCallIntent == null) {
            return null;
        }
        return incomingCallIntent.getStringExtra(EXTRA_CALL_ID);
    }

    private static int getSeqNum(Intent incomingCallIntent) {
        if (incomingCallIntent == null) {
            return -1;
        }
        return incomingCallIntent.getIntExtra(EXTRA_SEQ_NUM, -1);
    }

    private static int getServiceId(Intent incomingCallIntent) {
        if (incomingCallIntent == null) {
            return -1;
        }
        return incomingCallIntent.getIntExtra(EXTRA_SERVICE_ID, -1);
    }

    private void checkAndThrowExceptionIfServiceUnavailable() throws ImsException {
        if (this.mImsService == null) {
            createImsService(DBG);
            if (this.mImsService == null) {
                throw new ImsException("Service is unavailable", 106);
            }
        }
    }

    private static String getImsServiceName(int phoneId) {
        return "ims";
    }

    private void createImsService(boolean checkService) {
        if (checkService && ServiceManager.checkService(getImsServiceName(this.mPhoneId)) == null) {
            log("ImsManager: createImsService binder is null");
            return;
        }
        IBinder b = ServiceManager.getService(getImsServiceName(this.mPhoneId));
        if (b != null) {
            try {
                b.linkToDeath(this.mDeathRecipient, 0);
            } catch (RemoteException e) {
            }
        }
        this.mImsService = IImsService.Stub.asInterface(b);
        log("ImsManager: mImsService = " + this.mImsService);
    }

    private ImsCallSession createCallSession(int serviceId, ImsCallProfile profile) throws ImsException {
        try {
            return new ImsCallSession(this.mImsService.createCallSession(serviceId, profile, null));
        } catch (RemoteException e) {
            return null;
        }
    }

    private ImsRegistrationListenerProxy createRegistrationListenerProxy(int serviceClass, ImsConnectionStateListener listener) {
        return new ImsRegistrationListenerProxy(serviceClass, listener);
    }

    private static void log(String s) {
        Rlog.d(TAG, s);
    }

    private static void loge(String s) {
        Rlog.e(TAG, s);
    }

    private static void loge(String s, Throwable t) {
        Rlog.e(TAG, s, t);
    }

    private void turnOnIms() throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            this.mImsService.turnOnIms(this.mPhoneId);
        } catch (RemoteException e) {
            throw new ImsException("turnOnIms() ", e, 106);
        }
    }

    private boolean isImsTurnOffAllowed() {
        return (!getBooleanCarrierConfig(this.mContext, "carrier_allow_turnoff_ims_bool") || (isWfcEnabledByPlatform(this.mContext) && isWfcEnabledByUser(this.mContext))) ? false : DBG;
    }

    private void setAdvanced4GMode(boolean turnOn) throws ImsException {
        int i = 1;
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            ImsConfig config = getConfigInterface();
            if (config != null && (turnOn || !isImsTurnOffAllowed())) {
                int i2;
                if (turnOn) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                config.setFeatureValue(0, 13, i2, null);
                if (isVtEnabledByPlatform(this.mContext)) {
                    if (!turnOn) {
                        i = 0;
                    }
                    config.setFeatureValue(1, 13, i, null);
                }
            }
        } catch (ImsException e) {
            log("setAdvanced4GMode() : " + e);
        }
        if (turnOn) {
            turnOnIms();
        } else if (isImsTurnOffAllowed()) {
            log("setAdvanced4GMode() : imsServiceAllowTurnOff -> turnOffIms");
            turnOffIms();
        }
    }

    private void turnOffIms() throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            this.mImsService.turnOffIms(this.mPhoneId);
        } catch (RemoteException e) {
            throw new ImsException("turnOffIms() ", e, 106);
        }
    }

    public ImsEcbm getEcbmInterface(int serviceId) throws ImsException {
        if (this.mEcbm == null) {
            checkAndThrowExceptionIfServiceUnavailable();
            try {
                IImsEcbm iEcbm = this.mImsService.getEcbmInterface(serviceId);
                if (iEcbm == null) {
                    throw new ImsException("getEcbmInterface()", 901);
                }
                this.mEcbm = new ImsEcbm(iEcbm);
            } catch (RemoteException e) {
                throw new ImsException("getEcbmInterface()", e, 106);
            }
        }
        return this.mEcbm;
    }

    public void setCallIndication(int serviceId, Intent incomingCallIndication, boolean isAllow) throws ImsException {
        log("setCallIndication :: serviceId=" + serviceId + ", incomingCallIndication=" + incomingCallIndication);
        checkAndThrowExceptionIfServiceUnavailable();
        if (incomingCallIndication == null) {
            throw new ImsException("Can't retrieve session with null intent", 101);
        } else if (serviceId != getServiceId(incomingCallIndication)) {
            throw new ImsException("Service id is mismatched in the incoming call intent", 101);
        } else {
            String callId = getCallId(incomingCallIndication);
            if (callId == null) {
                throw new ImsException("Call ID missing in the incoming call intent", 101);
            }
            int seqNum = getSeqNum(incomingCallIndication);
            if (seqNum == -1) {
                throw new ImsException("seqNum missing in the incoming call intent", 101);
            }
            try {
                this.mImsService.setCallIndication(callId, seqNum, isAllow);
            } catch (RemoteException e) {
                throw new ImsException("setCallIndication()", e, 106);
            }
        }
    }

    public int getImsState() throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            return this.mImsService.getImsState();
        } catch (RemoteException e) {
            throw new ImsException("getImsState()", e, 106);
        }
    }

    public boolean getImsRegInfo() throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            return this.mImsService.getImsRegInfo(this.mPhoneId);
        } catch (RemoteException e) {
            throw new ImsException("getImsRegInfo", e, 106);
        }
    }

    public String getImsExtInfo() throws ImsException {
        String imsExtInfo = PhoneConstants.CFU_QUERY_TYPE_DEF_VALUE;
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            return this.mImsService.getImsExtInfo();
        } catch (RemoteException e) {
            throw new ImsException("getImsExtInfo()", e, 106);
        }
    }

    public void hangupAllCall() throws ImsException {
        checkAndThrowExceptionIfServiceUnavailable();
        try {
            this.mImsService.hangupAllCall();
        } catch (RemoteException e) {
            throw new ImsException("hangupAll()", e, 106);
        }
    }

    public int getWfcStatusCode() {
        int i = 100;
        if (this.mImsService != null) {
            try {
                i = this.mImsService.getRegistrationStatus();
            } catch (RemoteException e) {
            }
        }
        return i;
    }
}
