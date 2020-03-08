package com.anioncode.spojrzyj.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

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

public class StoreFragment extends Fragment {
    private FloatingActionButton btnShow;
    private AdapterStore adapter;
    private ArrayList<StoreModel> listStore;
    DatabaseHelperStore mDatabaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, parent, false);

        mDatabaseHelper=new DatabaseHelperStore(getActivity());

        listStore=new ArrayList<>();
        LensView();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterStore(getActivity(), listStore, new AdapterStore.ListenersInterface() {
            @Override
            public void itemDelete(StoreModel data) {
                mDatabaseHelper.deltebyNumberSize(data.getNumberSize());
                System.out.println(data.getNumberSize());
            }
        });
        recyclerView.setAdapter(adapter);

        btnShow = (FloatingActionButton) view.findViewById(R.id.fab);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(adapter,listStore);
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), bottomSheetDialog.getTag());

            }
        });

        return view;
    }
    private void LensView() {


        Cursor data = mDatabaseHelper.AllData();

        while(data.moveToNext()) {
            if(data.getInt(4)==1){
                listStore.add(new StoreModel(data.getInt(6),data.getInt(4),data.getDouble(1),data.getDouble(2),
                        data.getString(3)));
            }else {
                listStore.add(new StoreModel(data.getInt(6),data.getInt(4),data.getInt(5)) );
            }
        }
    }
}
