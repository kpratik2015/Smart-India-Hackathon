package com.pratikkataria.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Pat on 4/2/2017.
 */

public class ListViewNotification extends AppCompatActivity{

    public static ArrayList<String> items = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_list);

        ListView listView1 = (ListView) findViewById(R.id.list_view_notify);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);

        listView1.setAdapter(adapter);

    }

}
