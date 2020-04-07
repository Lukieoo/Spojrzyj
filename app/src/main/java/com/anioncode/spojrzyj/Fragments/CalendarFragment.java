package com.anioncode.spojrzyj.Fragments;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anioncode.spojrzyj.Adapter.AdapterCalendar;
import com.anioncode.spojrzyj.DatabaseHelper;
import com.anioncode.spojrzyj.Model.Data;
import com.anioncode.spojrzyj.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle sToogel;
    public ImageButton calendarID;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, parent, false);
        DayYear = view.findViewById(R.id.DayYear);
        text = view.findViewById(R.id.Text);
        calendarID = view.findViewById(R.id.calendarID);
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        ImageView today = view.findViewById(R.id.today);

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        eventy = new ArrayList<>();

        Date date = new Date(System.currentTimeMillis());
        listData = new ArrayList<>();

        DayYear.setText(simpleDateFormat.format(date));

        calendarID.setOnClickListener(v -> {
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            startActivity(intent);
        });

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

        RecyclerView recyclerView = view.findViewById(R.id.itemsEye);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterCalendar(getActivity(), listData);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        SimpleDateFormat setText = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        Date firstDatex = new Date();

        DayYear.setText(setText.format(firstDatex));
        compactCalendarView.setCurrentDate(firstDatex);

        listData.clear();
        List<Event> eventsx = compactCalendarView.getEvents(firstDatex);
        for (Event a : eventsx) {
            listData.add(new Data(simpleDateFormat.format(new Date()), a.getData().toString()));

        }
        if (eventsx.size() > 0) text.setVisibility(View.GONE);
        else text.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();

        today.setOnClickListener(v -> {
            Date firstDate = new Date();

            DayYear.setText(setText.format(firstDate));
            compactCalendarView.setCurrentDate(firstDate);

            listData.clear();
            List<Event> events = compactCalendarView.getEvents(firstDate);
            for (Event a : events) {
                listData.add(new Data(simpleDateFormat.format(new Date()), a.getData().toString()));

            }
            if (events.size() > 0) text.setVisibility(View.GONE);
            else text.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();


        });

        return view;
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
                eventy.add(new Event(Color.parseColor("#81d4fa"), date.getTime(), "Założone : " + data.getString(3)));
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
                eventy.add(new Event(Color.parseColor("#ef9a9a"), date1.getTime(), "Termin dla : " + formatter.format(date)));

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        compactCalendarView.addEvents(eventy);
    }
}
