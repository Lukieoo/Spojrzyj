package com.anioncode.spojrzyj;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.anioncode.spojrzyj.MainActivity.GLOBALNA;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, Integer.toString((int)LensView(mDatabaseHelper)));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    private static long LensView(DatabaseHelper mDatabaseHelper) {
        int dni=0;
        long pozostalo=0;
        String Data_do="";
        SimpleDateFormat dateFormat;
        Calendar calendar = Calendar.getInstance();
        Calendar calendartoday = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Cursor data = mDatabaseHelper.getData();

        while(data.moveToNext()){

            try {
                calendar.setTime(dateFormat.parse(data.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            switch (data.getString(3)){
                case "Jednodniowe":{
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dni=1;
                    break;
                }
                case "Dwutygodniowe":{
                    calendar.add(Calendar.DAY_OF_MONTH, 14);
                    dni=14;
                    break;}
                case "Miesięczne":{
                    calendar.add(Calendar.DAY_OF_MONTH, 31);
                    dni=31;
                    break;}
                case "Kwartalne":{
                    calendar.add(Calendar.DAY_OF_MONTH, 93);
                    dni=93;
                    break;}
                case "Roczne":{
                    calendar.add(Calendar.DAY_OF_MONTH, 365);
                    dni=365;
                    break;}
                case "Daily":{
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dni=1;
                    break;
                }
                case "Two Weekly":{
                    calendar.add(Calendar.DAY_OF_MONTH, 14);
                    dni=14;
                    break;}
                case "Monthly":{
                    calendar.add(Calendar.DAY_OF_MONTH, 31);
                    dni=31;
                    break;}
                case "Quarterly":{
                    calendar.add(Calendar.DAY_OF_MONTH, 93);
                    dni=93;
                    break;}
                case "Annual":{
                    calendar.add(Calendar.DAY_OF_MONTH, 365);
                    dni=365;
                    break;}
                default:{
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dni=1;
                }

            }




            long differenceInMillis = calendar.getTimeInMillis() - calendartoday.getTimeInMillis();
            Calendar result = Calendar.getInstance();
            result.setTimeInMillis(differenceInMillis);
            long liczbaMSwDobie = 1000 * 60 * 60 * 24;
            pozostalo= (result.getTimeInMillis() / liczbaMSwDobie)+1;
            return pozostalo;
        }


        //    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        //    listView.setAdapter(adapter);
        return 0;
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);
            updateAppWidget(context, appWidgetManager, appWidgetId);
           // CharSequence widgetText = context.getString(R.string.appwidget_text);
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setTextViewText(R.id.appwidget_text,Integer.toString((int)LensView(mDatabaseHelper)));


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

