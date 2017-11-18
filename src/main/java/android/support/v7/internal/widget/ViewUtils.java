package android.support.v7.internal.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.internal.widget.ViewUtils.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:370)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:356)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:106)
	... 7 more
*/
        /*
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 18;
        if (r1 < r2) goto L_0x0025;
    L_0x0006:
        r1 = android.view.View.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2 = "computeFitSystemWindows";	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3 = 2;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3 = new java.lang.Class[r3];	 Catch:{ NoSuchMethodException -> 0x0026 }
        r4 = 0;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r5 = android.graphics.Rect.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3[r4] = r5;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r4 = 1;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r5 = android.graphics.Rect.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3[r4] = r5;	 Catch:{ NoSuchMethodException -> 0x0026 }
        sComputeFitSystemWindowsMethod = r1;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r1 = sComputeFitSystemWindowsMethod;	 Catch:{ NoSuchMethodException -> 0x0026 }
        if (r1 != 0) goto L_0x0025;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r1 = sComputeFitSystemWindowsMethod;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2 = 1;	 Catch:{ NoSuchMethodException -> 0x0026 }
    L_0x0026:
        r0 = move-exception;
        r1 = "ViewUtils";
        r2 = "Could not find method computeFitSystemWindows. Oh well.";
        android.util.Log.d(r1, r2);
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.internal.widget.ViewUtils.<clinit>():void");
    }

    private ViewUtils() {
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static int combineMeasuredStates(int curState, int newState) {
        return curState | newState;
    }

    public static void computeFitSystemWindows(View view, Rect inoutInsets, Rect outLocalInsets) {
        if (sComputeFitSystemWindowsMethod != null) {
            try {
                sComputeFitSystemWindowsMethod.invoke(view, new Object[]{inoutInsets, outLocalInsets});
            } catch (Exception e) {
                Log.d(TAG, "Could not invoke computeFitSystemWindows", e);
            }
        }
    }

    public static void makeOptionalFitsSystemWindows(View view) {
        if (VERSION.SDK_INT >= 16) {
            try {
                Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                method.invoke(view, new Object[0]);
            } catch (NoSuchMethodException e) {
                Log.d(TAG, "Could not find method makeOptionalFitsSystemWindows. Oh well...");
            } catch (InvocationTargetException e2) {
                Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e2);
            } catch (IllegalAccessException e3) {
                Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e3);
            }
        }
    }
}
