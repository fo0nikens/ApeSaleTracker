package android.support.design.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

class ViewGroupUtils {
    private static final ViewGroupUtilsImpl IMPL = null;

    private interface ViewGroupUtilsImpl {
        void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect);
    }

    private static class ViewGroupUtilsImplBase implements ViewGroupUtilsImpl {
        private ViewGroupUtilsImplBase() {
        }

        public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
            parent.offsetDescendantRectToMyCoords(child, rect);
        }
    }

    private static class ViewGroupUtilsImplHoneycomb implements ViewGroupUtilsImpl {
        private ViewGroupUtilsImplHoneycomb() {
        }

        public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
            ViewGroupUtilsHoneycomb.offsetDescendantRect(parent, child, rect);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.design.widget.ViewGroupUtils.<clinit>():void
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
        r2 = 0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 11;
        if (r0 < r1) goto L_0x000f;
    L_0x0007:
        r1 = new android.support.design.widget.ViewGroupUtils$ViewGroupUtilsImplHoneycomb;
        r1.<init>();
        IMPL = r1;
    L_0x000f:
        r1 = new android.support.design.widget.ViewGroupUtils$ViewGroupUtilsImplBase;
        r1.<init>();
        IMPL = r1;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.ViewGroupUtils.<clinit>():void");
    }

    ViewGroupUtils() {
    }

    static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
        IMPL.offsetDescendantRect(parent, descendant, rect);
    }

    static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }
}
