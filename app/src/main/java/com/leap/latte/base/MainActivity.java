package com.leap.latte.base;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.leap.common_lib.BaseActivity;
import com.leap.latte.R;

import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContextViewId() {
        return R.id.rlContainer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();

        // 进入main frag
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.rlContainer,new MainFragment());
        transaction.commit();
    }

    private void getPermission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,},1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
