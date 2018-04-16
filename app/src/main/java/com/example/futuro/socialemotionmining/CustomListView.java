package com.example.futuro.socialemotionmining;




import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.KEYGUARD_SERVICE;

public class CustomListView extends ArrayAdapter<String> {
    SharedPreferences sharedPreferences;
    private final Activity context;
    private final ArrayList<String>  app;
    private final byte[] imageId;
    boolean flag;
    public static final String mypreference="mypref";
    public static final String name4 ="key";
    public CustomListView(Activity context,
                          ArrayList<String> app, byte[] imageId) {
        super(context, R.layout.list_single, app);
        this.context = context;
        this.app = app;
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //sharedPreferences =ro.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
      //  LinearLayout featuresTable = (LinearLayout)rowView. findViewById(R.id.ll);
        rowView.setMinimumHeight(150);
//rowView.setBackgroundColor(Color.parseColor("#696969"));

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
     //  final CheckBox txtcb = (CheckBox) rowView.findViewById(R.id.cb);
        TextView cat = (TextView) rowView.findViewById(R.id.tx1);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(app.get(0));
        txtTitle.setTextSize(25);
        txtTitle.setTextColor(Color.parseColor("#000000"));
        txtTitle.setGravity(Gravity.CENTER);
        cat.setText(app.get(1));
        cat.setTextSize(25);
        cat.setTextColor(Color.parseColor("#000000"));
        cat.setGravity(Gravity.CENTER);
        imageView.setImageBitmap(Utils.getImage(imageId));
        //boolean b = sharedPreferences.getBoolean(name4, false);




        return rowView;

    }





}