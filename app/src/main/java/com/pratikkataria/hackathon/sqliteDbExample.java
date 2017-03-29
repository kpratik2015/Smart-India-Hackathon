package com.pratikkataria.hackathon;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Pat on 3/29/2017.
 */



public class sqliteDbExample extends AppCompatActivity {

    databaseHelper meterDB;
    Button btnAddData,btnViewData,btnUpdateData,btnDeleteData;
    EditText etName,etEmail,etLocality,etID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sqlite);

        meterDB = new databaseHelper(this);
        ViewData();
    }
    
    public void ViewData()
    {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = meterDB.showData();

                if(data.getCount() == 0)
                {
                    //message
                    display("Error","No data found");
                }
                StringBuffer buffer = new StringBuffer();
                while(data.moveToNext())
                {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("NAME: " + data.getString(1) + "\n");
                    buffer.append("EMAIL: " + data.getString(2) + "\n");
                    buffer.append("LOCALITY: " + data.getString(3) + "\n");
                    //display("All Stored Data: ",buffer.toString());
                }
                display("All Stored Data: ",buffer.toString());

            }
        });
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
