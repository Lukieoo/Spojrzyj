package com.anioncode.spojrzyj.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.anioncode.spojrzyj.DatabaseHelper;
import com.anioncode.spojrzyj.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private AdView mAdView;
    private ListView listView;
    private DrawerLayout drawerLayout;
    DatabaseHelper mDatabaseHelper;
    FloatingActionButton floatingActionButton;
    int positionX = 0;
    TextView wpisy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history, parent, false);
        listView = view.findViewById(R.id.listview);
        drawerLayout = view.findViewById(R.id.drawer);
        floatingActionButton = view.findViewById(R.id.fab);
        wpisy=view.findViewById(R.id.wpisy);

        MobileAds.initialize(getActivity(), "ca-app-pub-3788232558823244~6450723475");

        mAdView = view.findViewById(R.id.adViews);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mDatabaseHelper = new DatabaseHelper(getActivity());


        LensView();
        if (listView.getCount()>0){
            wpisy.setVisibility(View.GONE);
        }else {
            wpisy.setVisibility(View.VISIBLE);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Are you sure?/Jesteś pewny?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (listView.getCount()>0){
                                    wpisy.setVisibility(View.GONE);
                                String ciach = listView.getItemAtPosition(positionX).toString();
                                String ciachniete="";
                                int i=0;
                                for (int a=0;a<ciach.length();a++){
                                    if(ciach.charAt(a)=='.'){
                                        ciachniete+=ciach.substring(0,a--);
                                        break;
                                    }
                                }
                                if (ciachniete!=null&&!ciachniete.trim().equals(""))
                                mDatabaseHelper.deleteName(Integer.parseInt(ciachniete));
                                }else {
                                    wpisy.setVisibility(View.VISIBLE);
                                }
                                LensView();

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
        return view;
    }

    private void LensView() {


        Cursor data = mDatabaseHelper.AllData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getInt(0) + ". L: " + data.getDouble(1) + ", P: " + data.getDouble(2) + " " + data.getString(3) + " " + data.getString(4));

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionX = position;
            }
        });


        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }
}
