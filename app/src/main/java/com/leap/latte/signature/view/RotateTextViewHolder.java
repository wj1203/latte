package com.leap.latte.signature.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * @author: wj
 * @time: 2019/10/14
 * @classname: RotateTextView
 * @description: 自定义 LinearLayout  旋转90度 纵向显示
 */
public class RotateTextViewHolder extends LinearLayout {

    private Context context;


    public RotateTextViewHolder(Context context) {
        super(context);
        init(context);
    }

    public RotateTextViewHolder(Context context, AttributeSet attrs) {
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
                RotateTextView rotateText = new RotateTextView(context);
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
