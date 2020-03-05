package com.anioncode.spojrzyj;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private RelativeLayout moveRelative;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawer = findViewById(R.id.drawer_layout);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

        toolbar = findViewById(R.id.toolbar);
        toolbar.setDrawingCacheBackgroundColor(Color.WHITE);
        toolbar.setTitle("");


        moveRelative = findViewById(R.id.MoveRelative);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);


                if (drawer.isDrawerVisible(Gravity.RIGHT)) {
                    float slideX = -1 * (drawerView.getWidth() * slideOffset);

                    moveRelative.setTranslationX(slideX);
                } else {

                    float slideX = drawerView.getWidth() * slideOffset;

                    moveRelative.setTranslationX(slideX);
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }
}
