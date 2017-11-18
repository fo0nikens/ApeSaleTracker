package android.support.v4.widget;

import android.view.View;
import android.widget.PopupWindow;

public class PopupWindowCompat {
    static final PopupWindowImpl IMPL = null;

    interface PopupWindowImpl {
        boolean getOverlapAnchor(PopupWindow popupWindow);

        int getWindowLayoutType(PopupWindow popupWindow);

        void setOverlapAnchor(PopupWindow popupWindow, boolean z);

        void setWindowLayoutType(PopupWindow popupWindow, int i);

        void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3);
    }

    static class BasePopupWindowImpl implements PopupWindowImpl {
        BasePopupWindowImpl() {
        }

        public void showAsDropDown(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
            popup.showAsDropDown(anchor, xoff, yoff);
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
        }

        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            return false;
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
        }

        public int getWindowLayoutType(PopupWindow popupWindow) {
            return 0;
        }
    }

    static class GingerbreadPopupWindowImpl extends BasePopupWindowImpl {
        GingerbreadPopupWindowImpl() {
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
            PopupWindowCompatGingerbread.setWindowLayoutType(popupWindow, layoutType);
        }

        public int getWindowLayoutType(PopupWindow popupWindow) {
            return PopupWindowCompatGingerbread.getWindowLayoutType(popupWindow);
        }
    }

    static class KitKatPopupWindowImpl extends GingerbreadPopupWindowImpl {
        KitKatPopupWindowImpl() {
        }

        public void showAsDropDown(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
            PopupWindowCompatKitKat.showAsDropDown(popup, anchor, xoff, yoff, gravity);
        }
    }

    static class Api21PopupWindowImpl extends KitKatPopupWindowImpl {
        Api21PopupWindowImpl() {
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
            PopupWindowCompatApi21.setOverlapAnchor(popupWindow, overlapAnchor);
        }

        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            return PopupWindowCompatApi21.getOverlapAnchor(popupWindow);
        }
    }

    static class Api23PopupWindowImpl extends Api21PopupWindowImpl {
        Api23PopupWindowImpl() {
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
            PopupWindowCompatApi23.setOverlapAnchor(popupWindow, overlapAnchor);
        }

        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            return PopupWindowCompatApi23.getOverlapAnchor(popupWindow);
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
            PopupWindowCompatApi23.setWindowLayoutType(popupWindow, layoutType);
        }

        public int getWindowLayoutType(PopupWindow popupWindow) {
            return PopupWindowCompatApi23.getWindowLayoutType(popupWindow);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.widget.PopupWindowCompat.<clinit>():void
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
        r1 = 23;
        if (r0 < r1) goto L_0x000e;
    L_0x0006:
        r1 = new android.support.v4.widget.PopupWindowCompat$Api23PopupWindowImpl;
        r1.<init>();
        IMPL = r1;
    L_0x000e:
        r1 = 21;
        if (r0 < r1) goto L_0x001a;
    L_0x0012:
        r1 = new android.support.v4.widget.PopupWindowCompat$Api21PopupWindowImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
    L_0x001a:
        r1 = 19;
        if (r0 < r1) goto L_0x0026;
        r1 = new android.support.v4.widget.PopupWindowCompat$KitKatPopupWindowImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        r1 = 9;
        if (r0 < r1) goto L_0x0032;
        r1 = new android.support.v4.widget.PopupWindowCompat$GingerbreadPopupWindowImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        r1 = new android.support.v4.widget.PopupWindowCompat$BasePopupWindowImpl;
        r1.<init>();
        IMPL = r1;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.PopupWindowCompat.<clinit>():void");
    }

    private PopupWindowCompat() {
    }

    public static void showAsDropDown(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
        IMPL.showAsDropDown(popup, anchor, xoff, yoff, gravity);
    }

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
        IMPL.setOverlapAnchor(popupWindow, overlapAnchor);
    }

    public static boolean getOverlapAnchor(PopupWindow popupWindow) {
        return IMPL.getOverlapAnchor(popupWindow);
    }

    public static void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
        IMPL.setWindowLayoutType(popupWindow, layoutType);
    }

    public static int getWindowLayoutType(PopupWindow popupWindow) {
        return IMPL.getWindowLayoutType(popupWindow);
    }
}
