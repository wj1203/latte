package com.leap.latte.base;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.popup.PopUpFragment;
import com.leap.latte.signature.SignatureFragment;
import com.leap.latte.camera.fragment.CameraFragment;
import com.leap.latte.recyclerview.RecyclerFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;


/**
 *
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private QMUITopBar topBar;

    private Button btnCamera;
    private Button btnRecyclerView;
    private Button btnAssignment;
    private Button btnPopUp;
    private Button btnScheme;

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
        btnPopUp = view.findViewById(R.id.btnPopUp);
        btnScheme = view.findViewById(R.id.btnScheme);

        topBar.setTitle("latte主页");


        btnCamera.setOnClickListener(this);
        btnRecyclerView.setOnClickListener(this);
        btnAssignment.setOnClickListener(this);
        btnPopUp.setOnClickListener(this);
        btnScheme.setOnClickListener(this);

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
                startFragment(new SignatureFragment());
                break;
            case R.id.btnPopUp:
                startFragment(new PopUpFragment());
                break;
            case R.id.btnScheme:
                schemeNavigateTo();
                break;

        }
    }

    private void schemeNavigateTo() {
        /**
         * [scheme]://[host]/[path]?[query]
         *
         * scheme 代表该Schema 协议名称
         *
         * host 代表Schema所作用的地址域
         *
         * path 代表Schema指定的页面
         *
         * query 要传递的参数
         * */
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("scheme://host/path")));
    }

    @Override
    protected void onBackPressed() {
        getActivity().finish();
    }
}
