package android.support.v4.view;

import android.view.MotionEvent;

public class MotionEventCompat {
    public static final int ACTION_HOVER_ENTER = 9;
    public static final int ACTION_HOVER_EXIT = 10;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_MASK = 255;
    public static final int ACTION_POINTER_DOWN = 5;
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_SCROLL = 8;
    public static final int AXIS_BRAKE = 23;
    public static final int AXIS_DISTANCE = 24;
    public static final int AXIS_GAS = 22;
    public static final int AXIS_GENERIC_1 = 32;
    public static final int AXIS_GENERIC_10 = 41;
    public static final int AXIS_GENERIC_11 = 42;
    public static final int AXIS_GENERIC_12 = 43;
    public static final int AXIS_GENERIC_13 = 44;
    public static final int AXIS_GENERIC_14 = 45;
    public static final int AXIS_GENERIC_15 = 46;
    public static final int AXIS_GENERIC_16 = 47;
    public static final int AXIS_GENERIC_2 = 33;
    public static final int AXIS_GENERIC_3 = 34;
    public static final int AXIS_GENERIC_4 = 35;
    public static final int AXIS_GENERIC_5 = 36;
    public static final int AXIS_GENERIC_6 = 37;
    public static final int AXIS_GENERIC_7 = 38;
    public static final int AXIS_GENERIC_8 = 39;
    public static final int AXIS_GENERIC_9 = 40;
    public static final int AXIS_HAT_X = 15;
    public static final int AXIS_HAT_Y = 16;
    public static final int AXIS_HSCROLL = 10;
    public static final int AXIS_LTRIGGER = 17;
    public static final int AXIS_ORIENTATION = 8;
    public static final int AXIS_PRESSURE = 2;
    public static final int AXIS_RTRIGGER = 18;
    public static final int AXIS_RUDDER = 20;
    public static final int AXIS_RX = 12;
    public static final int AXIS_RY = 13;
    public static final int AXIS_RZ = 14;
    public static final int AXIS_SIZE = 3;
    public static final int AXIS_THROTTLE = 19;
    public static final int AXIS_TILT = 25;
    public static final int AXIS_TOOL_MAJOR = 6;
    public static final int AXIS_TOOL_MINOR = 7;
    public static final int AXIS_TOUCH_MAJOR = 4;
    public static final int AXIS_TOUCH_MINOR = 5;
    public static final int AXIS_VSCROLL = 9;
    public static final int AXIS_WHEEL = 21;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 11;
    static final MotionEventVersionImpl IMPL = null;

    interface MotionEventVersionImpl {
        int findPointerIndex(MotionEvent motionEvent, int i);

        float getAxisValue(MotionEvent motionEvent, int i);

        float getAxisValue(MotionEvent motionEvent, int i, int i2);

        int getPointerCount(MotionEvent motionEvent);

        int getPointerId(MotionEvent motionEvent, int i);

        int getSource(MotionEvent motionEvent);

        float getX(MotionEvent motionEvent, int i);

        float getY(MotionEvent motionEvent, int i);
    }

    static class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
        BaseMotionEventVersionImpl() {
        }

        public int findPointerIndex(MotionEvent event, int pointerId) {
            if (pointerId == 0) {
                return 0;
            }
            return -1;
        }

        public int getPointerId(MotionEvent event, int pointerIndex) {
            if (pointerIndex == 0) {
                return 0;
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public float getX(MotionEvent event, int pointerIndex) {
            if (pointerIndex == 0) {
                return event.getX();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public float getY(MotionEvent event, int pointerIndex) {
            if (pointerIndex == 0) {
                return event.getY();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public int getPointerCount(MotionEvent event) {
            return 1;
        }

        public int getSource(MotionEvent event) {
            return 0;
        }

        public float getAxisValue(MotionEvent event, int axis) {
            return 0.0f;
        }

        public float getAxisValue(MotionEvent event, int axis, int pointerIndex) {
            return 0.0f;
        }
    }

    static class EclairMotionEventVersionImpl extends BaseMotionEventVersionImpl {
        EclairMotionEventVersionImpl() {
        }

        public int findPointerIndex(MotionEvent event, int pointerId) {
            return MotionEventCompatEclair.findPointerIndex(event, pointerId);
        }

        public int getPointerId(MotionEvent event, int pointerIndex) {
            return MotionEventCompatEclair.getPointerId(event, pointerIndex);
        }

        public float getX(MotionEvent event, int pointerIndex) {
            return MotionEventCompatEclair.getX(event, pointerIndex);
        }

        public float getY(MotionEvent event, int pointerIndex) {
            return MotionEventCompatEclair.getY(event, pointerIndex);
        }

        public int getPointerCount(MotionEvent event) {
            return MotionEventCompatEclair.getPointerCount(event);
        }
    }

    static class GingerbreadMotionEventVersionImpl extends EclairMotionEventVersionImpl {
        GingerbreadMotionEventVersionImpl() {
        }

        public int getSource(MotionEvent event) {
            return MotionEventCompatGingerbread.getSource(event);
        }
    }

    static class HoneycombMr1MotionEventVersionImpl extends GingerbreadMotionEventVersionImpl {
        HoneycombMr1MotionEventVersionImpl() {
        }

        public float getAxisValue(MotionEvent event, int axis) {
            return MotionEventCompatHoneycombMr1.getAxisValue(event, axis);
        }

        public float getAxisValue(MotionEvent event, int axis, int pointerIndex) {
            return MotionEventCompatHoneycombMr1.getAxisValue(event, axis, pointerIndex);
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.view.MotionEventCompat.<clinit>():void
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
        r1 = 12;
        if (r0 < r1) goto L_0x000e;
    L_0x0006:
        r0 = new android.support.v4.view.MotionEventCompat$HoneycombMr1MotionEventVersionImpl;
        r0.<init>();
        IMPL = r0;
    L_0x000e:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 9;
        if (r0 < r1) goto L_0x001c;
    L_0x0014:
        r0 = new android.support.v4.view.MotionEventCompat$GingerbreadMotionEventVersionImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000d;
    L_0x001c:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 5;
        if (r0 < r1) goto L_0x0029;
        r0 = new android.support.v4.view.MotionEventCompat$EclairMotionEventVersionImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000d;
        r0 = new android.support.v4.view.MotionEventCompat$BaseMotionEventVersionImpl;
        r0.<init>();
        IMPL = r0;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.MotionEventCompat.<clinit>():void");
    }

    public static int getActionMasked(MotionEvent event) {
        return event.getAction() & 255;
    }

    public static int getActionIndex(MotionEvent event) {
        return (event.getAction() & ACTION_POINTER_INDEX_MASK) >> 8;
    }

    public static int findPointerIndex(MotionEvent event, int pointerId) {
        return IMPL.findPointerIndex(event, pointerId);
    }

    public static int getPointerId(MotionEvent event, int pointerIndex) {
        return IMPL.getPointerId(event, pointerIndex);
    }

    public static float getX(MotionEvent event, int pointerIndex) {
        return IMPL.getX(event, pointerIndex);
    }

    public static float getY(MotionEvent event, int pointerIndex) {
        return IMPL.getY(event, pointerIndex);
    }

    public static int getPointerCount(MotionEvent event) {
        return IMPL.getPointerCount(event);
    }

    public static int getSource(MotionEvent event) {
        return IMPL.getSource(event);
    }

    public static float getAxisValue(MotionEvent event, int axis) {
        return IMPL.getAxisValue(event, axis);
    }

    public static float getAxisValue(MotionEvent event, int axis, int pointerIndex) {
        return IMPL.getAxisValue(event, axis, pointerIndex);
    }
}
