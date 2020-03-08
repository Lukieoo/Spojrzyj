package com.anioncode.spojrzyj.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.store_fragment, parent, false);

        return view;
    }

}
