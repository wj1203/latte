package com.leap.latte.surfaceview;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import com.leap.latte.R;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends QMUIFragment {

    public CameraFragment() {
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_camera, null);
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        return root;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
