package com.leap.latte.surfaceview;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created by  wj
 * Created at  2019/9/26
 */
@SuppressWarnings("ALL")
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private Camera mCamera;
    private Context mContext;

    private int screenWidth;
    private int screenHeight;

    //surfaceholder是控制surface的一个抽象接口，它能够控制surface的尺寸和格式，修改surface的像素，监视surface的变化等等
    private SurfaceHolder surfaceHolder;


    public CameraSurfaceView(Context context) {
        super(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        screenWidth = QMUIDisplayHelper.getScreenWidth(context);
        screenHeight = QMUIDisplayHelper.getScreenHeight(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }


    /**
     * 在surface创建后立即被调用。
     * 在开发自定义相机时，可以通过重载这个函数调用camera.open()、camera.setPreviewDisplay()，
     * 来实现获取相机资源、连接camera和surface等操作。
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            mCamera = Camera.open();
        }
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     *   在surface发生format或size变化时调用。在开发自定义相机时，
     *   可以通过重载这个函数调用camera.startPreview来开启相机预览，
     *   使得camera预览帧数据可以传递给surface，从而实时显示相机预览图像。
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //  获取相机的parameters
        Camera.Parameters parameters = mCamera.getParameters();
        //  获取支持的PictureSize
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        //  通过surface的高宽比获取最合适的 Camera.Size
        Camera.Size size = getProperSize(pictureSizeList, height / width);
        if (null == size) {
            // 如果没有使用设置的size
            size = parameters.getPictureSize();
        }
        // 根据选中的size设置surfaceView大小
        float w = size.width;
        float h = size.height;
        parameters.setPictureSize(size.width, size.height);
        this.setLayoutParams(new FrameLayout.LayoutParams((int) (height * (h / w)), height));
        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
        Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
        if (null != preSize) {
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        parameters.setJpegQuality(100); // 设置照片质量
        if (parameters.getSupportedFocusModes().contains(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }
//        mCamera.autoFocus(this);
        mCamera.cancelAutoFocus();//自动对焦。
        mCamera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
        mCamera.setParameters(parameters);
        mCamera.setPreviewCallback(this);

        mCamera.startPreview();

    }

    /**
     * 在surface销毁之前被调用。
     * 在开发自定义相机时，可以通过重载这个函数调用camera.stopPreview()，camera.release()
     * 来实现停止相机预览及释放相机资源等操作。
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    if (size.width > 1080) {
                        result = size;
                    }
                    break;
                }
            }
        }
        if (null == result) {
            for (int i = pictureSizeList.size() - 1; i > 0; i--) {
                float currentRatio = ((float) pictureSizeList.get(i).width) / pictureSizeList.get(i).height;
                if (screenRatio - currentRatio < 0.2) {
                    result = pictureSizeList.get(i);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d("5415", data.length + "");

    }
}
