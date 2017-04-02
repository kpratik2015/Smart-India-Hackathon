package com.pratikkataria.hackathon;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.pratikkataria.hackathon.MyFirebaseMessagingService.NOTIFY_MSG;

/**
 * Created by hp on 24-03-2017.
 */

public class databaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "meter.db";
    public static final String TABLE_NAME = "meter_table";
    InputStream inputStream;
    public static int j = 0;

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

        String createtable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (DEVICE TEXT, VOLTAGE INTEGER)";
        db.execSQL(createtable);

        String createNotifyTable = "CREATE TABLE IF NOT EXISTS NOTIFY (ID INTEGER PRIMARY KEY, MESSAGE TEXT)";
        Log.e("DataBase", createNotifyTable);
        db.execSQL(createNotifyTable);

        String columns = "device, voltage";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";

        //String columns1 = "ID, MESSAGE";
        ContentValues values = new ContentValues();
        values.put("MESSAGE", NOTIFY_MSG);
        db.insert("NOTIFY",null, values);
        //String str3 = "INSERT INTO NOTIFY (" + columns1 + ") values( '"+ NOTIFY_MSG +"' )";
        //Log.e("DataBase", str3);
        db.beginTransaction();
        //db.execSQL(str3);
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
        db.close();
    }

    public void replicaOfOnCreate(){
        SQLiteDatabase db = this.getWritableDatabase();
        String createNotifyTable = "CREATE TABLE IF NOT EXISTS NOTIFY (ID INTEGER PRIMARY KEY, MESSAGE TEXT)";
        Log.e("DataBase", createNotifyTable);
        db.execSQL(createNotifyTable);

        ContentValues values = new ContentValues();
        values.put("MESSAGE", NOTIFY_MSG);
        db.insert("NOTIFY",null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS NOTIFY");
        // create new tables
        onCreate(db);
    }

    public Cursor showData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }

    public Cursor showNotificationData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM NOTIFY",null);
        return data;
    }


}

