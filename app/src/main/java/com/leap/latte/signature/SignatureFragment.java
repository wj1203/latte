package com.leap.latte.signature;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.leap.common_lib.BaseFragment;
import com.leap.latte.R;
import com.leap.latte.signature.view.SignatureView;
import com.leap.latte.signature.view.RotateTextViewHolder;

public class SignatureFragment extends BaseFragment {

    private RotateTextViewHolder rtvClear;
    private RotateTextViewHolder rtvSave;

    private ImageView ivResult;

    private SignatureView signatureView;

    private Bitmap bitmap;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_signature, null);
        rtvClear = root.findViewById(R.id.rtvClear);
        rtvSave = root.findViewById(R.id.rtvSave);
        signatureView = root.findViewById(R.id.assignmentView);
        ivResult = root.findViewById(R.id.ivResult);

        rtvClear.setText("清 除", Color.RED);
        rtvSave.setText("保 存", Color.BLUE);

        rtvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
            }
        });

        rtvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.save(getActivity());
                ivResult.setImageBitmap(bitmap);
            }
        });

        return root;
    }


}
