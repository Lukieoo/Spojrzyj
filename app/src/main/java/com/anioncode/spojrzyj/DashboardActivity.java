package com.anioncode.spojrzyj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.anioncode.spojrzyj.Fragments.CalendarFragment;
import com.anioncode.spojrzyj.Fragments.MainFragment;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layoutFrame, new MainFragment());
        ft.commit();

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.colorPrimaryDark));
        }else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:

                    getSupportFragmentManager().beginTransaction().replace(R.id.layoutFrame,
                            new MainFragment()).commit();
                    drawer.closeDrawer(GravityCompat.START);


                break;
            case R.id.kalendarz:
                getSupportFragmentManager().beginTransaction().replace(R.id.layoutFrame,
                        new CalendarFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

        }


        return true;
    }
}
