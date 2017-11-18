package android.app;

import android.annotation.IntDef;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.IStatusBarService.Stub;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StatusBarManager {
    public static final int DISABLE2_MASK = 1;
    public static final int DISABLE2_NONE = 0;
    public static final int DISABLE2_QUICK_SETTINGS = 1;
    public static final int DISABLE_BACK = 4194304;
    public static final int DISABLE_CLOCK = 8388608;
    public static final int DISABLE_EXPAND = 65536;
    public static final int DISABLE_HOME = 2097152;
    public static final int DISABLE_MASK = 67043328;
    @Deprecated
    public static final int DISABLE_NAVIGATION = 18874368;
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int DISABLE_NOTIFICATION_ICONS = 131072;
    @Deprecated
    public static final int DISABLE_NOTIFICATION_TICKER = 524288;
    public static final int DISABLE_RECENT = 16777216;
    public static final int DISABLE_SEARCH = 33554432;
    public static final int DISABLE_SYSTEM_INFO = 1048576;
    public static final int NAVIGATION_HINT_BACK_ALT = 1;
    public static final int NAVIGATION_HINT_IME_SHOWN = 2;
    public static final int STATUS_ALWAYS_ASK = -100;
    public static final int STATUS_OTHER_ACCOUNTS = -97;
    public static final int STATUS_SIP = -99;
    public static final int STATUS_SMS_AUTO = -98;
    private static final String TAG = "StatusBarManager";
    public static final int WINDOW_NAVIGATION_BAR = 2;
    public static final int WINDOW_STATE_HIDDEN = 2;
    public static final int WINDOW_STATE_HIDING = 1;
    public static final int WINDOW_STATE_SHOWING = 0;
    public static final int WINDOW_STATUS_BAR = 1;
    private Context mContext;
    private IStatusBarService mService;
    private IBinder mToken = new Binder();

    @IntDef(flag = true, value = {0, 1, 1})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Disable2Flags {
    }

    StatusBarManager(Context context) {
        this.mContext = context;
    }

    private synchronized IStatusBarService getService() {
        if (this.mService == null) {
            this.mService = Stub.asInterface(ServiceManager.getService("statusbar"));
            if (this.mService == null) {
                Slog.w(TAG, "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mService;
    }

    public void disable(int what) {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.disable(what, this.mToken, this.mContext.getPackageName());
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void disable2(int what) {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.disable2(what, this.mToken, this.mContext.getPackageName());
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void expandNotificationsPanel() {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.expandNotificationsPanel();
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void collapsePanels() {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.collapsePanels();
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void expandSettingsPanel() {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.expandSettingsPanel();
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setIcon(String slot, int iconId, int iconLevel, String contentDescription) {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.setIcon(slot, this.mContext.getPackageName(), iconId, iconLevel, contentDescription);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeIcon(String slot) {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.removeIcon(slot);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setIconVisibility(String slot, boolean visible) {
        try {
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.setIconVisibility(slot, visible);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String windowStateToString(int state) {
        if (state == 1) {
            return "WINDOW_STATE_HIDING";
        }
        if (state == 2) {
            return "WINDOW_STATE_HIDDEN";
        }
        if (state == 0) {
            return "WINDOW_STATE_SHOWING";
        }
        return "WINDOW_STATE_UNKNOWN";
    }

    public void showDefaultAccountStatus(int subId) {
        String pkgName = this.mContext.getPackageName();
        try {
            Slog.d(TAG, "Show SIM indicator from " + pkgName + " with subId = " + subId);
            IStatusBarService svc = getService();
            if (svc != null) {
                svc.showDefaultAccountStatus(subId);
            }
        } catch (RemoteException ex) {
            Slog.d(TAG, "Show SIM indicator from " + pkgName + " occurs exception.");
            throw new RuntimeException(ex);
        }
    }

    public void hideDefaultAccountStatus() {
        String pkgName = this.mContext.getPackageName();
        try {
            Slog.d(TAG, "Hide SIM indicator from " + pkgName + ".");
            if (getService() != null) {
                this.mService.hideDefaultAccountStatus();
            }
        } catch (RemoteException ex) {
            Slog.d(TAG, "Hide SIM indicator from " + pkgName + " occurs exception.");
            throw new RuntimeException(ex);
        }
    }
}
