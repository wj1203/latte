package com.leap.latte.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.base.MainFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 *
 * */
public class RecyclerFragment extends BaseFragment {
    private QMUITopBar topBar;



    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recycler,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView.ItemDecoration itemDecoration;
        topBar = view.findViewById(R.id.topBar);
        topBar.setTitle("Recycler 展示");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new MainFragment());
            }
        });
    }

}
