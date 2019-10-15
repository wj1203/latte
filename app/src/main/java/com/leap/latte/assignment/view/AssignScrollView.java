package com.leap.latte.assignment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * @author: leap
 * @time: 2019/10/15
 * @classname: AssignScrollView
 * @description:  自定义 签名页面 scrollView  内部拦截法
 */
public class AssignScrollView extends ScrollView {

    private Context context;

    public AssignScrollView(Context context) {
        super(context);
    }

    public AssignScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        float screemW = QMUIDisplayHelper.getScreenWidth(context);
        float screemH = QMUIDisplayHelper.getScreenHeight(context);

        // viewGroup 拦截
        if (x<screemW*0.1){
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }
}
