package com.anioncode.spojrzyj.DatabaseSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperStore extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "store_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "lewe_oko";
    private static final String COL3 = "prawe_oko";
    private static final String COL4 = "rodzaj";
    private static final String COL5 = "typ";
    private static final String COL6 = "pojemnosc";
    private static final String COL7 = "numberSize";


    public DatabaseHelperStore(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE  " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " DOUBLE," +
                COL3 + " DOUBLE," +
                COL4 + " TEXT," +
                COL5 + " INTEGER," +
                COL6 + " INTEGER," +
                COL7 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //  db.execSQL("DROP  TABLE IF EXISTS " + TABLE_NAME);
        //   onCreate(db);
    }

    public boolean addData(double lewe_oko, double prawe_oko, String rodzaj, int typ, int pojemnosc, int numberSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, lewe_oko);
        contentValues.put(COL3, prawe_oko);
        contentValues.put(COL4, rodzaj);
        contentValues.put(COL5, typ);
        contentValues.put(COL6, pojemnosc);
        contentValues.put(COL7, numberSize);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + "  ORDER BY ID DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor AllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteName() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id>0";
        db.execSQL(query);
    }

    public void deltebyNumberSize(int numberSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE numberSize="+numberSize+"";
        System.out.println(query);
        db.execSQL(query);
    }
}

