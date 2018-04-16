package com.example.futuro.socialemotionmining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    TextView title;
DBadapter adapter;
    emotionadapter eadapter;
    ArrayList<String>data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
         title=(TextView)findViewById(R.id.title);
        TextView cat=(TextView)findViewById(R.id.cat);
        TextView score=(TextView)findViewById(R.id.score);
        ImageView icon=(ImageView)findViewById(R.id.icon);

        Intent vi=getIntent();
        String s=vi.getStringExtra("str");
        Log.d("app name",s);
        Log.d("app name",s.trim());
        adapter=new DBadapter(this);
        eadapter=new emotionadapter(this);

        ArrayList<String> appdetails=adapter.fetchUser(s.trim());
        String sum=eadapter.fetchcount(s);
        byte[] img=adapter.fetchImage(s);
        Log.d("fjgi",img.toString());
        Toast.makeText(getApplicationContext(),appdetails.get(0),Toast.LENGTH_LONG).show();
        icon.setImageBitmap(Utils.getImage(img));
title.setText(appdetails.get(0));
        cat.setText(appdetails.get(1));
        score.setText(sum);

    }
    public void zoom(View v)
    {
        Intent i=new Intent(this,SingleApp.class);
        i.putExtra("str",title.getText());
        startActivity(i);
    }
}
