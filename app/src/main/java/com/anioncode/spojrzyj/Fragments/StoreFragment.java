package com.anioncode.spojrzyj.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.anioncode.spojrzyj.Classes.BottomSheetDialog;
import com.anioncode.spojrzyj.R;

public class StoreFragment extends Fragment {
    private Button btnShow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, parent, false);

        btnShow = (Button) view.findViewById(R.id.btn_show_dialog);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), bottomSheetDialog.getTag());

            }
        });

        return view;
    }
}
