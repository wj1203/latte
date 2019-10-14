package com.leap.latte.assignment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.leap.latte.assignment.DrawPoint;

import java.util.ArrayList;

/**
 * @author: leap
 * @time: 2019/10/14
 * @classname: AssignmentView
 * @description: 签名view
 */
public class AssignmentView extends View {

    private Context context;
    private Paint paint;
    /**图片保存路径*/
    private String filePath;
    /**画笔颜色*/
    private int paintColor;
    /**画笔宽度*/
    private int strokeWidth;

    private Paint rectPaint;

    private int viewHeight;
    private int viewWidth;
    private int viewLeft;
    private int viewRight;
    private int viewTop;
    private int viewBottom;

    private Rect rect;

    private ArrayList<DrawPoint> pointList = new ArrayList<>();

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
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.RED);
        // 初始化线
        drawPath = new Path();

    }

    private float cur_x, cur_y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("5415 down x", String.valueOf(event.getX()));
                Log.d("5415 down y", String.valueOf(event.getY()));
                pointList.add(new DrawPoint(event.getX(),event.getY()));
                drawPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("5415 move x", String.valueOf(event.getX()));
                Log.d("5415 move y", String.valueOf(event.getY()));
                pointList.add(new DrawPoint(event.getX(),event.getY()));
                drawPath.quadTo(cur_x, cur_y, x, y);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("5415 up x", String.valueOf(event.getX()));
                Log.d("5415 up y", String.valueOf(event.getY()));
                pointList.add(new DrawPoint(event.getX(),event.getY()));
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
        // 矩形
        rect = new Rect(0,getTop(),getRight()-getLeft(),getBottom()-getTop());
        canvas.drawRect(rect,rectPaint);
        // 线
        canvas.drawPath(drawPath,rectPaint);

    }
}
