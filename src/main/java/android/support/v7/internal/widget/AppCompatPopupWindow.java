package android.support.v7.internal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AppCompatPopupWindow extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR;
    private static final String TAG = "AppCompatPopupWindow";
    private boolean mOverlapAnchor;

    static {
        /* JADX: method processing error */
/*
Error: java.lang.OutOfMemoryError: Java heap space
*/
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 21;
        if (r0 >= r1) goto L_0x000a;
    L_0x0006:
        r0 = 1;
    L_0x0007:
        COMPAT_OVERLAP_ANCHOR = r0;
    L_0x000a:
        r0 = 0;
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.internal.widget.AppCompatPopupWindow.<clinit>():void");
    }

    public AppCompatPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.PopupWindow, defStyleAttr, 0);
        if (a.hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            setSupportOverlapAnchor(a.getBoolean(R.styleable.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable(a.getDrawable(R.styleable.PopupWindow_android_popupBackground));
        a.recycle();
        if (VERSION.SDK_INT < 14) {
            wrapOnScrollChangedListener(this);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            yoff -= anchor.getHeight();
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @TargetApi(19)
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            yoff -= anchor.getHeight();
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void update(View anchor, int xoff, int yoff, int width, int height) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            yoff -= anchor.getHeight();
        }
        super.update(anchor, xoff, yoff, width, height);
    }

    private static void wrapOnScrollChangedListener(final PopupWindow popup) {
        try {
            final Field fieldAnchor = PopupWindow.class.getDeclaredField("mAnchor");
            fieldAnchor.setAccessible(true);
            Field fieldListener = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            fieldListener.setAccessible(true);
            final OnScrollChangedListener originalListener = (OnScrollChangedListener) fieldListener.get(popup);
            fieldListener.set(popup, new OnScrollChangedListener() {
                public void onScrollChanged() {
                    try {
                        WeakReference<View> mAnchor = (WeakReference) fieldAnchor.get(popup);
                        if (mAnchor != null && mAnchor.get() != null) {
                            originalListener.onScrollChanged();
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Exception while installing workaround OnScrollChangedListener", e);
        }
    }

    public void setSupportOverlapAnchor(boolean overlapAnchor) {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = overlapAnchor;
        } else {
            PopupWindowCompat.setOverlapAnchor(this, overlapAnchor);
        }
    }

    public boolean getSupportOverlapAnchor() {
        if (COMPAT_OVERLAP_ANCHOR) {
            return this.mOverlapAnchor;
        }
        return PopupWindowCompat.getOverlapAnchor(this);
    }
}
