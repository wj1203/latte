package com.leap.latte;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.leap.common_lib.BaseFragment;
import com.leap.latte.camera.fragment.CameraFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private QMUITopBar topBar;
    private Button btnCamera;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        btnCamera = view.findViewById(R.id.btnCamera);

        topBar.setTitle("latte主页");

        btnCamera.setOnClickListener(this);
        

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamera:
                CameraFragment cameraFragment = new CameraFragment();
                startFragment(cameraFragment);
                break;

        }
    }
}
