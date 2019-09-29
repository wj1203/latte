package com.leap.latte.camera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by  wj
 * Created at  2019/9/27
 * 相机矩形边框（自定义宽高比例）
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
    private int rectBottom;
    private int rectLeft;
    private int rectRight;

    /**矩形大小*/
    private int rectWidth;
    private int rectHeight;

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    private Rect rect;

    /**画笔*/
    private Paint rectPaint;

    /**矩形比例*/
    private int proportionH = 5;
    private int proportionW = 3;

    public CameraRectView(Context context) {
        super(context);
    }

    public CameraRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取屏幕大小
        screenWidth = QMUIDisplayHelper.getScreenWidth(context);
        screenHeight = QMUIDisplayHelper.getScreenHeight(context);
        // 计算矩形大小
        rectWidth =  screenWidth/5*4;
        rectHeight = rectWidth*proportionH/proportionW;

        // 计算padding
        leftPadding = screenWidth/10;
        topPadding = (screenHeight-rectHeight)/2;

        // 相对于viewGroup计算rect的坐标
        rectTop = topPadding;
        rectBottom = screenHeight - topPadding;
        rectLeft = leftPadding;
        rectRight = screenWidth - leftPadding;

        // 初始化画笔
        rectPaint = new Paint();
        rectPaint.setAntiAlias(false);       // 无锯齿
        rectPaint.setStyle(Paint.Style.STROKE);  // 设置绘制图像为空心，FULL是实心
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.RED);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * @param left   The X coordinate of the left side of the rectangle
         * @param top    The Y coordinate of the top of the rectangle
         * @param right  The X coordinate of the right side of the rectangle
         * @param bottom The Y coordinate of the bottom of the rectangle
         */
        rect = new Rect(rectLeft,rectTop,rectRight,rectBottom);
        canvas.drawRect(rect,rectPaint );
    }


    public void setHWProportion(int H,int W){
        this.proportionH = H;
        this.proportionW = W;
    }

}
