package com.anioncode.spojrzyj.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.anioncode.spojrzyj.AddLensActivity;
import com.anioncode.spojrzyj.DatabaseHelper;
import com.anioncode.spojrzyj.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainFragment extends Fragment {
    private AdView mAdView;

    private Button button;
    private LinearLayout button2;
    private TextView textView;
    private TextView textViewod;
    private TextView textViewdoo;
    private TextView okolewe;
    private TextView okoprawe;
    static int GLOBALNA = 0;
    CheckBox checkBox;
    private ProgressBar progressBar;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle sToogel;
    public ScrollView scrol;
    int dni = 0;
    long pozostalo = 0;
    String Data_do = "";

    int Year = 2020;
    int Month = 1;
    int Day = 15;
    //    private ListView listView;
    private TextView Dni;
    SimpleDateFormat dateFormat;
    DatabaseHelper mDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.main_fragment, parent, false);
        MobileAds.initialize(getActivity(), "ca-app-pub-3788232558823244~6450723475");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        textView = view.findViewById(R.id.type);
        textViewod = view.findViewById(R.id.od);
        textViewdoo = view.findViewById(R.id.doo);
        Dni = view.findViewById(R.id.Dni);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        progressBar = view.findViewById(R.id.horizontal_progress_bar);
        okolewe = view.findViewById(R.id.okolewe);
        okoprawe = view.findViewById(R.id.okoprawe);
        button = (Button) view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        LensView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddLensActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });

        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    private void LensView() {
        Calendar calendar = Calendar.getInstance();
        Calendar calendartoday = Calendar.getInstance();

        Cursor data = mDatabaseHelper.getData();
        // ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            //      listData.add(data.getInt(0)+". L: "+data.getDouble(1)+", P: "+data.getDouble(2)+" "+data.getString(3)+" "+data.getString(4));

            okolewe.setText(data.getString(1));
            okoprawe.setText(data.getString(2));
            textView.setText(data.getString(3));
            textViewod.setText(getString(R.string.od) + " " + data.getString(4));
            try {
                calendar.setTime(dateFormat.parse(data.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            switch (data.getString(3)) {
                case "Jednodniowe": {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dni = 1;
                    break;
                }
                case "Dwutygodniowe": {
                    calendar.add(Calendar.DAY_OF_MONTH, 14);
                    dni = 14;
                    break;
                }
                case "Miesięczne": {
                    calendar.add(Calendar.DAY_OF_MONTH, 31);
                    dni = 31;
                    break;
                }
                case "Kwartalne": {
                    calendar.add(Calendar.DAY_OF_MONTH, 93);
                    dni = 93;
                    break;
                }
                case "Roczne": {
                    calendar.add(Calendar.DAY_OF_MONTH, 365);
                    dni = 365;
                    break;
                }
                case "Daily": {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dni = 1;
                    break;
                }
                case "Two Weekly": {
                    calendar.add(Calendar.DAY_OF_MONTH, 14);
                    dni = 14;
                    break;
                }
                case "Monthly": {
                    calendar.add(Calendar.DAY_OF_MONTH, 31);
                    dni = 31;
                    break;
                }
                case "Quarterly": {
                    calendar.add(Calendar.DAY_OF_MONTH, 93);
                    dni = 93;
                    break;
                }
                case "Annual": {
                    calendar.add(Calendar.DAY_OF_MONTH, 365);
                    dni = 365;
                    break;
                }
                default: {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dni = 1;
                }

            }
            // Year=;
            textViewdoo.setText(getString(R.string.doo) + " " + dateFormat.format(calendar.getTime()));
            Data_do = dateFormat.format(calendar.getTime());
            String pomoDate = dateFormat.format(calendar.getTime());
            Year=Integer.parseInt(pomoDate.substring(6,10));
            Month=Integer.parseInt(pomoDate.substring(3,5));
            Day=Integer.parseInt(pomoDate.substring(0,2));

            long differenceInMillis = calendar.getTimeInMillis() - calendartoday.getTimeInMillis();
            Calendar result = Calendar.getInstance();
            result.setTimeInMillis(differenceInMillis);
            long liczbaMSwDobie = 1000 * 60 * 60 * 24;
            pozostalo = (result.getTimeInMillis() / liczbaMSwDobie) + 1;
            Dni.setText(getString(R.string.pozostdni) + " " + pozostalo + " " + getString(R.string.dni));
            progrsbar();
            GLOBALNA = (int) pozostalo;
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(Year, Month-1,
                            Day, 7, 30);
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(Year, Month-1,
                            Day, 8, 30);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, "Przypomnienie o ściągnięciu soczewek.")
                            .putExtra(CalendarContract.Events.DESCRIPTION, "Twój termin soczewek mija dziś")
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "Soczewki")
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);


                }
            });
        }


        //    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        //    listView.setAdapter(adapter);
    }

    private void progrsbar() {

        progressBar.setMax(dni);

        progressBar.setProgress((int) pozostalo);
        //Toast.makeText(this,dni+" "+pozostalo,Toast.LENGTH_LONG).show();
    }

}
