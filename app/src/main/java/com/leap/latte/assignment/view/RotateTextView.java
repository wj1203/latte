package com.leap.latte.assignment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * @author: wj
 * @time: 2019/10/14
 * @classname: RotateText
 * @description:  横向显示文字tv
 */
public class RotateTextView extends android.support.v7.widget.AppCompatTextView {



    public RotateTextView(Context context) {
        super(context);
    }
    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float w = getMeasuredWidth()/2;
        float h = getMeasuredHeight()/2;
        // 绕 view 中心 旋转90度
        canvas.rotate(90,w,h);
        super.onDraw(canvas);
    }
}
