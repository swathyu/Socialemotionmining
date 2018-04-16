package com.example.futuro.socialemotionmining;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AdminHome extends AppCompatActivity {
    EditText name, cate, desc;
    ImageView icon;
    String names, cat, dess, file;
    Button submit;
    byte[] inputData;
    DBadapter adapter;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        name = (EditText) findViewById(R.id.aname);
        cate = (EditText) findViewById(R.id.catname);
        desc = (EditText) findViewById(R.id.desc);
        icon = (ImageView)findViewById(R.id.icon);
adapter=new DBadapter(this);
    }

    public void browse(View v)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                pick();
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {
            pick();
            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AdminHome.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(AdminHome.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(AdminHome.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AdminHome.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
    public void pick() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Uri selectedImageUri = data.getData();
Log.d("vbjkgd",String.valueOf(selectedImageUri));
                if (null != selectedImageUri) {

                    // Saving to Database...
                    InputStream iStream = null;
                    try {
                        iStream = getContentResolver().openInputStream(selectedImageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                         inputData = Utils.getBytes(iStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    icon.setImageURI(selectedImageUri);
                }
            }
        }
    }
    public void insert(View v)
    {
        names=name.getText().toString();
        dess=desc.getText().toString();
        cat=cate.getText().toString();
        adapter.insertEntry(names,cat,dess,inputData);
        startActivity(new Intent(this,Index.class));
    }
}
