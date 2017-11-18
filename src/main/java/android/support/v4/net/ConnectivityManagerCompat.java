package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityManagerCompat {
    private static final ConnectivityManagerCompatImpl IMPL = null;

    interface ConnectivityManagerCompatImpl {
        boolean isActiveNetworkMetered(ConnectivityManager connectivityManager);
    }

    static class BaseConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        BaseConnectivityManagerCompatImpl() {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return true;
            }
            switch (info.getType()) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return true;
            }
        }
    }

    static class GingerbreadConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        GingerbreadConnectivityManagerCompatImpl() {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            return ConnectivityManagerCompatGingerbread.isActiveNetworkMetered(cm);
        }
    }

    static class HoneycombMR2ConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        HoneycombMR2ConnectivityManagerCompatImpl() {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            return ConnectivityManagerCompatHoneycombMR2.isActiveNetworkMetered(cm);
        }
    }

    static class JellyBeanConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        JellyBeanConnectivityManagerCompatImpl() {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            return ConnectivityManagerCompatJellyBean.isActiveNetworkMetered(cm);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.net.ConnectivityManagerCompat.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:370)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:360)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:106)
	... 5 more
*/
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 16;
        if (r0 < r1) goto L_0x000e;
    L_0x0006:
        r0 = new android.support.v4.net.ConnectivityManagerCompat$JellyBeanConnectivityManagerCompatImpl;
        r0.<init>();
        IMPL = r0;
    L_0x000e:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 13;
        if (r0 < r1) goto L_0x001c;
    L_0x0014:
        r0 = new android.support.v4.net.ConnectivityManagerCompat$HoneycombMR2ConnectivityManagerCompatImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000d;
    L_0x001c:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 8;
        if (r0 < r1) goto L_0x002a;
        r0 = new android.support.v4.net.ConnectivityManagerCompat$GingerbreadConnectivityManagerCompatImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000d;
        r0 = new android.support.v4.net.ConnectivityManagerCompat$BaseConnectivityManagerCompatImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.net.ConnectivityManagerCompat.<clinit>():void");
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        return IMPL.isActiveNetworkMetered(cm);
    }

    public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager cm, Intent intent) {
        NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        if (info != null) {
            return cm.getNetworkInfo(info.getType());
        }
        return null;
    }
}
