package com.example.futuro.socialemotionmining;
import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;




public class UserLogin extends AppCompatActivity implements OnClickListener,
        GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener {
String personname;
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;


    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mShouldResolve;

    private ConnectionResult connectionResult;

    private SignInButton signInButton;
    private Button signOutButton,Me;
    private TextView tvName, tvMail, tvNotSignedIn;
    private ImageView imgProfilePic;
    private LinearLayout viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signOutButton = (Button) findViewById(R.id.sign_out_button);
        tvName = (TextView) findViewById(R.id.tvName);
        tvMail = (TextView) findViewById(R.id.tvMail);
        tvNotSignedIn = (TextView) findViewById(R.id.notSignedIn_tv);
        viewContainer = (LinearLayout) findViewById(R.id.text_view_container);
        Me=(Button)findViewById(R.id.me);
        Me.setVisibility(View.INVISIBLE);

        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    private void resolveSignInError() {
        if (connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {

            connectionResult = result;

            if (mShouldResolve) {

                resolveSignInError();
            }
        }

    }

    /*
    onConnectionFailed() was started with startIntentSenderForResult and the code RC_SIGN_IN,
    we can capture the result inside Activity.onActivityResult.
     */
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mShouldResolve = false;
        if(isContactPermissionGranted()) {
            try {
                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                    Person person = Plus.PeopleApi
                            .getCurrentPerson(mGoogleApiClient);
                  personname = person.getDisplayName();
                    String personPhotoUrl = person.getImage().getUrl();
                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                    tvName.setText(personname);
                    tvMail.setText(email);

                    personPhotoUrl = personPhotoUrl.substring(0,
                            personPhotoUrl.length() - 2)
                            + PROFILE_PIC_SIZE;

                    new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

                    Toast.makeText(getApplicationContext(),
                            "You are Logged In " + personname, Toast.LENGTH_LONG).show();
                    Me.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Couldnt Get the Person Info", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"no t allowed",Toast.LENGTH_LONG).show();
        }
        signOutUI();

    }
public void sign(View v)
{
    Intent in=new Intent(this,MainActivity.class);
    in.putExtra("name",personname);
    startActivity(in);
}
    private void signOutUI() {
        signInButton.setVisibility(View.GONE);
        tvNotSignedIn.setVisibility(View.GONE);
        signOutButton.setVisibility(View.VISIBLE);
        Me.setVisibility(View.VISIBLE);
        viewContainer.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.VISIBLE);
    }

    private void signInUI() {
        signInButton.setVisibility(View.VISIBLE);
        tvNotSignedIn.setVisibility(View.VISIBLE);
        signOutButton.setVisibility(View.GONE);
        viewContainer.setVisibility(View.GONE);
    }




    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        signInUI();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                onSignInClicked();
                break;
            case R.id.sign_out_button:
                onSignOutClicked();
                break;
        }
    }


    private void onSignInClicked() {
        if (!mGoogleApiClient.isConnecting()) {
            mShouldResolve = true;
            resolveSignInError();
        }
    }


    private void onSignOutClicked() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            signInUI();
        }
    }


    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public boolean isContactPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.GET_ACCOUNTS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("perm", "Permission is granted");
                return true;
            } else {

                Log.v("perm", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("perm", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("perm", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }


    }
}