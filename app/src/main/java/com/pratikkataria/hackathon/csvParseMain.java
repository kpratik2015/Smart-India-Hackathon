package com.pratikkataria.hackathon;

/**
 * Created by Pat on 3/29/2017.
 */

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class csvParseMain extends ListActivity {
    InputStream inputStream;

    String[] ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_csv);
        inputStream = getResources().openRawResource(R.raw.meterdevicereport);

        ArrayList<String> values = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            int i = 0;
            //while ((csvLine = reader.readLine()) != null) {
            while ((csvLine = reader.readLine()) != null && i < 2) {
                ids=csvLine.split(",");
                if(i == 1) {
                    try {

                        Log.e("Device Name ", "" + ids[1]);
                        Log.e("Voltage R ", "" + ids[9]);
                        values.add(ids[1]);
                        values.add(ids[9]);

                    } catch (Exception e) {
                        Log.e("Unknown error", e.toString());
                    }
                }
                i+=1;
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


    }
}