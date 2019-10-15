package com.leap.latte.assignment.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * @author: leap
 * @time: 2019/10/14
 * @classname: AssignmentView
 * @description: 签名view
 */
public class AssignmentView extends View {

    private Context context;
    /**图片保存路径*/
    private String filePath;

    private Paint rectPaint;
    private Paint linePaint;

    private int viewHeight;
    private int viewWidth;
    private int viewLeft;
    private int viewRight;
    private int viewTop;
    private int viewBottom;

    private Rect rect;


    private Path drawPath;

    public AssignmentView(Context context) {
        super(context);
        init(context);

    }


    public AssignmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        // 初始化画笔
        rectPaint = new Paint();
        rectPaint.setAntiAlias(false);       // 无锯齿
        rectPaint.setStyle(Paint.Style.STROKE);  // 设置绘制图像为空心，Fill是实心
        rectPaint.setStrokeWidth(3);
        rectPaint.setColor(Color.RED);

        linePaint = new Paint();
        linePaint.setAntiAlias(false);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(8);
        linePaint.setColor(Color.BLACK);
        // 初始化线
        drawPath = new Path();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // Set the beginning of the next contour to the point (x,y)
                drawPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                // Add a line from the last point to the specified point (x,y).
                drawPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // Sets the last point of the path.
                drawPath.setLastPoint(x,y);
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        viewLeft = getLeft();
        viewRight = getRight();
        viewTop = getTop();
        viewBottom = getBottom();

        // 矩形框
        rect = new Rect(0,getTop(),getRight()-getLeft(),getBottom()-getTop());
        canvas.drawRect(rect,rectPaint);
        // 线
        canvas.drawPath(drawPath,linePaint);
    }

    /**
     *  清除画板上的path
     **/
    public void clear(){
        drawPath.reset();
        invalidate();
    }

    /**
     * 保存签名图片
     * */
    public Bitmap save(Activity activity){
        // 截取屏幕
        Bitmap mScreenshot;
        View activityView = activity.getWindow().getDecorView();
        activityView.setDrawingCacheEnabled(true);
        activityView.destroyDrawingCache();
        activityView.buildDrawingCache();
        mScreenshot = activityView.getDrawingCache();
        // 返回 经过 裁剪矩形
        Matrix matrix = new Matrix();
        matrix.setRotate(270);
        return Bitmap.createBitmap(mScreenshot,viewLeft,viewTop,viewWidth,viewHeight,matrix,true);
    }
}
