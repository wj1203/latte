package com.leap.latte.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by  wj
 * Created at  2019/9/27
 */
public class CameraRectView extends View {

    /**屏幕大小*/
    private int screenWidth;
    private int screenHeight;

    /**矩形边距*/
    private int leftPadding;
    private int topPadding;

    /**矩形坐标*/
    private int rectTop;
    private int rectButtom;
    private int rectLeft;
    private int rectRight;

    /**矩形大小*/
    private int rectWidth;
    private int rectHeight;






    public CameraRectView(Context context) {
        super(context);
    }

    public CameraRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取屏幕大小
        screenWidth = QMUIDisplayHelper.getScreenWidth(context);
        screenHeight = QMUIDisplayHelper.getScreenHeight(context);
        // 计算padding
        leftPadding = screenWidth/10;
        topPadding = screenHeight/10;
        // 计算矩形大小
        rectWidth = screenWidth - leftPadding*2;
        rectHeight = screenHeight - topPadding*2;

        // 相对于viewGroup计算rect的坐标
        rectTop = topPadding;
        rectButtom = screenHeight - topPadding;
        rectLeft = leftPadding;
        rectRight = screenWidth - leftPadding;






    }
}
