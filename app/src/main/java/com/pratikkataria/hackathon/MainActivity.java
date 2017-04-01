package com.pratikkataria.hackathon;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button sqliteBtn;
    Button voltBtn;
    Button mapBtn;
    databaseHelper meterDB;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = "MainActivity";
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteBtn = (Button)findViewById(R.id.sqlitedb);
        sqliteBtn.setOnClickListener(this);

        voltBtn = (Button)findViewById(R.id.voltVal);
        voltBtn.setOnClickListener(this);

        mapBtn = (Button)findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(this);

        //this.deleteDatabase("meter.db");

        mAuth = FirebaseAuth.getInstance();

        meterDB = new databaseHelper(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReferenceFromUrl("gs://hackathon-project-37c69.appspot.com/").child("MeterDeviceReport.csv");

        //mStorageRef = FirebaseStorage.getInstance().getReference();

        File storagePath = new File(Environment.getExternalStorageDirectory(), "downloadmanager");
// Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
            Log.e("Directory","Not found!");
        }
        final long ONE_MEGABYTE = 1024 * 1024;
        //download file as a byte array
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.e("Download","Success");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("Download","Failure");
            }
        });

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("TokenOfDevice", " "+ token);

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
            case R.id.mapBtn:
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                Intent i = new Intent(MainActivity.this, EmailPasswordActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
