package com.leap.latte.surfaceview;

import android.content.Context;
import android.graphics.Camera;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by  wj
 * Created at  2019/9/26
 */
public class CameraSurfaceView extends SurfaceView {
    private Camera mCamera;
    private Context mContext;

    private int screenWidth;
    private int screenHeight;




    public CameraSurfaceView(Context context) {
        super(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        screenWidth = QMUIDisplayHelper.getScreenWidth(context);
        screenHeight = QMUIDisplayHelper.getScreenHeight(context);
    }
}
