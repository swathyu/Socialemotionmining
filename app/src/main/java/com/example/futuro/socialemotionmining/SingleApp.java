package com.example.futuro.socialemotionmining;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.path;

public class SingleApp extends AppCompatActivity {
    DBadapter adapter;
    TextView score, ss, emotion, det;
    Double scorei;
    emotionadapter eadapter;
    SharedPreferences pref;
    public static final String mypreference="mypref";
    public static final String name1 ="namekey";String uname;
    String Tdate;
     TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_app);
        pref =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if(pref.contains(name1)){
     uname=pref.getString(name1,"");
        }

       title = (TextView) findViewById(R.id.title);
        TextView cat = (TextView) findViewById(R.id.cat);
        score = (TextView) findViewById(R.id.score);
        TextView desc = (TextView) findViewById(R.id.desc);
        emotion = (TextView) findViewById(R.id.em);
        det = (TextView) findViewById(R.id.textView8);
        ss = (TextView) findViewById(R.id.sc);
        ImageView icon = (ImageView) findViewById(R.id.img);
        eadapter=new emotionadapter(this);
        Intent vi = getIntent();
        String s = vi.getStringExtra("str");
        adapter = new DBadapter(this);
        ArrayList<String> appdetails = adapter.fetchUser(s);
        String sum=eadapter.fetchcount(s);
        byte[] img = adapter.fetchImage(s);
        Log.d("fjgi", img.toString());
        Toast.makeText(getApplicationContext(), appdetails.get(0), Toast.LENGTH_LONG).show();
        icon.setImageBitmap(Utils.getImage(img));
        title.setText(appdetails.get(0));
        cat.setText(appdetails.get(1));
        score.setText(sum);
        desc.setText(appdetails.get(2));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
         Tdate = df.format(c);
        String username = "80ff86fc-1329-46c1-9b51-45dda8d987e9";
        String password = "WnQUuZcjVfLP";

        final ToneAnalyzer toneAnalyzer =
                new ToneAnalyzer("2017-07-01");
        toneAnalyzer.setUsernameAndPassword(username, password);

        Button analyzeButton = (Button) findViewById(R.id.post);
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                det.setVisibility(View.VISIBLE);
                final EditText userInput = (EditText) findViewById(R.id.comm);
                final String textToAnalyze = userInput.getText().toString();

                ToneOptions options = new ToneOptions.Builder()
                        .addTone(Tone.EMOTION)
                        .html(false).build();

                toneAnalyzer.getTone(textToAnalyze, options).enqueue(new ServiceCallback<ToneAnalysis>() {
                    @Override
                    public void onResponse(ToneAnalysis response) {
                        List<ToneScore> scores = response.getDocumentTone().getTones()
                                .get(0).getTones();

                        String detectedTones = "";
                        for (ToneScore score : scores) {
                            scorei = score.getScore();
                            if (score.getScore() > 0.5f) {
                                detectedTones += score.getName() + " ";

                            }
                        }

                        final String toastMessage = "The following emotions were detected:\n\n"
                                + detectedTones.toUpperCase();
                        final String re = detectedTones;
final String[] splitted=re.split("\\s+");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              //  Toast.makeText(getBaseContext(),
                                      //  toastMessage, Toast.LENGTH_LONG).show();
                                ImageView h = (ImageView) findViewById(R.id.imageView3);
                                if (splitted[0].trim().equalsIgnoreCase("Joy".trim())) {
                                  h.setImageDrawable(getResources().getDrawable(R.drawable.happy));
                              }  else if (splitted[0].trim().equalsIgnoreCase("Sadness".trim())) {
                                    h.setImageDrawable(getResources().getDrawable(R.drawable.sad));
                                }else if (splitted[0].trim().equalsIgnoreCase("Anger".trim())) {
                                    h.setImageDrawable(getResources().getDrawable(R.drawable.angry));
                                }else if (splitted[0].trim().equalsIgnoreCase("Disgust".trim())) {
                                    h.setImageDrawable(getResources().getDrawable(R.drawable.disgust));
                                }
                                else if (splitted[0].trim().equalsIgnoreCase("Fear".trim())) {
                                    h.setImageDrawable(getResources().getDrawable(R.drawable.fear));
                                }
                                ss.setText(String.valueOf(scorei));

                               emotion.setText(splitted[0]);
String tit=title.getText().toString();
                               Log.d("hjuhk",tit);
                                eadapter.insertEntry(tit, userInput.getText().toString(), re,String.valueOf(scorei),uname,Tdate);
                                //new AddNewPrediction().execute(tit, userInput.getText().toString(), re,String.valueOf(scorei));

                            }
                        });
                    }


                    public void onFailure(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }


    public void select(View v)
    {
        Intent in=new Intent(this,Comments.class);
        in.putExtra("a",title.getText());
        startActivity(in);
    }
}

