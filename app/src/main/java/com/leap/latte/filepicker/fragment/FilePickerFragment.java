package com.leap.latte.filepicker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.base.MainFragment;
import com.leap.latte.filepicker.bean.FileEntity;
import com.leap.latte.filepicker.FileScannerTask;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

public class FilePickerFragment extends BaseFragment implements FileScannerTask.FileScannerListener {
    private QMUITopBar topBar;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FileScannerTask(getContext(), this).execute();
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_file_picker, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        topBar.setTitle("扫描本地文件");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new MainFragment());
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
    }


    @Override
    public void scannerResult(List<FileEntity> entities) {
        FilePickerAdapter adapter = new FilePickerAdapter(getContext(), (ArrayList<FileEntity>) entities);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
