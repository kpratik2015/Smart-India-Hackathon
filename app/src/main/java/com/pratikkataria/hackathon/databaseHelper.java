package com.pratikkataria.hackathon;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hp on 24-03-2017.
 */

public class databaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "meter.db";
    public static final String TABLE_NAME = "meter_table";
    InputStream inputStream;

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        inputStream = context.getResources().openRawResource(R.raw.meterdevicereport);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //FileReader file = new FileReader(fileName);

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String tableName = TABLE_NAME;

        String createtable = "CREATE TABLE " + TABLE_NAME + " (DEVICE TEXT, VOLTAGE INTEGER)";
        db.execSQL(createtable);

        String columns = "device, voltage";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        int i = 0;
        try {
            while ((line = buffer.readLine()) != null) {
                if(i > 0) {
                    StringBuilder sb = new StringBuilder(str1);
                    String[] str = line.split(",");
                    sb.append("'" + str[1] + "',");
                    sb.append(str[9]);
                    sb.append(str2);
                    db.execSQL(sb.toString());
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor showData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }


}