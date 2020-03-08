package com.anioncode.spojrzyj.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anioncode.spojrzyj.Adapter.AdapterStore;
import com.anioncode.spojrzyj.Classes.BottomSheetDialog;
import com.anioncode.spojrzyj.DatabaseSQL.DatabaseHelperStore;
import com.anioncode.spojrzyj.Model.StoreModel;
import com.anioncode.spojrzyj.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_store, parent, false);
        Button button=view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","pawkrzysciak@gmail.com", null));

            emailIntent.putExtra(Intent.EXTRA_TEXT, "Witaj");
            startActivity(Intent.createChooser(emailIntent, "Zapytanie"));

        });
        return view;
    }

}
