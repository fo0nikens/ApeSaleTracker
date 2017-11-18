package com.android.ex.editstyledtext;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.NoCopySpan.Concrete;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.ArrowKeyMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.AlignmentSpan.Standard;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EditStyledText extends EditText {
    private static final boolean DBG = true;
    public static final int DEFAULT_FOREGROUND_COLOR = -16777216;
    public static final int DEFAULT_TRANSPARENT_COLOR = 16777215;
    public static final int HINT_MSG_BIG_SIZE_ERROR = 5;
    public static final int HINT_MSG_COPY_BUF_BLANK = 1;
    public static final int HINT_MSG_END_COMPOSE = 7;
    public static final int HINT_MSG_END_PREVIEW = 6;
    public static final int HINT_MSG_NULL = 0;
    public static final int HINT_MSG_PUSH_COMPETE = 4;
    public static final int HINT_MSG_SELECT_END = 3;
    public static final int HINT_MSG_SELECT_START = 2;
    private static final int ID_CLEARSTYLES = 16776962;
    private static final int ID_COPY = 16908321;
    private static final int ID_CUT = 16908320;
    private static final int ID_HIDEEDIT = 16776964;
    private static final int ID_HORIZONTALLINE = 16776961;
    private static final int ID_PASTE = 16908322;
    private static final int ID_SELECT_ALL = 16908319;
    private static final int ID_SHOWEDIT = 16776963;
    private static final int ID_START_SELECTING_TEXT = 16908328;
    private static final int ID_STOP_SELECTING_TEXT = 16908329;
    public static final char IMAGECHAR = '￼';
    private static final int MAXIMAGEWIDTHDIP = 300;
    public static final int MODE_ALIGN = 6;
    public static final int MODE_BGCOLOR = 16;
    public static final int MODE_CANCEL = 18;
    public static final int MODE_CLEARSTYLES = 14;
    public static final int MODE_COLOR = 4;
    public static final int MODE_COPY = 1;
    public static final int MODE_CUT = 7;
    public static final int MODE_END_EDIT = 21;
    public static final int MODE_HORIZONTALLINE = 12;
    public static final int MODE_IMAGE = 15;
    public static final int MODE_MARQUEE = 10;
    public static final int MODE_NOTHING = 0;
    public static final int MODE_PASTE = 2;
    public static final int MODE_PREVIEW = 17;
    public static final int MODE_RESET = 22;
    public static final int MODE_SELECT = 5;
    public static final int MODE_SELECTALL = 11;
    public static final int MODE_SHOW_MENU = 23;
    public static final int MODE_SIZE = 3;
    public static final int MODE_START_EDIT = 20;
    public static final int MODE_STOP_SELECT = 13;
    public static final int MODE_SWING = 9;
    public static final int MODE_TELOP = 8;
    public static final int MODE_TEXTVIEWFUNCTION = 19;
    private static final int PRESSED = 16777233;
    private static final Concrete SELECTING = new Concrete();
    public static final int STATE_SELECTED = 2;
    public static final int STATE_SELECT_FIX = 3;
    public static final int STATE_SELECT_OFF = 0;
    public static final int STATE_SELECT_ON = 1;
    private static CharSequence STR_CLEARSTYLES = null;
    private static CharSequence STR_HORIZONTALLINE = null;
    private static CharSequence STR_PASTE = null;
    private static final String TAG = "EditStyledText";
    public static final char ZEROWIDTHCHAR = '⁠';
    private StyledTextConverter mConverter;
    private Drawable mDefaultBackground;
    private StyledTextDialog mDialog;
    private ArrayList<EditStyledTextNotifier> mESTNotifiers;
    private InputConnection mInputConnection;
    private EditorManager mManager;
    private float mPaddingScale = 0.0f;

    public static class ColorPaletteDrawable extends ShapeDrawable {
        private Rect mRect;

        public ColorPaletteDrawable(int color, int width, int height, int mergin) {
            super(new RectShape());
            this.mRect = new Rect(mergin, mergin, width - mergin, height - mergin);
            getPaint().setColor(color);
        }

        public void draw(Canvas canvas) {
            canvas.drawRect(this.mRect, getPaint());
        }
    }

    public class EditModeActions {
        private static final boolean DBG = true;
        private static final String TAG = "EditModeActions";
        private HashMap<Integer, EditModeActionBase> mActionMap = new HashMap();
        private AlignAction mAlignAction = new AlignAction();
        private BackgroundColorAction mBackgroundColorAction = new BackgroundColorAction();
        private CancelAction mCancelEditAction = new CancelAction();
        private ClearStylesAction mClearStylesAction = new ClearStylesAction();
        private ColorAction mColorAction = new ColorAction();
        private CopyAction mCopyAction = new CopyAction();
        private CutAction mCutAction = new CutAction();
        private StyledTextDialog mDialog;
        private EditStyledText mEST;
        private EndEditAction mEndEditAction = new EndEditAction();
        private HorizontalLineAction mHorizontalLineAction = new HorizontalLineAction();
        private ImageAction mImageAction = new ImageAction();
        private EditorManager mManager;
        private MarqueeDialogAction mMarqueeDialogAction = new MarqueeDialogAction();
        private int mMode = 0;
        private NothingAction mNothingAction = new NothingAction();
        private PasteAction mPasteAction = new PasteAction();
        private PreviewAction mPreviewAction = new PreviewAction();
        private ResetAction mResetAction = new ResetAction();
        private SelectAction mSelectAction = new SelectAction();
        private SelectAllAction mSelectAllAction = new SelectAllAction();
        private ShowMenuAction mShowMenuAction = new ShowMenuAction();
        private SizeAction mSizeAction = new SizeAction();
        private StartEditAction mStartEditAction = new StartEditAction();
        private StopSelectionAction mStopSelectionAction = new StopSelectionAction();
        private SwingAction mSwingAction = new SwingAction();
        private TelopAction mTelopAction = new TelopAction();
        private TextViewAction mTextViewAction = new TextViewAction();

        public class EditModeActionBase {
            private Object[] mParams;

            protected boolean canOverWrap() {
                return false;
            }

            protected boolean canSelect() {
                return false;
            }

            protected boolean canWaitInput() {
                return false;
            }

            protected boolean needSelection() {
                return false;
            }

            protected boolean isLine() {
                return false;
            }

            protected boolean doNotSelected() {
                return false;
            }

            protected boolean doStartPosIsSelected() {
                return doNotSelected();
            }

            protected boolean doEndPosIsSelected() {
                return doStartPosIsSelected();
            }

            protected boolean doSelectionIsFixed() {
                return doEndPosIsSelected();
            }

            protected boolean doSelectionIsFixedAndWaitingInput() {
                return doEndPosIsSelected();
            }

            protected boolean fixSelection() {
                EditModeActions.this.mEST.finishComposingText();
                EditModeActions.this.mManager.setSelectState(3);
                return EditModeActions.DBG;
            }

            protected void addParams(Object[] o) {
                this.mParams = o;
            }

            protected Object getParam(int num) {
                if (this.mParams != null && num <= this.mParams.length) {
                    return this.mParams[num];
                }
                Log.d(EditModeActions.TAG, "--- Number of the parameter is out of bound.");
                return null;
            }
        }

        public class SetSpanActionBase extends EditModeActionBase {
            public SetSpanActionBase() {
                super();
            }

            protected boolean doNotSelected() {
                if (EditModeActions.this.mManager.getEditMode() == 0 || EditModeActions.this.mManager.getEditMode() == 5) {
                    EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                    EditModeActions.this.mManager.setInternalSelection(EditModeActions.this.mEST.getSelectionStart(), EditModeActions.this.mEST.getSelectionEnd());
                    fixSelection();
                    EditModeActions.this.doNext();
                    return EditModeActions.DBG;
                } else if (EditModeActions.this.mManager.getEditMode() == EditModeActions.this.mMode) {
                    return false;
                } else {
                    Log.d(EditModeActions.TAG, "--- setspanactionbase" + EditModeActions.this.mManager.getEditMode() + "," + EditModeActions.this.mMode);
                    if (EditModeActions.this.mManager.isWaitInput()) {
                        EditModeActions.this.mManager.setEditMode(0);
                        EditModeActions.this.mManager.setSelectState(0);
                    } else {
                        EditModeActions.this.mManager.resetEdit();
                        EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                    }
                    EditModeActions.this.doNext();
                    return EditModeActions.DBG;
                }
            }

            protected boolean doStartPosIsSelected() {
                if (EditModeActions.this.mManager.getEditMode() != 0 && EditModeActions.this.mManager.getEditMode() != 5) {
                    return doNotSelected();
                }
                EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                EditModeActions.this.onSelectAction();
                return EditModeActions.DBG;
            }

            protected boolean doEndPosIsSelected() {
                if (EditModeActions.this.mManager.getEditMode() != 0 && EditModeActions.this.mManager.getEditMode() != 5) {
                    return doStartPosIsSelected();
                }
                EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                fixSelection();
                EditModeActions.this.doNext();
                return EditModeActions.DBG;
            }

            protected boolean doSelectionIsFixed() {
                if (doEndPosIsSelected()) {
                    return EditModeActions.DBG;
                }
                EditModeActions.this.mEST.sendHintMessage(0);
                return false;
            }
        }

        public class AlignAction extends SetSpanActionBase {
            public AlignAction() {
                super();
            }

            protected boolean doSelectionIsFixed() {
                if (!super.doSelectionIsFixed()) {
                    EditModeActions.this.mDialog.onShowAlignAlertDialog();
                }
                return EditModeActions.DBG;
            }
        }

        public class BackgroundColorAction extends EditModeActionBase {
            public BackgroundColorAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mDialog.onShowBackgroundColorAlertDialog();
                return EditModeActions.DBG;
            }
        }

        public class CancelAction extends EditModeActionBase {
            public CancelAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mEST.cancelViewManagers();
                return EditModeActions.DBG;
            }
        }

        public class ClearStylesAction extends EditModeActionBase {
            public ClearStylesAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.clearStyles();
                return EditModeActions.DBG;
            }
        }

        public class ColorAction extends SetSpanActionBase {
            public ColorAction() {
                super();
            }

            protected boolean doSelectionIsFixed() {
                if (!super.doSelectionIsFixed()) {
                    EditModeActions.this.mDialog.onShowForegroundColorAlertDialog();
                }
                return EditModeActions.DBG;
            }

            protected boolean doSelectionIsFixedAndWaitingInput() {
                if (!super.doSelectionIsFixedAndWaitingInput()) {
                    int size = EditModeActions.this.mManager.getSizeWaitInput();
                    EditModeActions.this.mManager.setItemColor(EditModeActions.this.mManager.getColorWaitInput(), false);
                    if (EditModeActions.this.mManager.isWaitInput()) {
                        fixSelection();
                        EditModeActions.this.mDialog.onShowForegroundColorAlertDialog();
                    } else {
                        EditModeActions.this.mManager.setItemSize(size, false);
                        EditModeActions.this.mManager.resetEdit();
                    }
                }
                return EditModeActions.DBG;
            }
        }

        public class TextViewActionBase extends EditModeActionBase {
            public TextViewActionBase() {
                super();
            }

            protected boolean doNotSelected() {
                if (EditModeActions.this.mManager.getEditMode() != 0 && EditModeActions.this.mManager.getEditMode() != 5) {
                    return false;
                }
                EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                EditModeActions.this.onSelectAction();
                return EditModeActions.DBG;
            }

            protected boolean doEndPosIsSelected() {
                if (EditModeActions.this.mManager.getEditMode() == 0 || EditModeActions.this.mManager.getEditMode() == 5) {
                    EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                    fixSelection();
                    EditModeActions.this.doNext();
                    return EditModeActions.DBG;
                } else if (EditModeActions.this.mManager.getEditMode() == EditModeActions.this.mMode) {
                    return false;
                } else {
                    EditModeActions.this.mManager.resetEdit();
                    EditModeActions.this.mManager.setEditMode(EditModeActions.this.mMode);
                    EditModeActions.this.doNext();
                    return EditModeActions.DBG;
                }
            }
        }

        public class CopyAction extends TextViewActionBase {
            public CopyAction() {
                super();
            }

            protected boolean doEndPosIsSelected() {
                if (!super.doEndPosIsSelected()) {
                    EditModeActions.this.mManager.copyToClipBoard();
                    EditModeActions.this.mManager.resetEdit();
                }
                return EditModeActions.DBG;
            }
        }

        public class CutAction extends TextViewActionBase {
            public CutAction() {
                super();
            }

            protected boolean doEndPosIsSelected() {
                if (!super.doEndPosIsSelected()) {
                    EditModeActions.this.mManager.cutToClipBoard();
                    EditModeActions.this.mManager.resetEdit();
                }
                return EditModeActions.DBG;
            }
        }

        public class EndEditAction extends EditModeActionBase {
            public EndEditAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.endEdit();
                return EditModeActions.DBG;
            }
        }

        public class HorizontalLineAction extends EditModeActionBase {
            public HorizontalLineAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.insertHorizontalLine();
                return EditModeActions.DBG;
            }
        }

        public class ImageAction extends EditModeActionBase {
            public ImageAction() {
                super();
            }

            protected boolean doNotSelected() {
                Object param = getParam(0);
                if (param == null) {
                    EditModeActions.this.mEST.showInsertImageSelectAlertDialog();
                } else if (param instanceof Uri) {
                    EditModeActions.this.mManager.insertImageFromUri((Uri) param);
                } else if (param instanceof Integer) {
                    EditModeActions.this.mManager.insertImageFromResId(((Integer) param).intValue());
                }
                return EditModeActions.DBG;
            }
        }

        public class MarqueeDialogAction extends SetSpanActionBase {
            public MarqueeDialogAction() {
                super();
            }

            protected boolean doSelectionIsFixed() {
                if (!super.doSelectionIsFixed()) {
                    EditModeActions.this.mDialog.onShowMarqueeAlertDialog();
                }
                return EditModeActions.DBG;
            }
        }

        public class NothingAction extends EditModeActionBase {
            public NothingAction() {
                super();
            }
        }

        public class PasteAction extends EditModeActionBase {
            public PasteAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.pasteFromClipboard();
                EditModeActions.this.mManager.resetEdit();
                return EditModeActions.DBG;
            }
        }

        public class PreviewAction extends EditModeActionBase {
            public PreviewAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mEST.showPreview();
                return EditModeActions.DBG;
            }
        }

        public class ResetAction extends EditModeActionBase {
            public ResetAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.resetEdit();
                return EditModeActions.DBG;
            }
        }

        public class SelectAction extends EditModeActionBase {
            public SelectAction() {
                super();
            }

            protected boolean doNotSelected() {
                if (EditModeActions.this.mManager.isTextSelected()) {
                    Log.e(EditModeActions.TAG, "Selection is off, but selected");
                }
                EditModeActions.this.mManager.setSelectStartPos();
                EditModeActions.this.mEST.sendHintMessage(3);
                return EditModeActions.DBG;
            }

            protected boolean doStartPosIsSelected() {
                if (EditModeActions.this.mManager.isTextSelected()) {
                    Log.e(EditModeActions.TAG, "Selection now start, but selected");
                }
                EditModeActions.this.mManager.setSelectEndPos();
                EditModeActions.this.mEST.sendHintMessage(4);
                if (EditModeActions.this.mManager.getEditMode() != 5) {
                    EditModeActions.this.doNext(EditModeActions.this.mManager.getEditMode());
                }
                return EditModeActions.DBG;
            }

            protected boolean doSelectionIsFixed() {
                return false;
            }
        }

        public class SelectAllAction extends EditModeActionBase {
            public SelectAllAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.selectAll();
                return EditModeActions.DBG;
            }
        }

        public class ShowMenuAction extends EditModeActionBase {
            public ShowMenuAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mEST.showMenuAlertDialog();
                return EditModeActions.DBG;
            }
        }

        public class SizeAction extends SetSpanActionBase {
            public SizeAction() {
                super();
            }

            protected boolean doSelectionIsFixed() {
                if (!super.doSelectionIsFixed()) {
                    EditModeActions.this.mDialog.onShowSizeAlertDialog();
                }
                return EditModeActions.DBG;
            }

            protected boolean doSelectionIsFixedAndWaitingInput() {
                if (!super.doSelectionIsFixedAndWaitingInput()) {
                    int color = EditModeActions.this.mManager.getColorWaitInput();
                    EditModeActions.this.mManager.setItemSize(EditModeActions.this.mManager.getSizeWaitInput(), false);
                    if (EditModeActions.this.mManager.isWaitInput()) {
                        fixSelection();
                        EditModeActions.this.mDialog.onShowSizeAlertDialog();
                    } else {
                        EditModeActions.this.mManager.setItemColor(color, false);
                        EditModeActions.this.mManager.resetEdit();
                    }
                }
                return EditModeActions.DBG;
            }
        }

        public class StartEditAction extends EditModeActionBase {
            public StartEditAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.startEdit();
                return EditModeActions.DBG;
            }
        }

        public class StopSelectionAction extends EditModeActionBase {
            public StopSelectionAction() {
                super();
            }

            protected boolean doNotSelected() {
                EditModeActions.this.mManager.fixSelectionAndDoNextAction();
                return EditModeActions.DBG;
            }
        }

        public class SwingAction extends SetSpanActionBase {
            public SwingAction() {
                super();
            }

            protected boolean doSelectionIsFixed() {
                if (!super.doSelectionIsFixed()) {
                    EditModeActions.this.mManager.setSwing();
                }
                return EditModeActions.DBG;
            }
        }

        public class TelopAction extends SetSpanActionBase {
            public TelopAction() {
                super();
            }

            protected boolean doSelectionIsFixed() {
                if (!super.doSelectionIsFixed()) {
                    EditModeActions.this.mManager.setTelop();
                }
                return EditModeActions.DBG;
            }
        }

        public class TextViewAction extends TextViewActionBase {
            public TextViewAction() {
                super();
            }

            protected boolean doEndPosIsSelected() {
                if (!super.doEndPosIsSelected()) {
                    Object param = getParam(0);
                    if (param != null && (param instanceof Integer)) {
                        EditModeActions.this.mEST.onTextContextMenuItem(((Integer) param).intValue());
                    }
                    EditModeActions.this.mManager.resetEdit();
                }
                return EditModeActions.DBG;
            }
        }

        EditModeActions(EditStyledText est, EditorManager manager, StyledTextDialog dialog) {
            this.mEST = est;
            this.mManager = manager;
            this.mDialog = dialog;
            this.mActionMap.put(Integer.valueOf(0), this.mNothingAction);
            this.mActionMap.put(Integer.valueOf(1), this.mCopyAction);
            this.mActionMap.put(Integer.valueOf(2), this.mPasteAction);
            this.mActionMap.put(Integer.valueOf(5), this.mSelectAction);
            this.mActionMap.put(Integer.valueOf(7), this.mCutAction);
            this.mActionMap.put(Integer.valueOf(11), this.mSelectAllAction);
            this.mActionMap.put(Integer.valueOf(12), this.mHorizontalLineAction);
            this.mActionMap.put(Integer.valueOf(13), this.mStopSelectionAction);
            this.mActionMap.put(Integer.valueOf(14), this.mClearStylesAction);
            this.mActionMap.put(Integer.valueOf(15), this.mImageAction);
            this.mActionMap.put(Integer.valueOf(16), this.mBackgroundColorAction);
            this.mActionMap.put(Integer.valueOf(17), this.mPreviewAction);
            this.mActionMap.put(Integer.valueOf(18), this.mCancelEditAction);
            this.mActionMap.put(Integer.valueOf(19), this.mTextViewAction);
            this.mActionMap.put(Integer.valueOf(20), this.mStartEditAction);
            this.mActionMap.put(Integer.valueOf(21), this.mEndEditAction);
            this.mActionMap.put(Integer.valueOf(22), this.mResetAction);
            this.mActionMap.put(Integer.valueOf(23), this.mShowMenuAction);
            this.mActionMap.put(Integer.valueOf(6), this.mAlignAction);
            this.mActionMap.put(Integer.valueOf(8), this.mTelopAction);
            this.mActionMap.put(Integer.valueOf(9), this.mSwingAction);
            this.mActionMap.put(Integer.valueOf(10), this.mMarqueeDialogAction);
            this.mActionMap.put(Integer.valueOf(4), this.mColorAction);
            this.mActionMap.put(Integer.valueOf(3), this.mSizeAction);
        }

        public void addAction(int modeId, EditModeActionBase action) {
            this.mActionMap.put(Integer.valueOf(modeId), action);
        }

        public void onAction(int newMode, Object[] params) {
            getAction(newMode).addParams(params);
            this.mMode = newMode;
            doNext(newMode);
        }

        public void onAction(int newMode, Object param) {
            onAction(newMode, new Object[]{param});
        }

        public void onAction(int newMode) {
            onAction(newMode, null);
        }

        public void onSelectAction() {
            doNext(5);
        }

        private EditModeActionBase getAction(int mode) {
            if (this.mActionMap.containsKey(Integer.valueOf(mode))) {
                return (EditModeActionBase) this.mActionMap.get(Integer.valueOf(mode));
            }
            return null;
        }

        public boolean doNext() {
            return doNext(this.mMode);
        }

        public boolean doNext(int mode) {
            Log.d(TAG, "--- do the next action: " + mode + "," + this.mManager.getSelectState());
            EditModeActionBase action = getAction(mode);
            if (action == null) {
                Log.e(TAG, "--- invalid action error.");
                return false;
            }
            switch (this.mManager.getSelectState()) {
                case 0:
                    return action.doNotSelected();
                case 1:
                    return action.doStartPosIsSelected();
                case 2:
                    return action.doEndPosIsSelected();
                case 3:
                    if (this.mManager.isWaitInput()) {
                        return action.doSelectionIsFixedAndWaitingInput();
                    }
                    return action.doSelectionIsFixed();
                default:
                    return false;
            }
        }
    }

    public interface EditStyledTextNotifier {
        void cancelViewManager();

        boolean isButtonsFocused();

        void onStateChanged(int i, int i2);

        void sendHintMsg(int i);

        boolean sendOnTouchEvent(MotionEvent motionEvent);

        boolean showInsertImageSelectAlertDialog();

        boolean showMenuAlertDialog();

        boolean showPreview();
    }

    public static class EditStyledTextSpans {
        private static final String LOG_TAG = "EditStyledTextSpan";

        public static class HorizontalLineDrawable extends ShapeDrawable {
            private static boolean DBG_HL = false;
            private Spannable mSpannable;
            private int mWidth;

            public HorizontalLineDrawable(int color, int width, Spannable spannable) {
                super(new RectShape());
                this.mSpannable = spannable;
                this.mWidth = width;
                renewColor(color);
                renewBounds(width);
            }

            public void draw(Canvas canvas) {
                renewColor();
                canvas.drawRect(new Rect(0, 9, this.mWidth, 11), getPaint());
            }

            public void renewBounds(int width) {
                if (DBG_HL) {
                    Log.d(EditStyledTextSpans.LOG_TAG, "--- renewBounds:" + width);
                }
                if (width > 20) {
                    width -= 20;
                }
                this.mWidth = width;
                setBounds(0, 0, width, 20);
            }

            private void renewColor(int color) {
                if (DBG_HL) {
                    Log.d(EditStyledTextSpans.LOG_TAG, "--- renewColor:" + color);
                }
                getPaint().setColor(color);
            }

            private void renewColor() {
                HorizontalLineSpan parent = getParentSpan();
                Spannable text = this.mSpannable;
                ForegroundColorSpan[] spans = (ForegroundColorSpan[]) text.getSpans(text.getSpanStart(parent), text.getSpanEnd(parent), ForegroundColorSpan.class);
                if (DBG_HL) {
                    Log.d(EditStyledTextSpans.LOG_TAG, "--- renewColor:" + spans.length);
                }
                if (spans.length > 0) {
                    renewColor(spans[spans.length - 1].getForegroundColor());
                }
            }

            private HorizontalLineSpan getParentSpan() {
                Spannable text = this.mSpannable;
                HorizontalLineSpan[] images = (HorizontalLineSpan[]) text.getSpans(0, text.length(), HorizontalLineSpan.class);
                if (images.length > 0) {
                    for (HorizontalLineSpan image : images) {
                        if (image.getDrawable() == this) {
                            return image;
                        }
                    }
                }
                Log.e(EditStyledTextSpans.LOG_TAG, "---renewBounds: Couldn't find");
                return null;
            }
        }

        public static class HorizontalLineSpan extends DynamicDrawableSpan {
            HorizontalLineDrawable mDrawable;

            public HorizontalLineSpan(int color, int width, Spannable spannable) {
                super(0);
                this.mDrawable = new HorizontalLineDrawable(color, width, spannable);
            }

            public Drawable getDrawable() {
                return this.mDrawable;
            }

            public void resetWidth(int width) {
                this.mDrawable.renewBounds(width);
            }

            public int getColor() {
                return this.mDrawable.getPaint().getColor();
            }
        }

        public static class MarqueeSpan extends CharacterStyle {
            public static final int ALTERNATE = 1;
            public static final int NOTHING = 2;
            public static final int SCROLL = 0;
            private int mMarqueeColor;
            private int mType;

            public MarqueeSpan(int type, int bgc) {
                this.mType = type;
                checkType(type);
                this.mMarqueeColor = getMarqueeColor(type, bgc);
            }

            public MarqueeSpan(int type) {
                this(type, 16777215);
            }

            public int getType() {
                return this.mType;
            }

            public void resetColor(int bgc) {
                this.mMarqueeColor = getMarqueeColor(this.mType, bgc);
            }

            private int getMarqueeColor(int type, int bgc) {
                int a = Color.alpha(bgc);
                int r = Color.red(bgc);
                int g = Color.green(bgc);
                int b = Color.blue(bgc);
                if (a == 0) {
                    a = 128;
                }
                switch (type) {
                    case 0:
                        if (r <= 128) {
                            r = (255 - r) / 2;
                            break;
                        }
                        r /= 2;
                        break;
                    case 1:
                        if (g <= 128) {
                            g = (255 - g) / 2;
                            break;
                        }
                        g /= 2;
                        break;
                    case 2:
                        return 16777215;
                    default:
                        Log.e(EditStyledText.TAG, "--- getMarqueeColor: got illigal marquee ID.");
                        return 16777215;
                }
                return Color.argb(a, r, g, b);
            }

            private boolean checkType(int type) {
                if (type == 0 || type == 1) {
                    return EditStyledText.DBG;
                }
                Log.e(EditStyledTextSpans.LOG_TAG, "--- Invalid type of MarqueeSpan");
                return false;
            }

            public void updateDrawState(TextPaint tp) {
                tp.bgColor = this.mMarqueeColor;
            }
        }

        public static class RescalableImageSpan extends ImageSpan {
            private final int MAXWIDTH;
            Uri mContentUri;
            private Context mContext;
            private Drawable mDrawable;
            public int mIntrinsicHeight = -1;
            public int mIntrinsicWidth = -1;

            public RescalableImageSpan(Context context, Uri uri, int maxwidth) {
                super(context, uri);
                this.mContext = context;
                this.mContentUri = uri;
                this.MAXWIDTH = maxwidth;
            }

            public RescalableImageSpan(Context context, int resourceId, int maxwidth) {
                super(context, resourceId);
                this.mContext = context;
                this.MAXWIDTH = maxwidth;
            }

            public Drawable getDrawable() {
                if (this.mDrawable != null) {
                    return this.mDrawable;
                }
                if (this.mContentUri != null) {
                    System.gc();
                    try {
                        Bitmap bitmap;
                        InputStream is = this.mContext.getContentResolver().openInputStream(this.mContentUri);
                        Options opt = new Options();
                        opt.inJustDecodeBounds = EditStyledText.DBG;
                        BitmapFactory.decodeStream(is, null, opt);
                        is.close();
                        is = this.mContext.getContentResolver().openInputStream(this.mContentUri);
                        int width = opt.outWidth;
                        int height = opt.outHeight;
                        this.mIntrinsicWidth = width;
                        this.mIntrinsicHeight = height;
                        if (opt.outWidth > this.MAXWIDTH) {
                            width = this.MAXWIDTH;
                            height = (this.MAXWIDTH * height) / opt.outWidth;
                            bitmap = BitmapFactory.decodeStream(is, new Rect(0, 0, width, height), null);
                        } else {
                            bitmap = BitmapFactory.decodeStream(is);
                        }
                        this.mDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
                        this.mDrawable.setBounds(0, 0, width, height);
                        is.close();
                    } catch (Exception e) {
                        Log.e(EditStyledTextSpans.LOG_TAG, "Failed to loaded content " + this.mContentUri, e);
                        return null;
                    } catch (OutOfMemoryError e2) {
                        Log.e(EditStyledTextSpans.LOG_TAG, "OutOfMemoryError");
                        return null;
                    }
                }
                this.mDrawable = super.getDrawable();
                rescaleBigImage(this.mDrawable);
                this.mIntrinsicWidth = this.mDrawable.getIntrinsicWidth();
                this.mIntrinsicHeight = this.mDrawable.getIntrinsicHeight();
                return this.mDrawable;
            }

            public boolean isOverSize() {
                return getDrawable().getIntrinsicWidth() > this.MAXWIDTH ? EditStyledText.DBG : false;
            }

            public Uri getContentUri() {
                return this.mContentUri;
            }

            private void rescaleBigImage(Drawable image) {
                Log.d(EditStyledTextSpans.LOG_TAG, "--- rescaleBigImage:");
                if (this.MAXWIDTH >= 0) {
                    int image_width = image.getIntrinsicWidth();
                    int image_height = image.getIntrinsicHeight();
                    Log.d(EditStyledTextSpans.LOG_TAG, "--- rescaleBigImage:" + image_width + "," + image_height + "," + this.MAXWIDTH);
                    if (image_width > this.MAXWIDTH) {
                        image_width = this.MAXWIDTH;
                        image_height = (this.MAXWIDTH * image_height) / image_width;
                    }
                    image.setBounds(0, 0, image_width, image_height);
                }
            }
        }
    }

    private class EditorManager {
        private static final String LOG_TAG = "EditStyledText.EditorManager";
        private EditModeActions mActions;
        private int mBackgroundColor = 16777215;
        private int mColorWaitInput = 16777215;
        private BackgroundColorSpan mComposingTextMask;
        private SpannableStringBuilder mCopyBuffer;
        private int mCurEnd = 0;
        private int mCurStart = 0;
        private EditStyledText mEST;
        private boolean mEditFlag = false;
        private boolean mKeepNonLineSpan = false;
        private int mMode = 0;
        private int mSizeWaitInput = 0;
        private SoftKeyReceiver mSkr;
        private boolean mSoftKeyBlockFlag = false;
        private int mState = 0;
        private boolean mTextIsFinishedFlag = false;
        private boolean mWaitInputFlag = false;

        EditorManager(EditStyledText est, StyledTextDialog dialog) {
            this.mEST = est;
            this.mActions = new EditModeActions(this.mEST, this, dialog);
            this.mSkr = new SoftKeyReceiver(this.mEST);
        }

        public void addAction(int mode, EditModeActionBase action) {
            this.mActions.addAction(mode, action);
        }

        public void onAction(int mode) {
            onAction(mode, EditStyledText.DBG);
        }

        public void onAction(int mode, boolean notifyStateChanged) {
            this.mActions.onAction(mode);
            if (notifyStateChanged) {
                this.mEST.notifyStateChanged(this.mMode, this.mState);
            }
        }

        private void startEdit() {
            resetEdit();
            showSoftKey();
        }

        public void onStartSelect(boolean notifyStateChanged) {
            Log.d(LOG_TAG, "--- onClickSelect");
            this.mMode = 5;
            if (this.mState == 0) {
                this.mActions.onSelectAction();
            } else {
                unsetSelect();
                this.mActions.onSelectAction();
            }
            if (notifyStateChanged) {
                this.mEST.notifyStateChanged(this.mMode, this.mState);
            }
        }

        public void onCursorMoved() {
            Log.d(LOG_TAG, "--- onClickView");
            if (this.mState == 1 || this.mState == 2) {
                this.mActions.onSelectAction();
                this.mEST.notifyStateChanged(this.mMode, this.mState);
            }
        }

        public void onStartSelectAll(boolean notifyStateChanged) {
            Log.d(LOG_TAG, "--- onClickSelectAll");
            handleSelectAll();
            if (notifyStateChanged) {
                this.mEST.notifyStateChanged(this.mMode, this.mState);
            }
        }

        public void onStartShowMenuAlertDialog() {
            this.mActions.onAction(23);
        }

        public void onFixSelectedItem() {
            Log.d(LOG_TAG, "--- onFixSelectedItem");
            fixSelectionAndDoNextAction();
            this.mEST.notifyStateChanged(this.mMode, this.mState);
        }

        public void onInsertImage(Uri uri) {
            this.mActions.onAction(15, (Object) uri);
            this.mEST.notifyStateChanged(this.mMode, this.mState);
        }

        public void onInsertImage(int resId) {
            this.mActions.onAction(15, Integer.valueOf(resId));
            this.mEST.notifyStateChanged(this.mMode, this.mState);
        }

        private void insertImageFromUri(Uri uri) {
            insertImageSpan(new RescalableImageSpan(this.mEST.getContext(), uri, this.mEST.getMaxImageWidthPx()), this.mEST.getSelectionStart());
        }

        private void insertImageFromResId(int resId) {
            insertImageSpan(new RescalableImageSpan(this.mEST.getContext(), resId, this.mEST.getMaxImageWidthDip()), this.mEST.getSelectionStart());
        }

        private void insertHorizontalLine() {
            int curpos;
            Log.d(LOG_TAG, "--- onInsertHorizontalLine:");
            int curpos2 = this.mEST.getSelectionStart();
            if (curpos2 > 0 && this.mEST.getText().charAt(curpos2 - 1) != '\n') {
                curpos = curpos2 + 1;
                this.mEST.getText().insert(curpos2, "\n");
                curpos2 = curpos;
            }
            curpos = curpos2 + 1;
            insertImageSpan(new HorizontalLineSpan(-16777216, this.mEST.getWidth(), this.mEST.getText()), curpos2);
            curpos2 = curpos + 1;
            this.mEST.getText().insert(curpos, "\n");
            this.mEST.setSelection(curpos2);
            this.mEST.notifyStateChanged(this.mMode, this.mState);
        }

        private void clearStyles(CharSequence txt) {
            Log.d(EditStyledText.TAG, "--- onClearStyles");
            int len = txt.length();
            if (txt instanceof Editable) {
                Editable editable = (Editable) txt;
                for (Object style : editable.getSpans(0, len, Object.class)) {
                    if ((style instanceof ParagraphStyle) || (style instanceof QuoteSpan) || ((style instanceof CharacterStyle) && !(style instanceof UnderlineSpan))) {
                        if ((style instanceof ImageSpan) || (style instanceof HorizontalLineSpan)) {
                            editable.replace(editable.getSpanStart(style), editable.getSpanEnd(style), "");
                        }
                        editable.removeSpan(style);
                    }
                }
            }
        }

        public void onClearStyles() {
            this.mActions.onAction(14);
        }

        public void onCancelViewManagers() {
            this.mActions.onAction(18);
        }

        private void clearStyles() {
            Log.d(LOG_TAG, "--- onClearStyles");
            clearStyles(this.mEST.getText());
            this.mEST.setBackgroundDrawable(this.mEST.mDefaultBackground);
            this.mBackgroundColor = 16777215;
            onRefreshZeoWidthChar();
        }

        public void onRefreshZeoWidthChar() {
            Editable txt = this.mEST.getText();
            int i = 0;
            while (i < txt.length()) {
                if (txt.charAt(i) == EditStyledText.ZEROWIDTHCHAR) {
                    txt.replace(i, i + 1, "");
                    i--;
                }
                i++;
            }
        }

        public void onRefreshStyles() {
            Log.d(LOG_TAG, "--- onRefreshStyles");
            Editable txt = this.mEST.getText();
            int len = txt.length();
            int width = this.mEST.getWidth();
            HorizontalLineSpan[] lines = (HorizontalLineSpan[]) txt.getSpans(0, len, HorizontalLineSpan.class);
            for (HorizontalLineSpan line : lines) {
                line.resetWidth(width);
            }
            for (MarqueeSpan marquee : (MarqueeSpan[]) txt.getSpans(0, len, MarqueeSpan.class)) {
                marquee.resetColor(this.mEST.getBackgroundColor());
            }
            if (lines.length > 0) {
                txt.replace(0, 1, "" + txt.charAt(0));
            }
        }

        public void setBackgroundColor(int color) {
            this.mBackgroundColor = color;
        }

        public void setItemSize(int size, boolean reset) {
            Log.d(LOG_TAG, "--- setItemSize");
            if (isWaitingNextAction()) {
                this.mSizeWaitInput = size;
            } else if (this.mState == 2 || this.mState == 3) {
                if (size > 0) {
                    changeSizeSelectedText(size);
                }
                if (reset) {
                    resetEdit();
                }
            }
        }

        public void setItemColor(int color, boolean reset) {
            Log.d(LOG_TAG, "--- setItemColor");
            if (isWaitingNextAction()) {
                this.mColorWaitInput = color;
            } else if (this.mState == 2 || this.mState == 3) {
                if (color != 16777215) {
                    changeColorSelectedText(color);
                }
                if (reset) {
                    resetEdit();
                }
            }
        }

        public void setAlignment(Alignment align) {
            if (this.mState == 2 || this.mState == 3) {
                changeAlign(align);
                resetEdit();
            }
        }

        public void setTelop() {
            if (this.mState == 2 || this.mState == 3) {
                addTelop();
                resetEdit();
            }
        }

        public void setSwing() {
            if (this.mState == 2 || this.mState == 3) {
                addSwing();
                resetEdit();
            }
        }

        public void setMarquee(int marquee) {
            if (this.mState == 2 || this.mState == 3) {
                addMarquee(marquee);
                resetEdit();
            }
        }

        public void setTextComposingMask(int start, int end) {
            int foregroundColor;
            Log.d(EditStyledText.TAG, "--- setTextComposingMask:" + start + "," + end);
            int min = Math.min(start, end);
            int max = Math.max(start, end);
            if (!isWaitInput() || this.mColorWaitInput == 16777215) {
                foregroundColor = this.mEST.getForegroundColor(min);
            } else {
                foregroundColor = this.mColorWaitInput;
            }
            int backgroundColor = this.mEST.getBackgroundColor();
            Log.d(EditStyledText.TAG, "--- fg:" + Integer.toHexString(foregroundColor) + ",bg:" + Integer.toHexString(backgroundColor) + "," + isWaitInput() + "," + "," + this.mMode);
            if (foregroundColor == backgroundColor) {
                int maskColor = Integer.MIN_VALUE | ((-16777216 | backgroundColor) ^ -1);
                if (this.mComposingTextMask == null || this.mComposingTextMask.getBackgroundColor() != maskColor) {
                    this.mComposingTextMask = new BackgroundColorSpan(maskColor);
                }
                this.mEST.getText().setSpan(this.mComposingTextMask, min, max, 33);
            }
        }

        private void setEditMode(int mode) {
            this.mMode = mode;
        }

        private void setSelectState(int state) {
            this.mState = state;
        }

        public void unsetTextComposingMask() {
            Log.d(EditStyledText.TAG, "--- unsetTextComposingMask");
            if (this.mComposingTextMask != null) {
                this.mEST.getText().removeSpan(this.mComposingTextMask);
                this.mComposingTextMask = null;
            }
        }

        public boolean isEditting() {
            return this.mEditFlag;
        }

        public boolean isStyledText() {
            Editable txt = this.mEST.getText();
            int len = txt.length();
            if (((ParagraphStyle[]) txt.getSpans(0, len, ParagraphStyle.class)).length > 0 || ((QuoteSpan[]) txt.getSpans(0, len, QuoteSpan.class)).length > 0 || ((CharacterStyle[]) txt.getSpans(0, len, CharacterStyle.class)).length > 0 || this.mBackgroundColor != 16777215) {
                return EditStyledText.DBG;
            }
            return false;
        }

        public boolean isSoftKeyBlocked() {
            return this.mSoftKeyBlockFlag;
        }

        public boolean isWaitInput() {
            return this.mWaitInputFlag;
        }

        public int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public int getEditMode() {
            return this.mMode;
        }

        public int getSelectState() {
            return this.mState;
        }

        public int getSelectionStart() {
            return this.mCurStart;
        }

        public int getSelectionEnd() {
            return this.mCurEnd;
        }

        public int getSizeWaitInput() {
            return this.mSizeWaitInput;
        }

        public int getColorWaitInput() {
            return this.mColorWaitInput;
        }

        private void setInternalSelection(int curStart, int curEnd) {
            this.mCurStart = curStart;
            this.mCurEnd = curEnd;
        }

        public void updateSpanPreviousFromCursor(Editable txt, int start, int before, int after) {
            Log.d(LOG_TAG, "updateSpanPrevious:" + start + "," + before + "," + after);
            int end = start + after;
            int min = Math.min(start, end);
            int max = Math.max(start, end);
            for (Object span : txt.getSpans(min, min, Object.class)) {
                int spanstart;
                int spanend;
                if ((span instanceof ForegroundColorSpan) || (span instanceof AbsoluteSizeSpan) || (span instanceof MarqueeSpan) || (span instanceof AlignmentSpan)) {
                    spanstart = txt.getSpanStart(span);
                    spanend = txt.getSpanEnd(span);
                    Log.d(LOG_TAG, "spantype:" + span.getClass() + "," + spanstart);
                    int tempmax = max;
                    if ((span instanceof MarqueeSpan) || (span instanceof AlignmentSpan)) {
                        tempmax = findLineEnd(this.mEST.getText(), max);
                    } else if (this.mKeepNonLineSpan) {
                        tempmax = spanend;
                    }
                    if (spanend < tempmax) {
                        Log.d(LOG_TAG, "updateSpanPrevious: extend span");
                        txt.setSpan(span, spanstart, tempmax, 33);
                    }
                } else if (span instanceof HorizontalLineSpan) {
                    spanstart = txt.getSpanStart(span);
                    spanend = txt.getSpanEnd(span);
                    if (before > after) {
                        txt.replace(spanstart, spanend, "");
                        txt.removeSpan(span);
                    } else if (spanend == end && end < txt.length() && this.mEST.getText().charAt(end) != '\n') {
                        this.mEST.getText().insert(end, "\n");
                    }
                }
            }
        }

        public void updateSpanNextToCursor(Editable txt, int start, int before, int after) {
            Log.d(LOG_TAG, "updateSpanNext:" + start + "," + before + "," + after);
            int end = start + after;
            int min = Math.min(start, end);
            int max = Math.max(start, end);
            for (Object span : txt.getSpans(max, max, Object.class)) {
                if ((span instanceof MarqueeSpan) || (span instanceof AlignmentSpan)) {
                    int spanstart = txt.getSpanStart(span);
                    int spanend = txt.getSpanEnd(span);
                    Log.d(LOG_TAG, "spantype:" + span.getClass() + "," + spanend);
                    int tempmin = min;
                    if ((span instanceof MarqueeSpan) || (span instanceof AlignmentSpan)) {
                        tempmin = findLineStart(this.mEST.getText(), min);
                    }
                    if (tempmin < spanstart && before > after) {
                        txt.removeSpan(span);
                    } else if (spanstart > min) {
                        txt.setSpan(span, min, spanend, 33);
                    }
                } else if ((span instanceof HorizontalLineSpan) && txt.getSpanStart(span) == end && end > 0 && this.mEST.getText().charAt(end - 1) != '\n') {
                    this.mEST.getText().insert(end, "\n");
                    this.mEST.setSelection(end);
                }
            }
        }

        public boolean canPaste() {
            return (this.mCopyBuffer == null || this.mCopyBuffer.length() <= 0 || removeImageChar(this.mCopyBuffer).length() != 0) ? false : EditStyledText.DBG;
        }

        private void endEdit() {
            Log.d(LOG_TAG, "--- handleCancel");
            this.mMode = 0;
            this.mState = 0;
            this.mEditFlag = false;
            this.mColorWaitInput = 16777215;
            this.mSizeWaitInput = 0;
            this.mWaitInputFlag = false;
            this.mSoftKeyBlockFlag = false;
            this.mKeepNonLineSpan = false;
            this.mTextIsFinishedFlag = false;
            unsetSelect();
            this.mEST.setOnClickListener(null);
            unblockSoftKey();
        }

        private void fixSelectionAndDoNextAction() {
            Log.d(LOG_TAG, "--- handleComplete:" + this.mCurStart + "," + this.mCurEnd);
            if (!this.mEditFlag) {
                return;
            }
            if (this.mCurStart == this.mCurEnd) {
                Log.d(LOG_TAG, "--- cancel handle complete:" + this.mCurStart);
                resetEdit();
                return;
            }
            if (this.mState == 2) {
                this.mState = 3;
            }
            this.mActions.doNext(this.mMode);
            EditStyledText.stopSelecting(this.mEST, this.mEST.getText());
        }

        private SpannableStringBuilder removeImageChar(SpannableStringBuilder text) {
            SpannableStringBuilder buf = new SpannableStringBuilder(text);
            for (DynamicDrawableSpan style : (DynamicDrawableSpan[]) buf.getSpans(0, buf.length(), DynamicDrawableSpan.class)) {
                if ((style instanceof HorizontalLineSpan) || (style instanceof RescalableImageSpan)) {
                    buf.replace(buf.getSpanStart(style), buf.getSpanEnd(style), "");
                }
            }
            return buf;
        }

        private void copyToClipBoard() {
            this.mCopyBuffer = (SpannableStringBuilder) this.mEST.getText().subSequence(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()));
            SpannableStringBuilder clipboardtxt = removeImageChar(this.mCopyBuffer);
            ((ClipboardManager) EditStyledText.this.getContext().getSystemService("clipboard")).setText(clipboardtxt);
            dumpSpannableString(clipboardtxt);
            dumpSpannableString(this.mCopyBuffer);
        }

        private void cutToClipBoard() {
            copyToClipBoard();
            this.mEST.getText().delete(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()));
        }

        private boolean isClipBoardChanged(CharSequence clipboardText) {
            Log.d(EditStyledText.TAG, "--- isClipBoardChanged:" + clipboardText);
            if (this.mCopyBuffer == null) {
                return EditStyledText.DBG;
            }
            int len = clipboardText.length();
            CharSequence removedClipBoard = removeImageChar(this.mCopyBuffer);
            Log.d(EditStyledText.TAG, "--- clipBoard:" + len + "," + removedClipBoard + clipboardText);
            if (len != removedClipBoard.length()) {
                return EditStyledText.DBG;
            }
            for (int i = 0; i < len; i++) {
                if (clipboardText.charAt(i) != removedClipBoard.charAt(i)) {
                    return EditStyledText.DBG;
                }
            }
            return false;
        }

        private void pasteFromClipboard() {
            int min = Math.min(this.mEST.getSelectionStart(), this.mEST.getSelectionEnd());
            int max = Math.max(this.mEST.getSelectionStart(), this.mEST.getSelectionEnd());
            Selection.setSelection(this.mEST.getText(), max);
            ClipboardManager clip = (ClipboardManager) EditStyledText.this.getContext().getSystemService("clipboard");
            this.mKeepNonLineSpan = EditStyledText.DBG;
            this.mEST.getText().replace(min, max, clip.getText());
            if (!isClipBoardChanged(clip.getText())) {
                Log.d(EditStyledText.TAG, "--- handlePaste: startPasteImage");
                for (DynamicDrawableSpan style : (DynamicDrawableSpan[]) this.mCopyBuffer.getSpans(0, this.mCopyBuffer.length(), DynamicDrawableSpan.class)) {
                    int start = this.mCopyBuffer.getSpanStart(style);
                    if (style instanceof HorizontalLineSpan) {
                        insertImageSpan(new HorizontalLineSpan(-16777216, this.mEST.getWidth(), this.mEST.getText()), min + start);
                    } else if (style instanceof RescalableImageSpan) {
                        insertImageSpan(new RescalableImageSpan(this.mEST.getContext(), ((RescalableImageSpan) style).getContentUri(), this.mEST.getMaxImageWidthPx()), min + start);
                    }
                }
            }
        }

        private void handleSelectAll() {
            if (this.mEditFlag) {
                this.mActions.onAction(11);
            }
        }

        private void selectAll() {
            Selection.selectAll(this.mEST.getText());
            this.mCurStart = this.mEST.getSelectionStart();
            this.mCurEnd = this.mEST.getSelectionEnd();
            this.mMode = 5;
            this.mState = 3;
        }

        private void resetEdit() {
            endEdit();
            this.mEditFlag = EditStyledText.DBG;
            this.mEST.notifyStateChanged(this.mMode, this.mState);
        }

        private void setSelection() {
            Log.d(LOG_TAG, "--- onSelect:" + this.mCurStart + "," + this.mCurEnd);
            if (this.mCurStart < 0 || this.mCurStart > this.mEST.getText().length() || this.mCurEnd < 0 || this.mCurEnd > this.mEST.getText().length()) {
                Log.e(LOG_TAG, "Select is on, but cursor positions are illigal.:" + this.mEST.getText().length() + "," + this.mCurStart + "," + this.mCurEnd);
            } else if (this.mCurStart < this.mCurEnd) {
                this.mEST.setSelection(this.mCurStart, this.mCurEnd);
                this.mState = 2;
            } else if (this.mCurStart > this.mCurEnd) {
                this.mEST.setSelection(this.mCurEnd, this.mCurStart);
                this.mState = 2;
            } else {
                this.mState = 1;
            }
        }

        private void unsetSelect() {
            Log.d(LOG_TAG, "--- offSelect");
            EditStyledText.stopSelecting(this.mEST, this.mEST.getText());
            int currpos = this.mEST.getSelectionStart();
            this.mEST.setSelection(currpos, currpos);
            this.mState = 0;
        }

        private void setSelectStartPos() {
            Log.d(LOG_TAG, "--- setSelectStartPos");
            this.mCurStart = this.mEST.getSelectionStart();
            this.mState = 1;
        }

        private void setSelectEndPos() {
            if (this.mEST.getSelectionEnd() == this.mCurStart) {
                setEndPos(this.mEST.getSelectionStart());
            } else {
                setEndPos(this.mEST.getSelectionEnd());
            }
        }

        public void setEndPos(int pos) {
            Log.d(LOG_TAG, "--- setSelectedEndPos:" + pos);
            this.mCurEnd = pos;
            setSelection();
        }

        private boolean isWaitingNextAction() {
            Log.d(LOG_TAG, "--- waitingNext:" + this.mCurStart + "," + this.mCurEnd + "," + this.mState);
            if (this.mCurStart == this.mCurEnd && this.mState == 3) {
                waitSelection();
                return EditStyledText.DBG;
            }
            resumeSelection();
            return false;
        }

        private void waitSelection() {
            Log.d(LOG_TAG, "--- waitSelection");
            this.mWaitInputFlag = EditStyledText.DBG;
            if (this.mCurStart == this.mCurEnd) {
                this.mState = 1;
            } else {
                this.mState = 2;
            }
            EditStyledText.startSelecting(this.mEST, this.mEST.getText());
        }

        private void resumeSelection() {
            Log.d(LOG_TAG, "--- resumeSelection");
            this.mWaitInputFlag = false;
            this.mState = 3;
            EditStyledText.stopSelecting(this.mEST, this.mEST.getText());
        }

        private boolean isTextSelected() {
            return (this.mState == 2 || this.mState == 3) ? EditStyledText.DBG : false;
        }

        private void setStyledTextSpan(Object span, int start, int end) {
            Log.d(LOG_TAG, "--- setStyledTextSpan:" + this.mMode + "," + start + "," + end);
            int min = Math.min(start, end);
            int max = Math.max(start, end);
            this.mEST.getText().setSpan(span, min, max, 33);
            Selection.setSelection(this.mEST.getText(), max);
        }

        private void setLineStyledTextSpan(Object span) {
            int min = Math.min(this.mCurStart, this.mCurEnd);
            int max = Math.max(this.mCurStart, this.mCurEnd);
            int current = this.mEST.getSelectionStart();
            int start = findLineStart(this.mEST.getText(), min);
            int end = findLineEnd(this.mEST.getText(), max);
            if (start == end) {
                this.mEST.getText().insert(end, "\n");
                setStyledTextSpan(span, start, end + 1);
            } else {
                setStyledTextSpan(span, start, end);
            }
            Selection.setSelection(this.mEST.getText(), current);
        }

        private void changeSizeSelectedText(int size) {
            if (this.mCurStart != this.mCurEnd) {
                setStyledTextSpan(new AbsoluteSizeSpan(size), this.mCurStart, this.mCurEnd);
            } else {
                Log.e(LOG_TAG, "---changeSize: Size of the span is zero");
            }
        }

        private void changeColorSelectedText(int color) {
            if (this.mCurStart != this.mCurEnd) {
                setStyledTextSpan(new ForegroundColorSpan(color), this.mCurStart, this.mCurEnd);
            } else {
                Log.e(LOG_TAG, "---changeColor: Size of the span is zero");
            }
        }

        private void changeAlign(Alignment align) {
            setLineStyledTextSpan(new Standard(align));
        }

        private void addTelop() {
            addMarquee(1);
        }

        private void addSwing() {
            addMarquee(0);
        }

        private void addMarquee(int marquee) {
            Log.d(LOG_TAG, "--- addMarquee:" + marquee);
            setLineStyledTextSpan(new MarqueeSpan(marquee, this.mEST.getBackgroundColor()));
        }

        private void insertImageSpan(DynamicDrawableSpan span, int curpos) {
            Log.d(LOG_TAG, "--- insertImageSpan:");
            if (span == null || span.getDrawable() == null) {
                Log.e(LOG_TAG, "--- insertImageSpan: null span was inserted");
                this.mEST.sendHintMessage(5);
                return;
            }
            this.mEST.getText().insert(curpos, "￼");
            this.mEST.getText().setSpan(span, curpos, curpos + 1, 33);
            this.mEST.notifyStateChanged(this.mMode, this.mState);
        }

        private int findLineStart(Editable text, int current) {
            int pos = current;
            while (pos > 0 && text.charAt(pos - 1) != '\n') {
                pos--;
            }
            Log.d(LOG_TAG, "--- findLineStart:" + current + "," + text.length() + "," + pos);
            return pos;
        }

        private int findLineEnd(Editable text, int current) {
            int pos = current;
            while (pos < text.length()) {
                if (text.charAt(pos) == '\n') {
                    pos++;
                    break;
                }
                pos++;
            }
            Log.d(LOG_TAG, "--- findLineEnd:" + current + "," + text.length() + "," + pos);
            return pos;
        }

        private void dumpSpannableString(CharSequence txt) {
            if (txt instanceof Spannable) {
                Spannable spannable = (Spannable) txt;
                int len = spannable.length();
                Log.d(EditStyledText.TAG, "--- dumpSpannableString, txt:" + spannable + ", len:" + len);
                for (Object style : spannable.getSpans(0, len, Object.class)) {
                    Log.d(EditStyledText.TAG, "--- dumpSpannableString, class:" + style + "," + spannable.getSpanStart(style) + "," + spannable.getSpanEnd(style) + "," + spannable.getSpanFlags(style));
                }
            }
        }

        public void showSoftKey() {
            showSoftKey(this.mEST.getSelectionStart(), this.mEST.getSelectionEnd());
        }

        public void showSoftKey(int oldSelStart, int oldSelEnd) {
            Log.d(LOG_TAG, "--- showsoftkey");
            if (this.mEST.isFocused() && !isSoftKeyBlocked()) {
                this.mSkr.mNewStart = Selection.getSelectionStart(this.mEST.getText());
                this.mSkr.mNewEnd = Selection.getSelectionEnd(this.mEST.getText());
                if (((InputMethodManager) EditStyledText.this.getContext().getSystemService("input_method")).showSoftInput(this.mEST, 0, this.mSkr) && this.mSkr != null) {
                    Selection.setSelection(EditStyledText.this.getText(), oldSelStart, oldSelEnd);
                }
            }
        }

        public void hideSoftKey() {
            Log.d(LOG_TAG, "--- hidesoftkey");
            if (this.mEST.isFocused()) {
                this.mSkr.mNewStart = Selection.getSelectionStart(this.mEST.getText());
                this.mSkr.mNewEnd = Selection.getSelectionEnd(this.mEST.getText());
                ((InputMethodManager) this.mEST.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mEST.getWindowToken(), 0, this.mSkr);
            }
        }

        public void blockSoftKey() {
            Log.d(LOG_TAG, "--- blockSoftKey:");
            hideSoftKey();
            this.mSoftKeyBlockFlag = EditStyledText.DBG;
        }

        public void unblockSoftKey() {
            Log.d(LOG_TAG, "--- unblockSoftKey:");
            this.mSoftKeyBlockFlag = false;
        }
    }

    private class MenuHandler implements OnMenuItemClickListener {
        private MenuHandler() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return EditStyledText.this.onTextContextMenuItem(item.getItemId());
        }
    }

    public static class SavedStyledTextState extends BaseSavedState {
        public int mBackgroundColor;

        SavedStyledTextState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mBackgroundColor);
        }

        public String toString() {
            return "EditStyledText.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " bgcolor=" + this.mBackgroundColor + "}";
        }
    }

    private static class SoftKeyReceiver extends ResultReceiver {
        EditStyledText mEST;
        int mNewEnd;
        int mNewStart;

        SoftKeyReceiver(EditStyledText est) {
            super(est.getHandler());
            this.mEST = est;
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode != 2) {
                Selection.setSelection(this.mEST.getText(), this.mNewStart, this.mNewEnd);
            }
        }
    }

    private static class StyledTextArrowKeyMethod extends ArrowKeyMovementMethod {
        String LOG_TAG = "StyledTextArrowKeyMethod";
        EditorManager mManager;

        StyledTextArrowKeyMethod(EditorManager manager) {
            this.mManager = manager;
        }

        public boolean onKeyDown(TextView widget, Spannable buffer, int keyCode, KeyEvent event) {
            Log.d(this.LOG_TAG, "---onkeydown:" + keyCode);
            this.mManager.unsetTextComposingMask();
            if (this.mManager.getSelectState() == 1 || this.mManager.getSelectState() == 2) {
                return executeDown(widget, buffer, keyCode);
            }
            return super.onKeyDown(widget, buffer, keyCode, event);
        }

        private int getEndPos(TextView widget) {
            if (widget.getSelectionStart() == this.mManager.getSelectionStart()) {
                return widget.getSelectionEnd();
            }
            return widget.getSelectionStart();
        }

        protected boolean up(TextView widget, Spannable buffer) {
            Log.d(this.LOG_TAG, "--- up:");
            Layout layout = widget.getLayout();
            int end = getEndPos(widget);
            int line = layout.getLineForOffset(end);
            if (line > 0) {
                int to;
                if (layout.getParagraphDirection(line) == layout.getParagraphDirection(line - 1)) {
                    to = layout.getOffsetForHorizontal(line - 1, layout.getPrimaryHorizontal(end));
                } else {
                    to = layout.getLineStart(line - 1);
                }
                this.mManager.setEndPos(to);
                this.mManager.onCursorMoved();
            }
            return EditStyledText.DBG;
        }

        protected boolean down(TextView widget, Spannable buffer) {
            Log.d(this.LOG_TAG, "--- down:");
            Layout layout = widget.getLayout();
            int end = getEndPos(widget);
            int line = layout.getLineForOffset(end);
            if (line < layout.getLineCount() - 1) {
                int to;
                if (layout.getParagraphDirection(line) == layout.getParagraphDirection(line + 1)) {
                    to = layout.getOffsetForHorizontal(line + 1, layout.getPrimaryHorizontal(end));
                } else {
                    to = layout.getLineStart(line + 1);
                }
                this.mManager.setEndPos(to);
                this.mManager.onCursorMoved();
            }
            return EditStyledText.DBG;
        }

        protected boolean left(TextView widget, Spannable buffer) {
            Log.d(this.LOG_TAG, "--- left:");
            this.mManager.setEndPos(widget.getLayout().getOffsetToLeftOf(getEndPos(widget)));
            this.mManager.onCursorMoved();
            return EditStyledText.DBG;
        }

        protected boolean right(TextView widget, Spannable buffer) {
            Log.d(this.LOG_TAG, "--- right:");
            this.mManager.setEndPos(widget.getLayout().getOffsetToRightOf(getEndPos(widget)));
            this.mManager.onCursorMoved();
            return EditStyledText.DBG;
        }

        private boolean executeDown(TextView widget, Spannable buffer, int keyCode) {
            Log.d(this.LOG_TAG, "--- executeDown: " + keyCode);
            switch (keyCode) {
                case 19:
                    return false | up(widget, buffer);
                case 20:
                    return false | down(widget, buffer);
                case 21:
                    return false | left(widget, buffer);
                case 22:
                    return false | right(widget, buffer);
                case 23:
                    this.mManager.onFixSelectedItem();
                    return EditStyledText.DBG;
                default:
                    return false;
            }
        }
    }

    private class StyledTextConverter {
        private EditStyledText mEST;
        private StyledTextHtmlConverter mHtml;

        public StyledTextConverter(EditStyledText est, StyledTextHtmlConverter html) {
            this.mEST = est;
            this.mHtml = html;
        }

        public void setStyledTextHtmlConverter(StyledTextHtmlConverter html) {
            this.mHtml = html;
        }

        public String getHtml(boolean escapeFlag) {
            this.mEST.clearComposingText();
            this.mEST.onRefreshZeoWidthChar();
            String htmlBody = this.mHtml.toHtml(this.mEST.getText(), escapeFlag);
            Log.d(EditStyledText.TAG, "--- getHtml:" + htmlBody);
            return htmlBody;
        }

        public String getPreviewHtml() {
            this.mEST.clearComposingText();
            this.mEST.onRefreshZeoWidthChar();
            String html = this.mHtml.toHtml(this.mEST.getText(), EditStyledText.DBG, EditStyledText.this.getMaxImageWidthDip(), EditStyledText.this.getPaddingScale());
            int bgColor = this.mEST.getBackgroundColor();
            html = String.format("<body bgcolor=\"#%02X%02X%02X\">%s</body>", new Object[]{Integer.valueOf(Color.red(bgColor)), Integer.valueOf(Color.green(bgColor)), Integer.valueOf(Color.blue(bgColor)), html});
            Log.d(EditStyledText.TAG, "--- getPreviewHtml:" + html + "," + this.mEST.getWidth());
            return html;
        }

        public void getUriArray(ArrayList<Uri> uris, Editable text) {
            uris.clear();
            Log.d(EditStyledText.TAG, "--- getUriArray:");
            int len = text.length();
            int i = 0;
            while (i < text.length()) {
                int next = text.nextSpanTransition(i, len, ImageSpan.class);
                ImageSpan[] images = (ImageSpan[]) text.getSpans(i, next, ImageSpan.class);
                for (int j = 0; j < images.length; j++) {
                    Log.d(EditStyledText.TAG, "--- getUriArray: foundArray" + images[j].getSource());
                    uris.add(Uri.parse(images[j].getSource()));
                }
                i = next;
            }
        }

        public void SetHtml(String html) {
            this.mEST.setText(this.mHtml.fromHtml(html, new ImageGetter() {
                public Drawable getDrawable(String src) {
                    Exception e;
                    Drawable drawable;
                    Log.d(EditStyledText.TAG, "--- sethtml: src=" + src);
                    if (!src.startsWith("content://")) {
                        return null;
                    }
                    Uri uri = Uri.parse(src);
                    try {
                        Bitmap bitmap;
                        System.gc();
                        InputStream is = StyledTextConverter.this.mEST.getContext().getContentResolver().openInputStream(uri);
                        Options opt = new Options();
                        opt.inJustDecodeBounds = EditStyledText.DBG;
                        BitmapFactory.decodeStream(is, null, opt);
                        is.close();
                        is = StyledTextConverter.this.mEST.getContext().getContentResolver().openInputStream(uri);
                        int width = opt.outWidth;
                        int height = opt.outHeight;
                        if (opt.outWidth > EditStyledText.this.getMaxImageWidthPx()) {
                            width = EditStyledText.this.getMaxImageWidthPx();
                            height = (EditStyledText.this.getMaxImageWidthPx() * height) / opt.outWidth;
                            bitmap = BitmapFactory.decodeStream(is, new Rect(0, 0, width, height), null);
                        } else {
                            bitmap = BitmapFactory.decodeStream(is);
                        }
                        Drawable drawable2 = new BitmapDrawable(StyledTextConverter.this.mEST.getContext().getResources(), bitmap);
                        try {
                            drawable2.setBounds(0, 0, width, height);
                            is.close();
                            return drawable2;
                        } catch (Exception e2) {
                            e = e2;
                            drawable = drawable2;
                        } catch (OutOfMemoryError e3) {
                            drawable = drawable2;
                            Log.e(EditStyledText.TAG, "OutOfMemoryError");
                            StyledTextConverter.this.mEST.setHint(5);
                            return null;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        Log.e(EditStyledText.TAG, "--- set html: Failed to loaded content " + uri, e);
                        return null;
                    } catch (OutOfMemoryError e5) {
                        Log.e(EditStyledText.TAG, "OutOfMemoryError");
                        StyledTextConverter.this.mEST.setHint(5);
                        return null;
                    }
                }
            }, null));
        }
    }

    private static class StyledTextDialog {
        private static final int TYPE_BACKGROUND = 1;
        private static final int TYPE_FOREGROUND = 0;
        private AlertDialog mAlertDialog;
        private CharSequence[] mAlignNames;
        private CharSequence mAlignTitle;
        private Builder mBuilder;
        private CharSequence mColorDefaultMessage;
        private CharSequence[] mColorInts;
        private CharSequence[] mColorNames;
        private CharSequence mColorTitle;
        private EditStyledText mEST;
        private CharSequence[] mMarqueeNames;
        private CharSequence mMarqueeTitle;
        private CharSequence[] mSizeDisplayInts;
        private CharSequence[] mSizeNames;
        private CharSequence[] mSizeSendInts;
        private CharSequence mSizeTitle;

        public StyledTextDialog(EditStyledText est) {
            this.mEST = est;
        }

        public void setBuilder(Builder builder) {
            this.mBuilder = builder;
        }

        public void setColorAlertParams(CharSequence colortitle, CharSequence[] colornames, CharSequence[] colorInts, CharSequence defaultColorMessage) {
            this.mColorTitle = colortitle;
            this.mColorNames = colornames;
            this.mColorInts = colorInts;
            this.mColorDefaultMessage = defaultColorMessage;
        }

        public void setSizeAlertParams(CharSequence sizetitle, CharSequence[] sizenames, CharSequence[] sizedisplayints, CharSequence[] sizesendints) {
            this.mSizeTitle = sizetitle;
            this.mSizeNames = sizenames;
            this.mSizeDisplayInts = sizedisplayints;
            this.mSizeSendInts = sizesendints;
        }

        public void setAlignAlertParams(CharSequence aligntitle, CharSequence[] alignnames) {
            this.mAlignTitle = aligntitle;
            this.mAlignNames = alignnames;
        }

        public void setMarqueeAlertParams(CharSequence marqueetitle, CharSequence[] marqueenames) {
            this.mMarqueeTitle = marqueetitle;
            this.mMarqueeNames = marqueenames;
        }

        private boolean checkColorAlertParams() {
            Log.d(EditStyledText.TAG, "--- checkParams");
            if (this.mBuilder == null) {
                Log.e(EditStyledText.TAG, "--- builder is null.");
                return false;
            } else if (this.mColorTitle == null || this.mColorNames == null || this.mColorInts == null) {
                Log.e(EditStyledText.TAG, "--- color alert params are null.");
                return false;
            } else if (this.mColorNames.length == this.mColorInts.length) {
                return EditStyledText.DBG;
            } else {
                Log.e(EditStyledText.TAG, "--- the length of color alert params are different.");
                return false;
            }
        }

        private boolean checkSizeAlertParams() {
            Log.d(EditStyledText.TAG, "--- checkParams");
            if (this.mBuilder == null) {
                Log.e(EditStyledText.TAG, "--- builder is null.");
                return false;
            } else if (this.mSizeTitle == null || this.mSizeNames == null || this.mSizeDisplayInts == null || this.mSizeSendInts == null) {
                Log.e(EditStyledText.TAG, "--- size alert params are null.");
                return false;
            } else if (this.mSizeNames.length == this.mSizeDisplayInts.length || this.mSizeSendInts.length == this.mSizeDisplayInts.length) {
                return EditStyledText.DBG;
            } else {
                Log.e(EditStyledText.TAG, "--- the length of size alert params are different.");
                return false;
            }
        }

        private boolean checkAlignAlertParams() {
            Log.d(EditStyledText.TAG, "--- checkAlignAlertParams");
            if (this.mBuilder == null) {
                Log.e(EditStyledText.TAG, "--- builder is null.");
                return false;
            } else if (this.mAlignTitle != null) {
                return EditStyledText.DBG;
            } else {
                Log.e(EditStyledText.TAG, "--- align alert params are null.");
                return false;
            }
        }

        private boolean checkMarqueeAlertParams() {
            Log.d(EditStyledText.TAG, "--- checkMarqueeAlertParams");
            if (this.mBuilder == null) {
                Log.e(EditStyledText.TAG, "--- builder is null.");
                return false;
            } else if (this.mMarqueeTitle != null) {
                return EditStyledText.DBG;
            } else {
                Log.e(EditStyledText.TAG, "--- Marquee alert params are null.");
                return false;
            }
        }

        private void buildDialogue(CharSequence title, CharSequence[] names, OnClickListener l) {
            this.mBuilder.setTitle(title);
            this.mBuilder.setIcon(0);
            this.mBuilder.setPositiveButton(null, null);
            this.mBuilder.setNegativeButton(17039360, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    StyledTextDialog.this.mEST.onStartEdit();
                }
            });
            this.mBuilder.setItems(names, l);
            this.mBuilder.setView(null);
            this.mBuilder.setCancelable(EditStyledText.DBG);
            this.mBuilder.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    Log.d(EditStyledText.TAG, "--- oncancel");
                    StyledTextDialog.this.mEST.onStartEdit();
                }
            });
            this.mBuilder.show();
        }

        private void buildAndShowColorDialogue(int type, CharSequence title, int[] colors) {
            int BUTTON_SIZE = this.mEST.dipToPx(50);
            int BUTTON_MERGIN = this.mEST.dipToPx(2);
            int BUTTON_PADDING = this.mEST.dipToPx(15);
            this.mBuilder.setTitle(title);
            this.mBuilder.setIcon(0);
            this.mBuilder.setPositiveButton(null, null);
            this.mBuilder.setNegativeButton(17039360, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    StyledTextDialog.this.mEST.onStartEdit();
                }
            });
            this.mBuilder.setItems(null, null);
            LinearLayout verticalLayout = new LinearLayout(this.mEST.getContext());
            verticalLayout.setOrientation(1);
            verticalLayout.setGravity(1);
            verticalLayout.setPadding(BUTTON_PADDING, BUTTON_PADDING, BUTTON_PADDING, BUTTON_PADDING);
            LinearLayout horizontalLayout = null;
            for (int i = 0; i < colors.length; i++) {
                if (i % 5 == 0) {
                    horizontalLayout = new LinearLayout(this.mEST.getContext());
                    verticalLayout.addView(horizontalLayout);
                }
                Button button = new Button(this.mEST.getContext());
                button.setHeight(BUTTON_SIZE);
                button.setWidth(BUTTON_SIZE);
                button.setBackgroundDrawable(new ColorPaletteDrawable(colors[i], BUTTON_SIZE, BUTTON_SIZE, BUTTON_MERGIN));
                button.setDrawingCacheBackgroundColor(colors[i]);
                if (type == 0) {
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            StyledTextDialog.this.mEST.setItemColor(view.getDrawingCacheBackgroundColor());
                            if (StyledTextDialog.this.mAlertDialog != null) {
                                StyledTextDialog.this.mAlertDialog.setView(null);
                                StyledTextDialog.this.mAlertDialog.dismiss();
                                StyledTextDialog.this.mAlertDialog = null;
                                return;
                            }
                            Log.e(EditStyledText.TAG, "--- buildAndShowColorDialogue: can't find alertDialog");
                        }
                    });
                } else if (type == 1) {
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            StyledTextDialog.this.mEST.setBackgroundColor(view.getDrawingCacheBackgroundColor());
                            if (StyledTextDialog.this.mAlertDialog != null) {
                                StyledTextDialog.this.mAlertDialog.setView(null);
                                StyledTextDialog.this.mAlertDialog.dismiss();
                                StyledTextDialog.this.mAlertDialog = null;
                                return;
                            }
                            Log.e(EditStyledText.TAG, "--- buildAndShowColorDialogue: can't find alertDialog");
                        }
                    });
                }
                horizontalLayout.addView(button);
            }
            if (type == 1) {
                this.mBuilder.setPositiveButton(this.mColorDefaultMessage, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StyledTextDialog.this.mEST.setBackgroundColor(16777215);
                    }
                });
            } else if (type == 0) {
                this.mBuilder.setPositiveButton(this.mColorDefaultMessage, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StyledTextDialog.this.mEST.setItemColor(-16777216);
                    }
                });
            }
            this.mBuilder.setView(verticalLayout);
            this.mBuilder.setCancelable(EditStyledText.DBG);
            this.mBuilder.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    StyledTextDialog.this.mEST.onStartEdit();
                }
            });
            this.mAlertDialog = this.mBuilder.show();
        }

        private void onShowForegroundColorAlertDialog() {
            Log.d(EditStyledText.TAG, "--- onShowForegroundColorAlertDialog");
            if (checkColorAlertParams()) {
                int[] colorints = new int[this.mColorInts.length];
                for (int i = 0; i < colorints.length; i++) {
                    colorints[i] = Integer.parseInt((String) this.mColorInts[i], 16) - 16777216;
                }
                buildAndShowColorDialogue(0, this.mColorTitle, colorints);
            }
        }

        private void onShowBackgroundColorAlertDialog() {
            Log.d(EditStyledText.TAG, "--- onShowBackgroundColorAlertDialog");
            if (checkColorAlertParams()) {
                int[] colorInts = new int[this.mColorInts.length];
                for (int i = 0; i < colorInts.length; i++) {
                    colorInts[i] = Integer.parseInt((String) this.mColorInts[i], 16) - 16777216;
                }
                buildAndShowColorDialogue(1, this.mColorTitle, colorInts);
            }
        }

        private void onShowSizeAlertDialog() {
            Log.d(EditStyledText.TAG, "--- onShowSizeAlertDialog");
            if (checkSizeAlertParams()) {
                buildDialogue(this.mSizeTitle, this.mSizeNames, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(EditStyledText.TAG, "mBuilder.onclick:" + which);
                        StyledTextDialog.this.mEST.setItemSize(StyledTextDialog.this.mEST.dipToPx(Integer.parseInt((String) StyledTextDialog.this.mSizeDisplayInts[which])));
                    }
                });
            }
        }

        private void onShowAlignAlertDialog() {
            Log.d(EditStyledText.TAG, "--- onShowAlignAlertDialog");
            if (checkAlignAlertParams()) {
                buildDialogue(this.mAlignTitle, this.mAlignNames, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Alignment align = Alignment.ALIGN_NORMAL;
                        switch (which) {
                            case 0:
                                align = Alignment.ALIGN_NORMAL;
                                break;
                            case 1:
                                align = Alignment.ALIGN_CENTER;
                                break;
                            case 2:
                                align = Alignment.ALIGN_OPPOSITE;
                                break;
                            default:
                                Log.e(EditStyledText.TAG, "--- onShowAlignAlertDialog: got illigal align.");
                                break;
                        }
                        StyledTextDialog.this.mEST.setAlignment(align);
                    }
                });
            }
        }

        private void onShowMarqueeAlertDialog() {
            Log.d(EditStyledText.TAG, "--- onShowMarqueeAlertDialog");
            if (checkMarqueeAlertParams()) {
                buildDialogue(this.mMarqueeTitle, this.mMarqueeNames, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(EditStyledText.TAG, "mBuilder.onclick:" + which);
                        StyledTextDialog.this.mEST.setMarquee(which);
                    }
                });
            }
        }
    }

    public interface StyledTextHtmlConverter {
        Spanned fromHtml(String str);

        Spanned fromHtml(String str, ImageGetter imageGetter, TagHandler tagHandler);

        String toHtml(Spanned spanned);

        String toHtml(Spanned spanned, boolean z);

        String toHtml(Spanned spanned, boolean z, int i, float f);
    }

    private class StyledTextHtmlStandard implements StyledTextHtmlConverter {
        private StyledTextHtmlStandard() {
        }

        public String toHtml(Spanned text) {
            return Html.toHtml(text);
        }

        public String toHtml(Spanned text, boolean escapeNonAsciiChar) {
            return Html.toHtml(text);
        }

        public String toHtml(Spanned text, boolean escapeNonAsciiChar, int width, float scale) {
            return Html.toHtml(text);
        }

        public Spanned fromHtml(String source) {
            return Html.fromHtml(source);
        }

        public Spanned fromHtml(String source, ImageGetter imageGetter, TagHandler tagHandler) {
            return Html.fromHtml(source, imageGetter, tagHandler);
        }
    }

    public static class StyledTextInputConnection extends InputConnectionWrapper {
        EditStyledText mEST;

        public StyledTextInputConnection(InputConnection target, EditStyledText est) {
            super(target, EditStyledText.DBG);
            this.mEST = est;
        }

        public boolean commitText(CharSequence text, int newCursorPosition) {
            Log.d(EditStyledText.TAG, "--- commitText:");
            this.mEST.mManager.unsetTextComposingMask();
            return super.commitText(text, newCursorPosition);
        }

        public boolean finishComposingText() {
            Log.d(EditStyledText.TAG, "--- finishcomposing:");
            if (!(this.mEST.isSoftKeyBlocked() || this.mEST.isButtonsFocused() || this.mEST.isEditting())) {
                this.mEST.onEndEdit();
            }
            return super.finishComposingText();
        }
    }

    public EditStyledText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditStyledText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditStyledText(Context context) {
        super(context);
        init();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean superResult;
        if (event.getAction() == 1) {
            cancelLongPress();
            boolean editting = isEditting();
            if (!editting) {
                onStartEdit();
            }
            int oldSelStart = Selection.getSelectionStart(getText());
            int oldSelEnd = Selection.getSelectionEnd(getText());
            superResult = super.onTouchEvent(event);
            if (isFocused() && getSelectState() == 0) {
                if (editting) {
                    this.mManager.showSoftKey(Selection.getSelectionStart(getText()), Selection.getSelectionEnd(getText()));
                } else {
                    this.mManager.showSoftKey(oldSelStart, oldSelEnd);
                }
            }
            this.mManager.onCursorMoved();
            this.mManager.unsetTextComposingMask();
        } else {
            superResult = super.onTouchEvent(event);
        }
        sendOnTouchEvent(event);
        return superResult;
    }

    public Parcelable onSaveInstanceState() {
        SavedStyledTextState ss = new SavedStyledTextState(super.onSaveInstanceState());
        ss.mBackgroundColor = this.mManager.getBackgroundColor();
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedStyledTextState) {
            SavedStyledTextState ss = (SavedStyledTextState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            setBackgroundColor(ss.mBackgroundColor);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mManager != null) {
            this.mManager.onRefreshStyles();
        }
    }

    public boolean onTextContextMenuItem(int id) {
        boolean selection = getSelectionStart() != getSelectionEnd() ? DBG : false;
        switch (id) {
            case ID_HORIZONTALLINE /*16776961*/:
                onInsertHorizontalLine();
                return DBG;
            case ID_CLEARSTYLES /*16776962*/:
                onClearStyles();
                return DBG;
            case ID_SHOWEDIT /*16776963*/:
                onStartEdit();
                return DBG;
            case ID_HIDEEDIT /*16776964*/:
                onEndEdit();
                return DBG;
            case ID_SELECT_ALL /*16908319*/:
                onStartSelectAll();
                return DBG;
            case ID_CUT /*16908320*/:
                if (selection) {
                    onStartCut();
                    return DBG;
                }
                this.mManager.onStartSelectAll(false);
                onStartCut();
                return DBG;
            case ID_COPY /*16908321*/:
                if (selection) {
                    onStartCopy();
                    return DBG;
                }
                this.mManager.onStartSelectAll(false);
                onStartCopy();
                return DBG;
            case ID_PASTE /*16908322*/:
                onStartPaste();
                return DBG;
            case ID_START_SELECTING_TEXT /*16908328*/:
                onStartSelect();
                this.mManager.blockSoftKey();
                break;
            case ID_STOP_SELECTING_TEXT /*16908329*/:
                onFixSelectedItem();
                break;
        }
        return super.onTextContextMenuItem(id);
    }

    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
        MenuHandler handler = new MenuHandler();
        if (STR_HORIZONTALLINE != null) {
            menu.add(0, ID_HORIZONTALLINE, 0, STR_HORIZONTALLINE).setOnMenuItemClickListener(handler);
        }
        if (isStyledText() && STR_CLEARSTYLES != null) {
            menu.add(0, ID_CLEARSTYLES, 0, STR_CLEARSTYLES).setOnMenuItemClickListener(handler);
        }
        if (this.mManager.canPaste()) {
            menu.add(0, ID_PASTE, 0, STR_PASTE).setOnMenuItemClickListener(handler).setAlphabeticShortcut('v');
        }
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        if (this.mManager != null) {
            this.mManager.updateSpanNextToCursor(getText(), start, before, after);
            this.mManager.updateSpanPreviousFromCursor(getText(), start, before, after);
            if (after > before) {
                this.mManager.setTextComposingMask(start, start + after);
            } else if (before < after) {
                this.mManager.unsetTextComposingMask();
            }
            if (this.mManager.isWaitInput()) {
                if (after > before) {
                    this.mManager.onCursorMoved();
                    onFixSelectedItem();
                } else if (after < before) {
                    this.mManager.onAction(22);
                }
            }
        }
        super.onTextChanged(text, start, before, after);
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        this.mInputConnection = new StyledTextInputConnection(super.onCreateInputConnection(outAttrs), this);
        return this.mInputConnection;
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            onStartEdit();
        } else if (!isButtonsFocused()) {
            onEndEdit();
        }
    }

    private void init() {
        this.mConverter = new StyledTextConverter(this, new StyledTextHtmlStandard());
        this.mDialog = new StyledTextDialog(this);
        this.mManager = new EditorManager(this, this.mDialog);
        setMovementMethod(new StyledTextArrowKeyMethod(this.mManager));
        this.mDefaultBackground = getBackground();
        requestFocus();
    }

    public void setStyledTextHtmlConverter(StyledTextHtmlConverter html) {
        this.mConverter.setStyledTextHtmlConverter(html);
    }

    public void addEditStyledTextListener(EditStyledTextNotifier estInterface) {
        if (this.mESTNotifiers == null) {
            this.mESTNotifiers = new ArrayList();
        }
        this.mESTNotifiers.add(estInterface);
    }

    public void removeEditStyledTextListener(EditStyledTextNotifier estInterface) {
        if (this.mESTNotifiers != null) {
            int i = this.mESTNotifiers.indexOf(estInterface);
            if (i > 0) {
                this.mESTNotifiers.remove(i);
            }
        }
    }

    private void sendOnTouchEvent(MotionEvent event) {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                ((EditStyledTextNotifier) i$.next()).sendOnTouchEvent(event);
            }
        }
    }

    public boolean isButtonsFocused() {
        boolean retval = false;
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                retval |= ((EditStyledTextNotifier) i$.next()).isButtonsFocused();
            }
        }
        return retval;
    }

    private void showPreview() {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                if (((EditStyledTextNotifier) i$.next()).showPreview()) {
                    return;
                }
            }
        }
    }

    private void cancelViewManagers() {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                ((EditStyledTextNotifier) i$.next()).cancelViewManager();
            }
        }
    }

    private void showInsertImageSelectAlertDialog() {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                if (((EditStyledTextNotifier) i$.next()).showInsertImageSelectAlertDialog()) {
                    return;
                }
            }
        }
    }

    private void showMenuAlertDialog() {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                if (((EditStyledTextNotifier) i$.next()).showMenuAlertDialog()) {
                    return;
                }
            }
        }
    }

    private void sendHintMessage(int msgId) {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                ((EditStyledTextNotifier) i$.next()).sendHintMsg(msgId);
            }
        }
    }

    private void notifyStateChanged(int mode, int state) {
        if (this.mESTNotifiers != null) {
            Iterator i$ = this.mESTNotifiers.iterator();
            while (i$.hasNext()) {
                ((EditStyledTextNotifier) i$.next()).onStateChanged(mode, state);
            }
        }
    }

    public void onStartEdit() {
        this.mManager.onAction(20);
    }

    public void onEndEdit() {
        this.mManager.onAction(21);
    }

    public void onResetEdit() {
        this.mManager.onAction(22);
    }

    public void onStartCopy() {
        this.mManager.onAction(1);
    }

    public void onStartCut() {
        this.mManager.onAction(7);
    }

    public void onStartPaste() {
        this.mManager.onAction(2);
    }

    public void onStartSize() {
        this.mManager.onAction(3);
    }

    public void onStartColor() {
        this.mManager.onAction(4);
    }

    public void onStartBackgroundColor() {
        this.mManager.onAction(16);
    }

    public void onStartAlign() {
        this.mManager.onAction(6);
    }

    public void onStartTelop() {
        this.mManager.onAction(8);
    }

    public void onStartSwing() {
        this.mManager.onAction(9);
    }

    public void onStartMarquee() {
        this.mManager.onAction(10);
    }

    public void onStartSelect() {
        this.mManager.onStartSelect(DBG);
    }

    public void onStartSelectAll() {
        this.mManager.onStartSelectAll(DBG);
    }

    public void onStartShowPreview() {
        this.mManager.onAction(17);
    }

    public void onStartShowMenuAlertDialog() {
        this.mManager.onStartShowMenuAlertDialog();
    }

    public void onStartAction(int mode, boolean notifyStateChanged) {
        this.mManager.onAction(mode, notifyStateChanged);
    }

    public void onFixSelectedItem() {
        this.mManager.onFixSelectedItem();
    }

    public void onInsertImage() {
        this.mManager.onAction(15);
    }

    public void onInsertImage(Uri uri) {
        this.mManager.onInsertImage(uri);
    }

    public void onInsertImage(int resId) {
        this.mManager.onInsertImage(resId);
    }

    public void onInsertHorizontalLine() {
        this.mManager.onAction(12);
    }

    public void onClearStyles() {
        this.mManager.onClearStyles();
    }

    public void onBlockSoftKey() {
        this.mManager.blockSoftKey();
    }

    public void onUnblockSoftKey() {
        this.mManager.unblockSoftKey();
    }

    public void onCancelViewManagers() {
        this.mManager.onCancelViewManagers();
    }

    private void onRefreshStyles() {
        this.mManager.onRefreshStyles();
    }

    private void onRefreshZeoWidthChar() {
        this.mManager.onRefreshZeoWidthChar();
    }

    public void setItemSize(int size) {
        this.mManager.setItemSize(size, DBG);
    }

    public void setItemColor(int color) {
        this.mManager.setItemColor(color, DBG);
    }

    public void setAlignment(Alignment align) {
        this.mManager.setAlignment(align);
    }

    public void setBackgroundColor(int color) {
        if (color != 16777215) {
            super.setBackgroundColor(color);
        } else {
            setBackgroundDrawable(this.mDefaultBackground);
        }
        this.mManager.setBackgroundColor(color);
        onRefreshStyles();
    }

    public void setMarquee(int marquee) {
        this.mManager.setMarquee(marquee);
    }

    public void setHtml(String html) {
        this.mConverter.SetHtml(html);
    }

    public void setBuilder(Builder builder) {
        this.mDialog.setBuilder(builder);
    }

    public void setColorAlertParams(CharSequence colortitle, CharSequence[] colornames, CharSequence[] colorints, CharSequence transparent) {
        this.mDialog.setColorAlertParams(colortitle, colornames, colorints, transparent);
    }

    public void setSizeAlertParams(CharSequence sizetitle, CharSequence[] sizenames, CharSequence[] sizedisplayints, CharSequence[] sizesendints) {
        this.mDialog.setSizeAlertParams(sizetitle, sizenames, sizedisplayints, sizesendints);
    }

    public void setAlignAlertParams(CharSequence aligntitle, CharSequence[] alignnames) {
        this.mDialog.setAlignAlertParams(aligntitle, alignnames);
    }

    public void setMarqueeAlertParams(CharSequence marqueetitle, CharSequence[] marqueenames) {
        this.mDialog.setMarqueeAlertParams(marqueetitle, marqueenames);
    }

    public void setContextMenuStrings(CharSequence horizontalline, CharSequence clearstyles, CharSequence paste) {
        STR_HORIZONTALLINE = horizontalline;
        STR_CLEARSTYLES = clearstyles;
        STR_PASTE = paste;
    }

    public boolean isEditting() {
        return this.mManager.isEditting();
    }

    public boolean isStyledText() {
        return this.mManager.isStyledText();
    }

    public boolean isSoftKeyBlocked() {
        return this.mManager.isSoftKeyBlocked();
    }

    public int getEditMode() {
        return this.mManager.getEditMode();
    }

    public int getSelectState() {
        return this.mManager.getSelectState();
    }

    public String getHtml() {
        return this.mConverter.getHtml(DBG);
    }

    public String getHtml(boolean escapeFlag) {
        return this.mConverter.getHtml(escapeFlag);
    }

    public String getHtml(ArrayList<Uri> uris, boolean escapeFlag) {
        this.mConverter.getUriArray(uris, getText());
        return this.mConverter.getHtml(escapeFlag);
    }

    public String getPreviewHtml() {
        return this.mConverter.getPreviewHtml();
    }

    public int getBackgroundColor() {
        return this.mManager.getBackgroundColor();
    }

    public EditorManager getEditStyledTextManager() {
        return this.mManager;
    }

    public int getForegroundColor(int pos) {
        if (pos < 0 || pos > getText().length()) {
            return -16777216;
        }
        ForegroundColorSpan[] spans = (ForegroundColorSpan[]) getText().getSpans(pos, pos, ForegroundColorSpan.class);
        if (spans.length > 0) {
            return spans[0].getForegroundColor();
        }
        return -16777216;
    }

    private void finishComposingText() {
        if (this.mInputConnection != null && !this.mManager.mTextIsFinishedFlag) {
            this.mInputConnection.finishComposingText();
            this.mManager.mTextIsFinishedFlag = DBG;
        }
    }

    private float getPaddingScale() {
        if (this.mPaddingScale <= 0.0f) {
            this.mPaddingScale = getContext().getResources().getDisplayMetrics().density;
        }
        return this.mPaddingScale;
    }

    private int dipToPx(int dip) {
        if (this.mPaddingScale <= 0.0f) {
            this.mPaddingScale = getContext().getResources().getDisplayMetrics().density;
        }
        return (int) (((double) (((float) dip) * getPaddingScale())) + 0.5d);
    }

    private int getMaxImageWidthDip() {
        return MAXIMAGEWIDTHDIP;
    }

    private int getMaxImageWidthPx() {
        return dipToPx(MAXIMAGEWIDTHDIP);
    }

    public void addAction(int mode, EditModeActionBase action) {
        this.mManager.addAction(mode, action);
    }

    public void addInputExtra(boolean create, String extra) {
        Bundle bundle = super.getInputExtras(create);
        if (bundle != null) {
            bundle.putBoolean(extra, DBG);
        }
    }

    private static void startSelecting(View view, Spannable content) {
        content.setSpan(SELECTING, 0, 0, PRESSED);
    }

    private static void stopSelecting(View view, Spannable content) {
        content.removeSpan(SELECTING);
    }
}
