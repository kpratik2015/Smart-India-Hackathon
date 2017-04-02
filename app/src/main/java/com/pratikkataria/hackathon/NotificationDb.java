package com.pratikkataria.hackathon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Pat on 4/2/2017.
 */

public class NotificationDb extends AppCompatActivity implements View.OnClickListener{

    Button viewNotify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        viewNotify = (Button)findViewById(R.id.viewNotify);
        viewNotify.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.viewNotify:
                startActivity(new Intent(NotificationDb.this,ListViewNotification.class));
                break;

        }
    }
}
