package android.support.v7.internal.widget;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import java.lang.reflect.Field;

public class DrawableUtils {
    public static final Rect INSETS_NONE = null;
    private static final String TAG = "DrawableUtils";
    private static Class<?> sInsetsClazz;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.internal.widget.DrawableUtils.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
        /*
        r0 = new android.graphics.Rect;
        r0.<init>();
        INSETS_NONE = r0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 18;
        if (r0 < r1) goto L_0x0015;
    L_0x000d:
        r0 = "android.graphics.Insets";	 Catch:{ ClassNotFoundException -> 0x0016 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x0016 }
        sInsetsClazz = r0;	 Catch:{ ClassNotFoundException -> 0x0016 }
    L_0x0016:
        r0 = move-exception;
        goto L_0x0015;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.internal.widget.DrawableUtils.<clinit>():void");
    }

    private DrawableUtils() {
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        if (sInsetsClazz != null) {
            try {
                drawable = DrawableCompat.unwrap(drawable);
                Object insets = drawable.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(drawable, new Object[0]);
                if (insets != null) {
                    Rect result = new Rect();
                    for (Field field : sInsetsClazz.getFields()) {
                        String name = field.getName();
                        Object obj = -1;
                        switch (name.hashCode()) {
                            case -1383228885:
                                if (name.equals("bottom")) {
                                    obj = 3;
                                    break;
                                }
                                break;
                            case 115029:
                                if (name.equals("top")) {
                                    obj = 1;
                                    break;
                                }
                                break;
                            case 3317767:
                                if (name.equals("left")) {
                                    obj = null;
                                    break;
                                }
                                break;
                            case 108511772:
                                if (name.equals("right")) {
                                    obj = 2;
                                    break;
                                }
                                break;
                        }
                        switch (obj) {
                            case null:
                                result.left = field.getInt(insets);
                                break;
                            case 1:
                                result.top = field.getInt(insets);
                                break;
                            case 2:
                                result.right = field.getInt(insets);
                                break;
                            case 3:
                                result.bottom = field.getInt(insets);
                                break;
                            default:
                                break;
                        }
                    }
                    return result;
                }
            } catch (Exception e) {
                Log.e(TAG, "Couldn't obtain the optical insets. Ignoring.");
            }
        }
        return INSETS_NONE;
    }
}
