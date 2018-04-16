package com.example.futuro.socialemotionmining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_login extends AppCompatActivity {
EditText un,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        un=(EditText)findViewById(R.id.editText2);
        pass=(EditText)findViewById(R.id.editText3);
    }
    public void getAdminhome(View v)
    {
        String uname=un.getText().toString();
        String passd=pass.getText().toString();
        if(uname.trim().equals("admin") && passd.trim().equals("admin123"))
        {
            startActivity(new Intent(this,AdminHome.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Not valid",Toast.LENGTH_LONG).show();
        }

    }
}
