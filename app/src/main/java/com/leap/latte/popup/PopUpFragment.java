package com.leap.latte.popup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;

import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.base.MainFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopUpFragment extends BaseFragment {
    private QMUITopBar topBar;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pop_up, null);
        initView(view);
        List<String> data = new ArrayList<>();
        data.add("11");
        data.add("22");
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.simple_list_item, data);
        PopupView popupView = new PopupView(getContext(),adapter);
        popupView.createList(QMUIDisplayHelper.dpToPx(250), QMUIDisplayHelper.dpToPx(200),
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                }
        );
        return view;
    }

    private void initView(View view) {
        topBar = view.findViewById(R.id.topBar);
        topBar.setTitle("自定义PopUpView");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new MainFragment());
            }
        });
    }


}
