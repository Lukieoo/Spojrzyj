package com.anioncode.spojrzyj.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements AdapterCalendar.ItemClickListener{
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        navigation();
        DayYear = findViewById(R.id.DayYear);
        text = findViewById(R.id.Text);
         compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        mDatabaseHelper=new DatabaseHelper(getApplicationContext());
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        final Event ev1 = new Event(Color.WHITE, 1433701251000L, "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);

        Date date = new Date(System.currentTimeMillis());


        DayYear.setText(simpleDateFormat.format(date));
        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
        Event ev2 = new Event(Color.WHITE, 1433704251000L);
        compactCalendarView.addEvent(ev2);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                // Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                DayYear.setText(simpleDateFormat.format(dateClicked));
                listData.clear();
                for (Event event:events){
                    listData.add(new Data(simpleDateFormat.format(dateClicked),event.getData().toString()));
                }
                if(listData.size()>0)text.setVisibility(View.GONE);
                else text.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                LensView();
                if(listData.size()>0)text.setVisibility(View.GONE);
                else text.setVisibility(View.VISIBLE);
                //   Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                DayYear.setText(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });

         listData = new ArrayList<>();
        LensView();
       // animalNames.add("Horse");
       // animalNames.add("Cow");
       // animalNames.add("Camel");
      //  animalNames.add("Sheep");
      //  animalNames.add("Goat");

        if(listData.size()>0)text.setVisibility(View.GONE);
        else text.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.itemsEye);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterCalendar(this, listData);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }
    private void LensView() {

        Cursor data = mDatabaseHelper.AllData();

        while(data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
           // listData.add(data.getInt(0)+". L: "+data.getDouble(1)+", P: "+data.getDouble(2)+" "+data.getString(3)+" "+data.getString(4));
            listData.add(new Data(data.getString(4),"Założenie soczewek"));

            String input = data.getString(4).replace( "." , "-" )+" 18:00:00".replace( " " , "T" ) ;

            if(input.charAt(4)=='-')input=input.subSequence(0,3)+"0"+input.subSequence(3,input.length());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                  LocalDateTime ldt = LocalDateTime.parse( input) ;
                ZoneId z = ZoneId.of( "Europe/Poland" ) ;
                ZonedDateTime zdt = ldt.atZone( z ) ;
                Instant instant = zdt.toInstant() ;
                long millisSinceEpoch = instant.toEpochMilli() ;
                Event ev2 = new Event(Color.WHITE, millisSinceEpoch,"Założenie soczewek");
                compactCalendarView.addEvent(ev2);

            }

        }

    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
