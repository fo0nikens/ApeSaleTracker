package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;

public class DrawableCompat {
    static final DrawableImpl IMPL = null;

    interface DrawableImpl {
        int getLayoutDirection(Drawable drawable);

        boolean isAutoMirrored(Drawable drawable);

        void jumpToCurrentState(Drawable drawable);

        void setAutoMirrored(Drawable drawable, boolean z);

        void setHotspot(Drawable drawable, float f, float f2);

        void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4);

        void setLayoutDirection(Drawable drawable, int i);

        void setTint(Drawable drawable, int i);

        void setTintList(Drawable drawable, ColorStateList colorStateList);

        void setTintMode(Drawable drawable, Mode mode);

        Drawable wrap(Drawable drawable);
    }

    static class BaseDrawableImpl implements DrawableImpl {
        BaseDrawableImpl() {
        }

        public void jumpToCurrentState(Drawable drawable) {
        }

        public void setAutoMirrored(Drawable drawable, boolean mirrored) {
        }

        public boolean isAutoMirrored(Drawable drawable) {
            return false;
        }

        public void setHotspot(Drawable drawable, float x, float y) {
        }

        public void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) {
        }

        public void setTint(Drawable drawable, int tint) {
            DrawableCompatBase.setTint(drawable, tint);
        }

        public void setTintList(Drawable drawable, ColorStateList tint) {
            DrawableCompatBase.setTintList(drawable, tint);
        }

        public void setTintMode(Drawable drawable, Mode tintMode) {
            DrawableCompatBase.setTintMode(drawable, tintMode);
        }

        public Drawable wrap(Drawable drawable) {
            return DrawableCompatBase.wrapForTinting(drawable);
        }

        public void setLayoutDirection(Drawable drawable, int layoutDirection) {
        }

        public int getLayoutDirection(Drawable drawable) {
            return 0;
        }
    }

    static class HoneycombDrawableImpl extends BaseDrawableImpl {
        HoneycombDrawableImpl() {
        }

        public void jumpToCurrentState(Drawable drawable) {
            DrawableCompatHoneycomb.jumpToCurrentState(drawable);
        }

        public Drawable wrap(Drawable drawable) {
            return DrawableCompatHoneycomb.wrapForTinting(drawable);
        }
    }

    static class JellybeanMr1DrawableImpl extends HoneycombDrawableImpl {
        JellybeanMr1DrawableImpl() {
        }

        public void setLayoutDirection(Drawable drawable, int layoutDirection) {
            DrawableCompatJellybeanMr1.setLayoutDirection(drawable, layoutDirection);
        }

        public int getLayoutDirection(Drawable drawable) {
            int dir = DrawableCompatJellybeanMr1.getLayoutDirection(drawable);
            return dir >= 0 ? dir : 0;
        }
    }

    static class KitKatDrawableImpl extends JellybeanMr1DrawableImpl {
        KitKatDrawableImpl() {
        }

        public void setAutoMirrored(Drawable drawable, boolean mirrored) {
            DrawableCompatKitKat.setAutoMirrored(drawable, mirrored);
        }

        public boolean isAutoMirrored(Drawable drawable) {
            return DrawableCompatKitKat.isAutoMirrored(drawable);
        }

        public Drawable wrap(Drawable drawable) {
            return DrawableCompatKitKat.wrapForTinting(drawable);
        }
    }

    static class LollipopDrawableImpl extends KitKatDrawableImpl {
        LollipopDrawableImpl() {
        }

        public void setHotspot(Drawable drawable, float x, float y) {
            DrawableCompatLollipop.setHotspot(drawable, x, y);
        }

        public void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) {
            DrawableCompatLollipop.setHotspotBounds(drawable, left, top, right, bottom);
        }

        public void setTint(Drawable drawable, int tint) {
            DrawableCompatLollipop.setTint(drawable, tint);
        }

        public void setTintList(Drawable drawable, ColorStateList tint) {
            DrawableCompatLollipop.setTintList(drawable, tint);
        }

        public void setTintMode(Drawable drawable, Mode tintMode) {
            DrawableCompatLollipop.setTintMode(drawable, tintMode);
        }

        public Drawable wrap(Drawable drawable) {
            return DrawableCompatLollipop.wrapForTinting(drawable);
        }
    }

    static class LollipopMr1DrawableImpl extends LollipopDrawableImpl {
        LollipopMr1DrawableImpl() {
        }

        public Drawable wrap(Drawable drawable) {
            return DrawableCompatApi22.wrapForTinting(drawable);
        }
    }

    static class MDrawableImpl extends LollipopMr1DrawableImpl {
        MDrawableImpl() {
        }

        public void setLayoutDirection(Drawable drawable, int layoutDirection) {
            DrawableCompatApi23.setLayoutDirection(drawable, layoutDirection);
        }

        public int getLayoutDirection(Drawable drawable) {
            return DrawableCompatApi23.getLayoutDirection(drawable);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.graphics.drawable.DrawableCompat.<clinit>():void
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
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:360)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:106)
	... 7 more
*/
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 23;
        if (r0 < r1) goto L_0x000e;
    L_0x0006:
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$MDrawableImpl;
        r1.<init>();
        IMPL = r1;
    L_0x000e:
        r1 = 22;
        if (r0 < r1) goto L_0x001a;
    L_0x0012:
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$LollipopMr1DrawableImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
    L_0x001a:
        r1 = 21;
        if (r0 < r1) goto L_0x0026;
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$LollipopDrawableImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        r1 = 19;
        if (r0 < r1) goto L_0x0032;
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$KitKatDrawableImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        r1 = 17;
        if (r0 < r1) goto L_0x003e;
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$JellybeanMr1DrawableImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        r1 = 11;
        if (r0 < r1) goto L_0x004a;
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$HoneycombDrawableImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        r1 = new android.support.v4.graphics.drawable.DrawableCompat$BaseDrawableImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.drawable.DrawableCompat.<clinit>():void");
    }

    public static void jumpToCurrentState(Drawable drawable) {
        IMPL.jumpToCurrentState(drawable);
    }

    public static void setAutoMirrored(Drawable drawable, boolean mirrored) {
        IMPL.setAutoMirrored(drawable, mirrored);
    }

    public static boolean isAutoMirrored(Drawable drawable) {
        return IMPL.isAutoMirrored(drawable);
    }

    public static void setHotspot(Drawable drawable, float x, float y) {
        IMPL.setHotspot(drawable, x, y);
    }

    public static void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) {
        IMPL.setHotspotBounds(drawable, left, top, right, bottom);
    }

    public static void setTint(Drawable drawable, int tint) {
        IMPL.setTint(drawable, tint);
    }

    public static void setTintList(Drawable drawable, ColorStateList tint) {
        IMPL.setTintList(drawable, tint);
    }

    public static void setTintMode(Drawable drawable, Mode tintMode) {
        IMPL.setTintMode(drawable, tintMode);
    }

    public static Drawable wrap(Drawable drawable) {
        return IMPL.wrap(drawable);
    }

    public static <T extends Drawable> T unwrap(Drawable drawable) {
        if (drawable instanceof DrawableWrapper) {
            return ((DrawableWrapper) drawable).getWrappedDrawable();
        }
        return drawable;
    }

    public static void setLayoutDirection(Drawable drawable, int layoutDirection) {
        IMPL.setLayoutDirection(drawable, layoutDirection);
    }

    public static int getLayoutDirection(Drawable drawable) {
        return IMPL.getLayoutDirection(drawable);
    }
}
