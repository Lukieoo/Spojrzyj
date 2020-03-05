package com.anioncode.spojrzyj;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anioncode.spojrzyj.Activity.CalendarActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

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

        navigation();
        LensView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



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
                                        Toast.makeText(view.getContext(),listView.getSelectedItemPosition()+"",Toast.LENGTH_LONG).show();
                                       // LensView();

                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

            }
        });

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
                    case R.id.kalendarz: {
                        Intent intent =new Intent(history.this, CalendarActivity.class);
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        finish();
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
