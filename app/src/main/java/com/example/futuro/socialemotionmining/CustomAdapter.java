package com.example.futuro.socialemotionmining;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Model> spacecrafts;

    public CustomAdapter(Context c, ArrayList<Model> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int i) {
        return spacecrafts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }

        final Model s= (Model) this.getItem(i);

        ImageView img= (ImageView) view.findViewById(R.id.spacecraftImg);
        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        TextView propTxt= (TextView) view.findViewById(R.id.propellantTxt);
        TextView date= (TextView) view.findViewById(R.id.date);
        TextView score= (TextView) view.findViewById(R.id.score);

        nameTxt.setText(s.getName());
        propTxt.setText(s.getComments());
        img.setImageResource(s.getImage());
        date.setText(s.getDate());
        score.setText(s.getScore());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, s.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}