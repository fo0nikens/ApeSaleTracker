package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageButton;

import java.util.List;

@DefaultBehavior(Behavior.class)
public class FloatingActionButton extends ImageButton {
    private static final int SIZE_MINI = 1;
    private static final int SIZE_NORMAL = 0;
    private ColorStateList mBackgroundTint;
    private Mode mBackgroundTintMode;
    private int mBorderWidth;
    private int mContentPadding;
    private final FloatingActionButtonImpl mImpl;
    private int mRippleColor;
    private final Rect mShadowPadding;
    private int mSize;

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<FloatingActionButton> {
        private static final boolean SNACKBAR_BEHAVIOR_ENABLED;
        private float mFabTranslationY;
        private Rect mTmpRect;

        static {
            /* JADX: method processing error */
/*
Error: java.lang.OutOfMemoryError: Java heap space
	at java.base/java.util.Arrays.copyOf(Arrays.java:3688)
	at java.base/java.util.ArrayList.grow(ArrayList.java:236)
	at java.base/java.util.ArrayList.grow(ArrayList.java:241)
	at java.base/java.util.ArrayList.add(ArrayList.java:466)
	at java.base/java.util.ArrayList.add(ArrayList.java:479)
	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:146)
	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:94)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = android.os.Build.VERSION.SDK_INT;
            r1 = 11;
            if (r0 < r1) goto L_0x000a;
        L_0x0006:
            r0 = 1;
        L_0x0007:
            SNACKBAR_BEHAVIOR_ENABLED = r0;
        L_0x000a:
            r0 = 0;
            goto L_0x0007;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.FloatingActionButton.Behavior.<clinit>():void");
        }

        public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            return SNACKBAR_BEHAVIOR_ENABLED && (dependency instanceof SnackbarLayout);
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            if (dependency instanceof SnackbarLayout) {
                updateFabTranslationForSnackbar(parent, child, dependency);
            } else if (dependency instanceof AppBarLayout) {
                updateFabVisibility(parent, (AppBarLayout) dependency, child);
            }
            return false;
        }

        private boolean updateFabVisibility(CoordinatorLayout parent, AppBarLayout appBarLayout, FloatingActionButton child) {
            if (((LayoutParams) child.getLayoutParams()).getAnchorId() != appBarLayout.getId()) {
                return false;
            }
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                child.hide();
            } else {
                child.show();
            }
            return true;
        }

        private void updateFabTranslationForSnackbar(CoordinatorLayout parent, FloatingActionButton fab, View snackbar) {
            if (fab.getVisibility() == 0) {
                float targetTransY = getFabTranslationYForSnackbar(parent, fab);
                if (this.mFabTranslationY != targetTransY) {
                    this.mFabTranslationY = targetTransY;
                    if (Math.abs(ViewCompat.getTranslationY(fab) - targetTransY) > ((float) fab.getHeight()) * 0.667f) {
                        ViewCompat.animate(fab).translationY(targetTransY).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(null);
                        return;
                    }
                    ViewCompat.animate(fab).cancel();
                    ViewCompat.setTranslationY(fab, targetTransY);
                }
            }
        }

        private float getFabTranslationYForSnackbar(CoordinatorLayout parent, FloatingActionButton fab) {
            float minOffset = 0.0f;
            List<View> dependencies = parent.getDependencies(fab);
            int z = dependencies.size();
            for (int i = 0; i < z; i++) {
                View view = (View) dependencies.get(i);
                if ((view instanceof SnackbarLayout) && parent.doViewsOverlap(fab, view)) {
                    minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - ((float) view.getHeight()));
                }
            }
            return minOffset;
        }

        public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
            List<View> dependencies = parent.getDependencies(child);
            int count = dependencies.size();
            for (int i = 0; i < count; i++) {
                View dependency = (View) dependencies.get(i);
                if ((dependency instanceof AppBarLayout) && updateFabVisibility(parent, (AppBarLayout) dependency, child)) {
                    break;
                }
            }
            parent.onLayoutChild(child, layoutDirection);
            offsetIfNeeded(parent, child);
            return true;
        }

        private void offsetIfNeeded(CoordinatorLayout parent, FloatingActionButton fab) {
            Rect padding = fab.mShadowPadding;
            if (padding != null && padding.centerX() > 0 && padding.centerY() > 0) {
                LayoutParams lp = (LayoutParams) fab.getLayoutParams();
                int offsetTB = 0;
                int offsetLR = 0;
                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    offsetLR = -padding.left;
                }
                if (fab.getBottom() >= parent.getBottom() - lp.bottomMargin) {
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    offsetTB = -padding.top;
                }
                fab.offsetTopAndBottom(offsetTB);
                fab.offsetLeftAndRight(offsetLR);
            }
        }
    }

    public static abstract class OnVisibilityChangedListener {
        public void onShown(FloatingActionButton fab) {
        }

        public void onHidden(FloatingActionButton fab) {
        }
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ThemeUtils.checkAppCompatTheme(context);
        this.mShadowPadding = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, defStyleAttr, R.style.Widget_Design_FloatingActionButton);
        Drawable background = a.getDrawable(R.styleable.FloatingActionButton_android_background);
        this.mBackgroundTint = a.getColorStateList(R.styleable.FloatingActionButton_backgroundTint);
        this.mBackgroundTintMode = parseTintMode(a.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.mRippleColor = a.getColor(R.styleable.FloatingActionButton_rippleColor, 0);
        this.mSize = a.getInt(R.styleable.FloatingActionButton_fabSize, 0);
        this.mBorderWidth = a.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float elevation = a.getDimension(R.styleable.FloatingActionButton_elevation, 0.0f);
        float pressedTranslationZ = a.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        a.recycle();
        ShadowViewDelegate delegate = new ShadowViewDelegate() {
            public float getRadius() {
                return ((float) FloatingActionButton.this.getSizeDimension()) / 2.0f;
            }

            public void setShadowPadding(int left, int top, int right, int bottom) {
                FloatingActionButton.this.mShadowPadding.set(left, top, right, bottom);
                FloatingActionButton.this.setPadding(FloatingActionButton.this.mContentPadding + left, FloatingActionButton.this.mContentPadding + top, FloatingActionButton.this.mContentPadding + right, FloatingActionButton.this.mContentPadding + bottom);
            }

            public void setBackgroundDrawable(Drawable background) {
                super.setBackgroundDrawable(background);
            }
        };
        int sdk = VERSION.SDK_INT;
        if (sdk >= 21) {
            this.mImpl = new FloatingActionButtonLollipop(this, delegate);
        } else if (sdk >= 12) {
            this.mImpl = new FloatingActionButtonHoneycombMr1(this, delegate);
        } else {
            this.mImpl = new FloatingActionButtonEclairMr1(this, delegate);
        }
        this.mContentPadding = (getSizeDimension() - ((int) getResources().getDimension(R.dimen.design_fab_content_size))) / 2;
        this.mImpl.setBackgroundDrawable(background, this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        this.mImpl.setElevation(elevation);
        this.mImpl.setPressedTranslationZ(pressedTranslationZ);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int preferredSize = getSizeDimension();
        int d = Math.min(resolveAdjustedSize(preferredSize, widthMeasureSpec), resolveAdjustedSize(preferredSize, heightMeasureSpec));
        setMeasuredDimension((this.mShadowPadding.left + d) + this.mShadowPadding.right, (this.mShadowPadding.top + d) + this.mShadowPadding.bottom);
    }

    public void setRippleColor(@ColorInt int color) {
        if (this.mRippleColor != color) {
            this.mRippleColor = color;
            this.mImpl.setRippleColor(color);
        }
    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        if (this.mBackgroundTint != tint) {
            this.mBackgroundTint = tint;
            this.mImpl.setBackgroundTintList(tint);
        }
    }

    @Nullable
    public Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    public void setBackgroundTintMode(@Nullable Mode tintMode) {
        if (this.mBackgroundTintMode != tintMode) {
            this.mBackgroundTintMode = tintMode;
            this.mImpl.setBackgroundTintMode(tintMode);
        }
    }

    public void setBackgroundDrawable(@NonNull Drawable background) {
        if (this.mImpl != null) {
            this.mImpl.setBackgroundDrawable(background, this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        }
    }

    public void show() {
        this.mImpl.show(null);
    }

    public void show(@Nullable OnVisibilityChangedListener listener) {
        this.mImpl.show(wrapOnVisibilityChangedListener(listener));
    }

    public void hide() {
        this.mImpl.hide(null);
    }

    public void hide(@Nullable OnVisibilityChangedListener listener) {
        this.mImpl.hide(wrapOnVisibilityChangedListener(listener));
    }

    @Nullable
    private InternalVisibilityChangedListener wrapOnVisibilityChangedListener(@Nullable final OnVisibilityChangedListener listener) {
        if (listener == null) {
            return null;
        }
        return new InternalVisibilityChangedListener() {
            public void onShown() {
                listener.onShown(FloatingActionButton.this);
            }

            public void onHidden() {
                listener.onHidden(FloatingActionButton.this);
            }
        };
    }

    final int getSizeDimension() {
        switch (this.mSize) {
            case 1:
                return getResources().getDimensionPixelSize(R.dimen.design_fab_size_mini);
            default:
                return getResources().getDimensionPixelSize(R.dimen.design_fab_size_normal);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.mImpl.onDrawableStateChanged(getDrawableState());
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.mImpl.jumpDrawableToCurrentState();
    }

    private static int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
                return Math.min(desiredSize, specSize);
            case 0:
                return desiredSize;
            case 1073741824:
                return specSize;
            default:
                return result;
        }
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
