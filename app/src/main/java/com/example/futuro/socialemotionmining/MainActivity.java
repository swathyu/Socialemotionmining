package com.example.futuro.socialemotionmining;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
EditText search;
    String s;
    DBadapter adapter;
    SharedPreferences pref;
    public static final String mypreference="mypref";
    public static final String name1 ="namekey";
    ArrayAdapter<String> myAdapter;
    String[] item = new String[] {"Please search..."};
    CustomAutoCompleteView myAutoComplete;
    TextView sea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // CustomAutoCompleteView myAutoComplete;
        myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

        // add the listener so it will tries to suggest while the user types
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

        // set our adapter
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        myAutoComplete.setAdapter(myAdapter);
        pref =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Intent in=getIntent();
        String name=in.getStringExtra("name");
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(name1,name);
        editor.commit();
        sea=(TextView)findViewById(R.id.search);
        adapter=new DBadapter(this);
    }
    public void v(View v)
    {
        s=myAutoComplete.getText().toString();
        Log.d("aa",s);
        Intent vi= new Intent(this,Result.class);
        vi.putExtra("str",s);
        startActivity(vi);

    }
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = adapter.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

}
