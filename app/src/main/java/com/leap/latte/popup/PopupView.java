package com.leap.latte.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIWrapContentListView;

/**
 * @author: leap
 * @time: 2019/10/16
 * @classname: PupupView
 * @description:
 */
public class PopupView{

    private PopupWindow mPopUpWindow;
    private WindowManager mWindowManager;
    private Context mContext;
    private BaseAdapter adapter;

    public PopupView(Context context,BaseAdapter adapter) {
        this.mContext = context;
        this.adapter = adapter;
        mPopUpWindow = new PopupWindow(mContext);
        mPopUpWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    mPopUpWindow.dismiss();
                    return false;
                }
                return true;
            }
        });
        mWindowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
    }

    public void createList(int width, int maxHeight, AdapterView.OnItemClickListener onItemClickListener) {
        ListView listView = new QMUIWrapContentListView(mContext, maxHeight);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, maxHeight);
        listView.setLayoutParams(lp);
        listView.setAdapter(adapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setDivider(null);
        setContentView(listView);
    }

    protected ImageView mArrowUp;
    protected ImageView mArrowDown;
    private   RootView mRootViewWrapper;
    protected View mRootView;
    private void setContentView(View root) {
        if (root.getBackground() != null) {
            if (root instanceof IQMUILayout) {
                ((IQMUILayout) root).setRadius(getRootLayoutRadius(mContext));
            } else {
                QMUIFrameLayout clipLayout = new QMUIFrameLayout(mContext);
                clipLayout.setRadius(getRootLayoutRadius(mContext));
                clipLayout.addView(root);
                root = clipLayout;
            }
        }
        @SuppressLint("InflateParams") FrameLayout layout = (FrameLayout) LayoutInflater.from(mContext)
                .inflate(getRootLayout(), null, false);
        mArrowDown = (ImageView) layout.findViewById(com.qmuiteam.qmui.R.id.arrow_down);
        mArrowUp = (ImageView) layout.findViewById(com.qmuiteam.qmui.R.id.arrow_up);
        FrameLayout box = (FrameLayout) layout.findViewById(com.qmuiteam.qmui.R.id.box);
        box.addView(root);

        mRootViewWrapper = new PopupView.RootView(mContext);
        mRootViewWrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRootView = root;
        mRootViewWrapper.addView(root);
        mPopUpWindow.setContentView(mRootViewWrapper);
        mPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                QMUIBasePopup.this.onDismiss();
//                if (mDismissListener != null) {
//                    mDismissListener.onDismiss();
//                }
            }
        });
    }

    protected int getRootLayoutRadius(Context context) {
        return QMUIDisplayHelper.dp2px(context, 5);
    }
    @LayoutRes
    protected int getRootLayout() {
        return com.qmuiteam.qmui.R.layout.qmui_popup_layout;
    }
    protected void onConfigurationChanged(Configuration newConfig) {

    }
    protected int makeWidthMeasureSpec(View view) {
        return View.MeasureSpec.makeMeasureSpec(QMUIDisplayHelper.getScreenWidth(mContext), View.MeasureSpec.AT_MOST);
    }

    protected int makeHeightMeasureSpec(View view) {
        return View.MeasureSpec.makeMeasureSpec(QMUIDisplayHelper.getScreenHeight(mContext), View.MeasureSpec.AT_MOST);
    }

    /**
     *  rootView 内部类
     * */
    protected int mWindowHeight = 0;
    protected int mWindowWidth = 0;
    public class RootView extends QMUIFrameLayout {
        public RootView(Context context) {
            this(context, null);
        }

        public RootView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onConfigurationChanged(Configuration newConfig) {
            if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
                mPopUpWindow.dismiss();
            }
            PopupView.this.onConfigurationChanged(newConfig);
        }

        @Override
        public void addView(View child) {
            if (getChildCount() > 0) {
                throw new RuntimeException("only support one child");
            }
//            super.addView(child);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (getChildCount() == 0) {
                setMeasuredDimension(0, 0);
            }
            int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
            widthMeasureSpec = makeWidthMeasureSpec(this);
            heightMeasureSpec = makeHeightMeasureSpec(this);
            int targetWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            int targetWidthMode = MeasureSpec.getMode(widthMeasureSpec);
            int targetHeightSize = MeasureSpec.getSize(heightMeasureSpec);
            int targetHeightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (parentWidthSize < targetWidthSize) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, targetWidthMode);
            }
            if (parentHeightSize < targetHeightSize) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(parentHeightSize, targetHeightMode);
            }
            View child = getChildAt(0);
            child.measure(widthMeasureSpec, heightMeasureSpec);
            int oldWidth = mWindowWidth, oldHeight = mWindowHeight;
            mWindowWidth = child.getMeasuredWidth();
            mWindowHeight = child.getMeasuredHeight();
            setMeasuredDimension(mWindowWidth, mWindowHeight);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            if (getChildCount() == 0) {
                return;
            }
            View child = getChildAt(0);
            child.layout(getPaddingLeft(), getPaddingTop(), child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }
}
