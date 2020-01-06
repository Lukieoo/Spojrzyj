package com.anioncode.spojrzyj;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    private Button button;
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
    //    private ListView listView;
    private TextView Dni;
    SimpleDateFormat dateFormat;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation();

        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Toast.makeText(getApplicationContext(),sharedPref.getString("dane", ""),Toast.LENGTH_SHORT).show();
        String txt = sharedPref.getString("dane", "");


        checkBox = findViewById(R.id.chexbox);

        if (txt.equals("Yes")) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {

                                                        SharedPreferences.Editor editor = sharedPref.edit();
                                                        editor.putString("dane", "Yes");
                                                        editor.commit();
                                                        //     Toast.makeText(getApplicationContext(),sharedPref.getString("dane", ""),Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        SharedPreferences.Editor editor = sharedPref.edit();
                                                        editor.putString("dane", "NO");
                                                        editor.commit();
                                                        //     Toast.makeText(getApplicationContext(),sharedPref.getString("dane", ""),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
        );
        MobileAds.initialize(MainActivity.this, "ca-app-pub-3788232558823244~6450723475");


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        textView = findViewById(R.id.type);
        textViewod = findViewById(R.id.od);
        textViewdoo = findViewById(R.id.doo);
        Dni = findViewById(R.id.Dni);

        dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        progressBar = findViewById(R.id.horizontal_progress_bar);


        okolewe = findViewById(R.id.okolewe);
        okoprawe = findViewById(R.id.okoprawe);

        button = (Button) findViewById(R.id.button);
        //  listView=findViewById(R.id.conetent);
        mDatabaseHelper = new DatabaseHelper(this);
        LensView();
        //   if(pozostalo<3){
        //  sendNotification();
        //   }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Addnew.class);
                startActivity(intent);

                finish();

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

                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.info: {
                        Info();
                        drawerLayout.closeDrawers();

                        break;
                    }
                    case R.id.historia: {

                        finish();
                     //   overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(MainActivity.this, history.class);
                        startActivity(intent);

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
        scrol=findViewById(R.id.scrol);
        sToogel = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){
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
            textViewdoo.setText(getString(R.string.doo) + " " + dateFormat.format(calendar.getTime()));
            Data_do = dateFormat.format(calendar.getTime());


            long differenceInMillis = calendar.getTimeInMillis() - calendartoday.getTimeInMillis();
            Calendar result = Calendar.getInstance();
            result.setTimeInMillis(differenceInMillis);
            long liczbaMSwDobie = 1000 * 60 * 60 * 24;
            pozostalo = (result.getTimeInMillis() / liczbaMSwDobie) + 1;
            Dni.setText(getString(R.string.pozostdni) + " " + pozostalo + " " + getString(R.string.dni));
            progrsbar();
            GLOBALNA = (int) pozostalo;
        }


        //    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        //    listView.setAdapter(adapter);
    }

    private void progrsbar() {

        progressBar.setMax(dni);

        progressBar.setProgress((int) pozostalo);
        //Toast.makeText(this,dni+" "+pozostalo,Toast.LENGTH_LONG).show();
    }

    public void sendNotification() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Spojrzyj";
            String description = "Spojrzyj";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Spojrzyj", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Spojrzyj")
                .setSmallIcon(R.drawable.eye)
                .setContentTitle(getString(R.string.twoje))
                .setContentText(getString(R.string.pozostdni) + " " + pozostalo + " " + getString(R.string.dni))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.pozostdni) + " " + pozostalo + " " + getString(R.string.dni)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (sToogel.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        LensView();
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String txt = sharedPref.getString("dane", "");
        if (txt.equals("Yes")) {

            sendNotification();
        }
        super.onPause();
    }
}
