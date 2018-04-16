package com.example.futuro.socialemotionmining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
CustomListView lv;
    DBadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent vi=getIntent();
        String s=vi.getStringExtra("str");
        adapter=new DBadapter(this);
        ArrayList<String> appdetails=adapter.fetchUser(s);
        byte[] img=adapter.fetchImage(s);
        Log.d("fjgi",img.toString());
        Toast.makeText(getApplicationContext(),appdetails.get(0),Toast.LENGTH_LONG).show();
        lv=new CustomListView(this,appdetails,img);
        ListView l=(ListView)findViewById(R.id.list);
        l.setAdapter(lv);
    }
}
