package com.example.futuro.socialemotionmining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
    public void admin(View v)
    {
        startActivity(new Intent(this,Admin_login.class));
    }
    public void user(View v)
    {
        startActivity(new Intent(this,UserLogin.class));
    }
}
