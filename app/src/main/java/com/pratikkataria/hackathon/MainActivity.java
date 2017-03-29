package com.pratikkataria.hackathon;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button sqliteBtn;
    Button voltBtn;
    databaseHelper meterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteBtn = (Button)findViewById(R.id.sqlitedb);
        sqliteBtn.setOnClickListener(this);

        voltBtn = (Button)findViewById(R.id.voltVal);
        voltBtn.setOnClickListener(this);

        meterDB = new databaseHelper(this);


        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("TokenOfDevice", token);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sqlitedb:
                DataPull();
                break;
            case R.id.voltVal:
                startActivity(new Intent(MainActivity.this,csvParseMain.class));
                break;
        }
    }

    public void DataPull(){
        Cursor data = meterDB.showData();

        if(data.getCount() == 0)
        {
            //message
            display("Error","No data found");
        }
        StringBuffer buffer = new StringBuffer();
        while(data.moveToNext())
        {
            buffer.append("Device: " + data.getString(0) + "\n");
            buffer.append("Voltage R: " + data.getString(1) + "\n");
        }
        display("All Stored Data: ",buffer.toString());

    }

    public void display(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
