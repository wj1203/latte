package com.leap.latte.recyclerview;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;

import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.base.MainFragment;
import com.leap.latte.recyclerview.scrollRecycler.imple.TouchCallback;
import com.leap.latte.recyclerview.scrollRecycler.imple.TouchRecyclerAdapter;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;

/**
 *
 */
public class RecyclerFragment extends BaseFragment {
    private QMUITopBar topBar;
    private RecyclerView recyclerView;

    private ArrayList<String> dataList = new ArrayList<>();


    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recycler, null);
        initView(view);

        for (int i = 0; i < 20; i++)
            dataList.add(i + "");

        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        topBar.setTitle("可拖动Item的Recycler");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new MainFragment());
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        TouchRecyclerAdapter adapter = new TouchRecyclerAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new TouchCallback(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

}
