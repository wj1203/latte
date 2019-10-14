package com.leap.latte.assignment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.leap.latte.assignment.view.RotateText;

/**
 * @author: wj
 * @time: 2019/10/14
 * @classname: RotateTextView
 * @description: 自定义 tv  旋转90度 纵向显示
 */
public class RotateTextView extends LinearLayout {

    private Context context;


    public RotateTextView(Context context) {
        super(context);
        init(context);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setText(String text, int color) {
        removeAllViews();
        if (text != null) {
            char[] chara = text.toCharArray();
            for (int i = 0; i < chara.length; i++) {
                RotateText rotateText = new RotateText(context);
                rotateText.setText(text.substring(i, i + 1));
                rotateText.setTextColor(color);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,100);
                rotateText.setGravity(Gravity.CENTER);
                rotateText.setLayoutParams(params);
                addView(rotateText);
            }
        }
    }


}
