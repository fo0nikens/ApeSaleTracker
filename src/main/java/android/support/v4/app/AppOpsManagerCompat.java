package android.support.v4.app;

import android.content.Context;
import android.support.annotation.NonNull;

public class AppOpsManagerCompat {
    private static final AppOpsManagerImpl IMPL = null;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_IGNORED = 1;

    private static class AppOpsManagerImpl {
        private AppOpsManagerImpl() {
        }

        public String permissionToOp(String permission) {
            return null;
        }

        public int noteOp(Context context, String op, int uid, String packageName) {
            return 1;
        }

        public int noteProxyOp(Context context, String op, String proxiedPackageName) {
            return 1;
        }
    }

    private static class AppOpsManager23 extends AppOpsManagerImpl {
        private AppOpsManager23() {
            super();
        }

        public String permissionToOp(String permission) {
            return AppOpsManagerCompat23.permissionToOp(permission);
        }

        public int noteOp(Context context, String op, int uid, String packageName) {
            return AppOpsManagerCompat23.noteOp(context, op, uid, packageName);
        }

        public int noteProxyOp(Context context, String op, String proxiedPackageName) {
            return AppOpsManagerCompat23.noteProxyOp(context, op, proxiedPackageName);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.app.AppOpsManagerCompat.<clinit>():void
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
        r2 = 0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 23;
        if (r0 < r1) goto L_0x000f;
    L_0x0007:
        r0 = new android.support.v4.app.AppOpsManagerCompat$AppOpsManager23;
        r0.<init>();
        IMPL = r0;
    L_0x000f:
        r0 = new android.support.v4.app.AppOpsManagerCompat$AppOpsManagerImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.AppOpsManagerCompat.<clinit>():void");
    }

    public static String permissionToOp(@NonNull String permission) {
        return IMPL.permissionToOp(permission);
    }

    public static int noteOp(@NonNull Context context, @NonNull String op, int uid, @NonNull String packageName) {
        return IMPL.noteOp(context, op, uid, packageName);
    }

    public static int noteProxyOp(@NonNull Context context, @NonNull String op, @NonNull String proxiedPackageName) {
        return IMPL.noteProxyOp(context, op, proxiedPackageName);
    }
}
