package com.anioncode.spojrzyj.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anioncode.spojrzyj.Adapter.AdapterCalendar;
import com.anioncode.spojrzyj.DatabaseHelper;
import com.anioncode.spojrzyj.MainActivity;
import com.anioncode.spojrzyj.Model.Data;
import com.anioncode.spojrzyj.R;
import com.anioncode.spojrzyj.history;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements AdapterCalendar.ItemClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle sToogel;
    public RelativeLayout scrol;
    public TextView DayYear;
    public AdapterCalendar adapter;
    public TextView text;
    ArrayList<Data> listData;
    DatabaseHelper mDatabaseHelper;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    CompactCalendarView compactCalendarView;
    ArrayList<Event> eventy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        navigation();
        DayYear = findViewById(R.id.DayYear);
        text = findViewById(R.id.Text);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        eventy = new ArrayList<>();

        Date date = new Date(System.currentTimeMillis());
        listData = new ArrayList<>();

        DayYear.setText(simpleDateFormat.format(date));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                // Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                DayYear.setText(simpleDateFormat.format(dateClicked));
                listData.clear();
                for (Event event : events) {
                    listData.add(new Data(simpleDateFormat.format(dateClicked), event.getData().toString()));
                }
                if (listData.size() > 0) text.setVisibility(View.GONE);
                else text.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //   LensView();
                listData.clear();
                List<Event> events = compactCalendarView.getEvents(firstDayOfNewMonth);
                for (Event a : events) {
                    listData.add(new Data(simpleDateFormat.format(firstDayOfNewMonth), a.getData().toString()));
                }
                if (events.size() > 0) text.setVisibility(View.GONE);
                else text.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                DayYear.setText(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });


        LensView();


        if (listData.size() > 0) text.setVisibility(View.GONE);
        else text.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.itemsEye);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterCalendar(this, listData);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void LensView() {

        Cursor data = mDatabaseHelper.AllData();

        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            // listData.add(data.getInt(0)+". L: "+data.getDouble(1)+", P: "+data.getDouble(2)+" "+data.getString(3)+" "+data.getString(4));
            //  listData.add(new Data(data.getString(4),"Założenie soczewek"));

            // String input = data.getString(4).replace( "." , "-" )+" 18:00:00".replace( " " , "T" ) ;
            System.out.println(data.getString(4));

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Calendar c = Calendar.getInstance();
            try {

                Date date = formatter.parse(data.getString(4));
                System.out.println(date);
                System.out.println(formatter.format(date));
                eventy.add(new Event(Color.BLUE, date.getTime(), "Założone soczeweki : " + data.getString(3)));
                c.setTime(date);
                switch (data.getString(3)) {
                    case "Jednodniowe": {
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        break;
                    }
                    case "Dwutygodniowe": {
                        c.add(Calendar.DAY_OF_MONTH, 14);
                        break;
                    }
                    case "Miesięczne": {
                        c.add(Calendar.DAY_OF_MONTH, 31);
                        break;
                    }
                    case "Kwartalne": {
                        c.add(Calendar.DAY_OF_MONTH, 93);
                        break;
                    }
                    case "Roczne": {
                        c.add(Calendar.DAY_OF_MONTH, 365);
                        break;
                    }
                    case "Daily": {
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        break;
                    }
                    case "Two Weekly": {
                        c.add(Calendar.DAY_OF_MONTH, 14);
                        break;
                    }
                    case "Monthly": {
                        c.add(Calendar.DAY_OF_MONTH, 31);
                        break;
                    }
                    case "Quarterly": {
                        c.add(Calendar.DAY_OF_MONTH, 93);
                        break;
                    }
                    case "Annual": {
                        c.add(Calendar.DAY_OF_MONTH, 365);
                        break;
                    }
                    default: {
                        c.add(Calendar.DAY_OF_MONTH, 1);
                    }

                }
                Date date1 = c.getTime();
                eventy.add(new Event(Color.RED, date1.getTime(), "Termin ważności dla : " + formatter.format(date)));

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        compactCalendarView.addEvents(eventy);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onItemClick(View view, int position) {
      //  Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    private void navigation() {

        navigationView = findViewById(R.id.navigationd_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu: {
                        finish();
                        //overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.info: {
                        Info();
                        drawerLayout.closeDrawers();

                        break;
                    }

                    case R.id.historia: {


                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(CalendarActivity.this, history.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.kalendarz: {

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
        scrol = findViewById(R.id.Main);
        sToogel = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                scrol.setTranslationX(slideX);

            }
        };

        drawerLayout.addDrawerListener(sToogel);
        sToogel.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
