package com.anioncode.spojrzyj;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class history extends AppCompatActivity {

    private AdView mAdView;
    private ListView listView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle sToogel;
    private RelativeLayout relativeLayout;
    DatabaseHelper mDatabaseHelper;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView=findViewById(R.id.listview);
        drawerLayout=findViewById(R.id.drawer);
        floatingActionButton=findViewById(R.id.fab);

        MobileAds.initialize(history.this, "ca-app-pub-3788232558823244~6450723475");

        mAdView = findViewById(R.id.adViews);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mDatabaseHelper=new DatabaseHelper(getApplicationContext());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                        AlertDialog.Builder builder1 = new AlertDialog.Builder(history.this);
                        builder1.setMessage("Are you sure?/Jesteś pewny?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mDatabaseHelper.deleteName();
                                        LensView();

                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        LensView();

                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

            }
        });

        navigation();
        LensView();
    }

    private void navigation() {

        navigationView = findViewById(R.id.navigationd_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu: {
                        Intent intent =new Intent(history.this,MainActivity.class);
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    }
                    case R.id.info: {
                        Info();
                        drawerLayout.closeDrawers();

                        break;
                    }
                    case R.id.historia: {
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.close: {
                        System.exit(0);
                        break;
                    }

                }
                return false;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        relativeLayout=findViewById(R.id.relative);
        sToogel = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                relativeLayout.setTranslationX(slideX);

            }
        };
        drawerLayout.addDrawerListener(sToogel);
        sToogel.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void LensView() {


        Cursor data = mDatabaseHelper.AllData();
         ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
                  listData.add(data.getInt(0)+". L: "+data.getDouble(1)+", P: "+data.getDouble(2)+" "+data.getString(3)+" "+data.getString(4));

        }




            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            listView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(history.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (sToogel.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Info() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setMessage(getString(R.string.informacje));
        builder1.setIcon(R.drawable.eye);
        builder1.setTitle(getString(R.string.program));

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
