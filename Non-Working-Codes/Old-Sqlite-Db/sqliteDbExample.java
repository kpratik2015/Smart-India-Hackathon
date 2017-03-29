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

    databaseHelper peopleDB;
    Button btnAddData,btnViewData,btnUpdateData,btnDeleteData;
    EditText etName,etEmail,etLocality,etID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        peopleDB = new databaseHelper(this);
        btnAddData = (Button)findViewById(R.id.btnAddData);
        etName = (EditText)findViewById(R.id.etNewName);
        etEmail = (EditText)findViewById(R.id.etNewEmail);
        etLocality = (EditText)findViewById(R.id.etNewLocality);
        btnViewData = (Button)findViewById(R.id.btnViewData);
        btnUpdateData = (Button)findViewById(R.id.btnID);
        etID = (EditText)findViewById(R.id.etID);
        btnDeleteData = (Button)findViewById(R.id.btnDelete);
        AddData();
        ViewData();
        UpdateData();
        DeleteData();

    }

    public void AddData()
    {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String locality = etLocality.getText().toString();

                boolean insertData = peopleDB.addData(name,email,locality);
                if(insertData==true)
                {
                    Toast.makeText(sqliteDbExample.this,"Data Entered Successfully",Toast.LENGTH_LONG).show();
                    etID.setText("");
                    etName.setText("");
                    etEmail.setText("");
                    etLocality.setText("");
                }
                else
                {
                    Toast.makeText(sqliteDbExample.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ViewData()
    {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = peopleDB.showData();

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
    public void UpdateData()
    {
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=etID.getText().toString().length();
                if(temp>0)
                {
                    boolean update = peopleDB.updateData(etID.getText().toString(),etName.getText().toString(),etEmail.getText().toString(),etLocality.getText().toString());
                    if(update==true)
                    {
                        Toast.makeText(sqliteDbExample.this,"Successfully updated data",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(sqliteDbExample.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(sqliteDbExample.this,"You must enter ID to update data",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData()
    {
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = etID.getText().toString().length();
                if(temp>0)
                {
                    Integer deleterow = peopleDB.deleteProduct(etID.getText().toString());
                    if(deleterow>0)
                    {
                        Toast.makeText(sqliteDbExample.this,"Successfully deleted data!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(sqliteDbExample.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(sqliteDbExample.this,"You must enter ID to delete data",Toast.LENGTH_LONG).show();
                }
            }
        });
        
    }
}
