package com.leap.latte.camera.fragment;


import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.leap.common_lib.BaseFragment;
import com.leap.latte.base.MainFragment;
import com.leap.latte.R;
import com.leap.latte.camera.view.CameraRectView;
import com.leap.latte.camera.view.CameraSurfaceView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends BaseFragment {
    private CameraRectView rectView;
    private CameraSurfaceView surfaceView;

    private Button btnPhoto;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_camera, null);
        rectView = root.findViewById(R.id.rectView);
        surfaceView = root.findViewById(R.id.surfaceView);
        btnPhoto = root.findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicByte();
            }
        });
        return root;
    }


    private void getPicByte() {
        byte[] bytes = surfaceView.takePhoto();
        Log.d("5415", "获取到图片的byte[]  大小：" + bytes.length);
        Camera.Size size =this.surfaceView.mCamera.getParameters().getPreviewSize();
        Bundle bundle = new Bundle();
        bundle.putByteArray("bytes", bytes);
        bundle.putInt("mPicHeight",size.height);
        bundle.putInt("mPicWidth",size.width);
        bundle.putInt("rectHeight",rectView.getRectHeight());
        bundle.putInt("rectWidth",rectView.getRectWidth());
        bundle.putInt("screenHeight",rectView.getScreenHeight());
        bundle.putInt("screenWidth",rectView.getScreenWidth());
        PhotoResultFragment resultFragment = new PhotoResultFragment();
        resultFragment.setArguments(bundle);
        startFragment(resultFragment);

    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.onDestroy();
        this.onDestroyView();
        this.onDetach();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onBackPressed() {
        startFragment(new MainFragment());
    }
}
