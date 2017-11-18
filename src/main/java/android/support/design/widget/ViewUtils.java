package android.support.design.widget;

import android.view.View;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR = null;
    private static final ViewUtilsImpl IMPL = null;

    private interface ViewUtilsImpl {
        void setBoundsViewOutlineProvider(View view);
    }

    private static class ViewUtilsImplBase implements ViewUtilsImpl {
        private ViewUtilsImplBase() {
        }

        public void setBoundsViewOutlineProvider(View view) {
        }
    }

    private static class ViewUtilsImplLollipop implements ViewUtilsImpl {
        private ViewUtilsImplLollipop() {
        }

        public void setBoundsViewOutlineProvider(View view) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(view);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.design.widget.ViewUtils.<clinit>():void
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
        r1 = new android.support.design.widget.ViewUtils$1;
        r1.<init>();
        DEFAULT_ANIMATOR_CREATOR = r1;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x0016;
    L_0x000e:
        r1 = new android.support.design.widget.ViewUtils$ViewUtilsImplLollipop;
        r1.<init>();
        IMPL = r1;
    L_0x0016:
        r1 = new android.support.design.widget.ViewUtils$ViewUtilsImplBase;
        r1.<init>();
        IMPL = r1;
        goto L_0x0015;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.ViewUtils.<clinit>():void");
    }

    ViewUtils() {
    }

    static void setBoundsViewOutlineProvider(View view) {
        IMPL.setBoundsViewOutlineProvider(view);
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }
}
