package com.leap.latte.camera.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.camera.fragment.CameraFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoResultFragment extends BaseFragment {

    private Bundle bundle;
    private byte [] bytes;

    private QMUITopBar topBar;
    private ImageView ivRow;
    private ImageView ivCut;

    private int mPicHeight;
    private int mPicWidth;
    private int mRectHeight=0;
    private int mRectWidth=0;

    private int screenHeight;
    private int screenWidth;
    private int rectHeight;
    private int rectWidth;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_photo_result, null, false);
        bundle = getArguments();
        if (bundle !=null){
            bytes = bundle.getByteArray("bytes");
            mPicHeight = bundle.getInt("mPicHeight");
            mPicWidth = bundle.getInt("mPicWidth");
            screenHeight = bundle.getInt("screenHeight");
            screenWidth = bundle.getInt("screenWidth");
            rectHeight = bundle.getInt("rectHeight");
            rectWidth = bundle.getInt("rectWidth");

        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        ivRow = view.findViewById(R.id.ivRow);
        ivCut = view.findViewById(R.id.ivCut);

        topBar.setTitle("相机结果处理");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new CameraFragment());
            }
        });

        // 处理bytes
        loadImage();
    }

    private void loadImage() {
        // 转码处理
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        YuvImage yuvimage = new YuvImage(bytes, ImageFormat.NV21,mPicWidth,mPicHeight,null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, mPicWidth, mPicHeight), 100, baos);  //这里 80 是图片质量，取值范围 0-100，100为品质最高
        byte[] jdata = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(jdata, 0, jdata.length, options);
        // 旋转
        Matrix m = new Matrix();
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        m.setRotate(90);
        //旋转后的图片
        Bitmap bitmapRow = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);

        // 裁剪,计算裁剪的大小，坐标 (计算有问题)
        mRectHeight = rectHeight*mPicWidth/screenHeight;
        mRectWidth = rectWidth*mPicHeight/screenWidth;

        int x = (mPicHeight - mRectWidth)/2;
        int y = (mPicWidth - mRectHeight)/2;

        Bitmap bitmapCut = Bitmap.createBitmap(bitmapRow,x,y,mRectWidth,mRectHeight);


//        Bitmap bitmapCut = Bitmap.createBitmap(bitmapRow,
//                bundle.getInt("left"),bundle.getInt("top"),
//                bundle.getInt("right")-bundle.getInt("left"),
//                bundle.getInt("bottom")-bundle.getInt("top"));


        ivRow.setImageBitmap(bitmapRow);
        ivCut.setImageBitmap(bitmapCut);
    }


    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        startFragment(new CameraFragment());
    }
}
