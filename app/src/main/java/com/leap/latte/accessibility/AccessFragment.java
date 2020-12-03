package com.leap.latte.accessibility;


import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.base.MainFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccessFragment extends BaseFragment {


    private QMUITopBar topBar;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_access, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        topBar.setTitle("Accessibility  Service");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new MainFragment());
            }
        });
    }



}
