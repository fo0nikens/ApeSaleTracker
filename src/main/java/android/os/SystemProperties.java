package android.os;

import android.util.Slog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SystemProperties {
    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 91;
    private static final ArrayList<Runnable> sChangeCallbacks = new ArrayList();

    private static native void native_add_change_callback();

    private static native String native_get(String str);

    private static native String native_get(String str, String str2);

    private static native boolean native_get_boolean(String str, boolean z);

    private static native int native_get_int(String str, int i);

    private static native long native_get_long(String str, long j);

    private static native void native_set(String str, String str2);

    public static String get(String key) {
        if (key.length() <= 31) {
            return native_get(key);
        }
        throw new IllegalArgumentException("key.length > 31");
    }

    public static String get(String key, String def) {
        if (key.length() <= 31) {
            return native_get(key, def);
        }
        throw new IllegalArgumentException("key.length > 31");
    }

    public static int getInt(String key, int def) {
        if (key.length() <= 31) {
            return native_get_int(key, def);
        }
        throw new IllegalArgumentException("key.length > 31");
    }

    public static long getLong(String key, long def) {
        if (key.length() <= 31) {
            return native_get_long(key, def);
        }
        throw new IllegalArgumentException("key.length > 31");
    }

    public static boolean getBoolean(String key, boolean def) {
        if (key.length() <= 31) {
            return native_get_boolean(key, def);
        }
        throw new IllegalArgumentException("key.length > 31");
    }

    public static void set(String key, String val) {
        if (key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else if (val == null || val.length() <= 91) {
            native_set(key, val);
        } else {
            throw new IllegalArgumentException("val.length > 91");
        }
    }

    public static void addChangeCallback(Runnable callback) {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.size() == 0) {
                native_add_change_callback();
            }
            sChangeCallbacks.add(callback);
        }
    }

    static void callChangeCallbacks() {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.size() == 0) {
                return;
            }
            ArrayList<Runnable> callbacks = new ArrayList(sChangeCallbacks);
            for (int i = 0; i < callbacks.size(); i++) {
                ((Runnable) callbacks.get(i)).run();
            }
        }
    }

    public static boolean is_target(String s) {
        return get("ro.target", "").equals(s);
    }

    public static boolean is_project(String s) {
        return get("ro.project", "").equals(s);
    }

    public static boolean is_mmx_common() {
        return is_project("mmx_in");
    }

    public static boolean is_mmx_in() {
        return is_project("mmx_in");
    }

    public static boolean is_mmx_ru() {
        return is_project("mmx_ru");
    }

    public static boolean is_blu_us() {
        return is_project("blu_us") || is_project("blu_viv_us") || is_project("blu_dig_us") || is_project("blu_bmo_us");
    }

    public static boolean is_blu_viv_us() {
        return is_project("blu_viv_us");
    }

    public static boolean is_blu_dig_us() {
        return is_project("blu_dig_us");
    }

    public static boolean is_blu_bmo_us() {
        return is_project("blu_bmo_us");
    }

    public static boolean is_cel_tn() {
        return is_project("cel_tn");
    }

    public static boolean is_myp_ph() {
        return is_project("myp_ph");
    }

    public static boolean is_qmb_pk() {
        return is_project("qmb_pk");
    }

    public static boolean is_wik_du() {
        return is_project("wik_du");
    }

    public static boolean is_wik_fr() {
        return is_project("wik_fr");
    }

    public static boolean is_wik_id() {
        return is_project("wik_id");
    }

    public static boolean is_wik_th() {
        return is_project("wik_th");
    }

    public static boolean is_wik_vn() {
        return is_project("wik_vn");
    }

    public static boolean is_cas_tr() {
        return is_project("cas_tr");
    }

    public static boolean is_lax_tel_mx() {
        return is_project("lax_tel_mx");
    }

    public static boolean is_clf_ph() {
        return is_project("clf_ph");
    }

    public static boolean is_wiko_prj() {
        return get("ro.wiko", "").equals("wiko");
    }

    public static boolean is_wiko_unify() {
        return get("ro.wiko.type", "").equals("unify");
    }

    public static boolean is_wiko_area(String s) {
        if (s != null && is_wiko_prj() && is_wiko_unify()) {
            return get_market_area().equals(s);
        }
        return false;
    }

    public static boolean is_wiko_eu() {
        if (!is_wiko_prj()) {
            return false;
        }
        if (is_wiko_unify()) {
            return is_wiko_area("EU");
        }
        return true;
    }

    public static boolean is_wiko_asia() {
        if (is_wiko_prj() && is_wiko_unify() && (is_wiko_area("TH") || is_wiko_area("DU") || is_wiko_area("VN") || is_wiko_area("ID"))) {
            return true;
        }
        return false;
    }

    public static String get_market_area() {
        IOException ex;
        Throwable th;
        NumberFormatException ex2;
        String Market_Area = "";
        FileReader reader = null;
        String filename = "/proc/Tinno_devinfo/Market_Area";
        char[] buf = new char[8];
        if (new File(filename).exists()) {
            try {
                FileReader reader2 = new FileReader(filename);
                try {
                    int n = reader2.read(buf);
                    if (n > 0) {
                        Market_Area = new String(buf, 0, n);
                    }
                    if (reader2 != null) {
                        try {
                            reader2.close();
                            reader = reader2;
                        } catch (IOException e) {
                            reader = reader2;
                        }
                    }
                } catch (IOException e2) {
                    ex = e2;
                    reader = reader2;
                    try {
                        Slog.w("WikoUnify", "Couldn't read Market_Area  from " + filename + ": " + ex);
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e3) {
                            }
                        }
                        return Market_Area;
                    } catch (Throwable th2) {
                        th = th2;
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e4) {
                            }
                        }
                        throw th;
                    }
                } catch (NumberFormatException e5) {
                    ex2 = e5;
                    reader = reader2;
                    Slog.w("WikoUnify", "Couldn't read Market_Area  from " + filename + ": " + ex2);
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e6) {
                        }
                    }
                    return Market_Area;
                } catch (Throwable th3) {
                    th = th3;
                    reader = reader2;
                    if (reader != null) {
                        reader.close();
                    }
                    throw th;
                }
            } catch (IOException e7) {
                ex = e7;
                Slog.w("WikoUnify", "Couldn't read Market_Area  from " + filename + ": " + ex);
                if (reader != null) {
                    reader.close();
                }
                return Market_Area;
            } catch (NumberFormatException e8) {
                ex2 = e8;
                Slog.w("WikoUnify", "Couldn't read Market_Area  from " + filename + ": " + ex2);
                if (reader != null) {
                    reader.close();
                }
                return Market_Area;
            }
        }
        Slog.e("WikoUnify", "get_market_area filename=" + filename + ",  not exsit!");
        return Market_Area;
    }

    public static boolean is_lax_cla_co() {
        return is_project("lax_cla_co");
    }

    public static boolean is_sma_ir() {
        return is_project("sma_ir");
    }

    public static boolean is_knb_mm() {
        return is_project("knb_mm");
    }

    public static boolean notification_light_tinno() {
        return get("ro.notification_light_tinno", "false").equals("true");
    }
}
