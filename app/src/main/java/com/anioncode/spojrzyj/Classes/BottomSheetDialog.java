package com.anioncode.spojrzyj.Classes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.anioncode.spojrzyj.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        View contentView = View.inflate(getContext(), R.layout.model_bottom_sheet_layout, null);
        dialog.setContentView(contentView);

//        ImageView imageView = (ImageView) contentView.findViewById(R.id.dummy_image);
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "The Dialog ImageView is working fine...", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
