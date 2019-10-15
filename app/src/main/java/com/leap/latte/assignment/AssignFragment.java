package com.leap.latte.assignment;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.assignment.view.AssignmentView;
import com.leap.latte.assignment.view.RotateTextViewHolder;

public class AssignFragment extends BaseFragment {

    private RotateTextViewHolder rtvClear;
    private RotateTextViewHolder rtvSave;

    private ImageView ivResult;

    private AssignmentView assignmentView;

    private Bitmap bitmap;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_assign, null);
        rtvClear = root.findViewById(R.id.rtvClear);
        rtvSave = root.findViewById(R.id.rtvSave);
        assignmentView = root.findViewById(R.id.assignmentView);
        ivResult = root.findViewById(R.id.ivResult);

        rtvClear.setText("清 除", Color.RED);
        rtvSave.setText("保 存", Color.BLUE);

        rtvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentView.clear();
            }
        });

        rtvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = assignmentView.save(getActivity());
                ivResult.setImageBitmap(bitmap);
            }
        });

        return root;
    }


}
