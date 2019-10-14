package com.leap.latte.base;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.assignment.AssignFragment;
import com.leap.latte.camera.fragment.CameraFragment;
import com.leap.latte.recyclerview.RecyclerFragment;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;


/**
 *
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private QMUITopBar topBar;

    private Button btnCamera;
    private Button btnRecyclerView;
    private Button btnAssignment;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnRecyclerView = view.findViewById(R.id.btnRecyclerView);
        btnAssignment = view.findViewById(R.id.btnAssignment);

        topBar.setTitle("latte主页");


        btnCamera.setOnClickListener(this);
        btnRecyclerView.setOnClickListener(this);
        btnAssignment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamera:
                CameraFragment cameraFragment = new CameraFragment();
                startFragment(cameraFragment);
                break;

            case R.id.btnRecyclerView:
                startFragment(new RecyclerFragment());
                break;
            case R.id.btnAssignment:
                startFragment(new AssignFragment());
                break;

        }
    }

    @Override
    protected void onBackPressed() {
        getActivity().finish();
    }
}
