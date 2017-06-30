package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 1/12/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class SplashScreen extends Activity implements ConnectivityReceiver.ConnectivityReceiverListener{
    /**
     * Called when the activity is first created.
     */
    public ImageView car;
    public ImageView house;
    public ImageView cloud;
    public ImageView cloud1;
    public ImageView road;
    public ImageView sun;
    public Animation houseanimation;
    public Animation cloudanimation;
    public Animation roadanim;
    public Animation sunanimation;
    public Animation animSlide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //read and set language
        SharedPreferences settings_lang = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = settings_lang.getString("LANG", "");
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        car = (ImageView) findViewById(R.id.car);
        house = (ImageView) findViewById(R.id.hotel);
        cloud = (ImageView) findViewById(R.id.cloud);
        cloud1 = (ImageView) findViewById(R.id.cloud2);
        sun = (ImageView) findViewById(R.id.sun);
        road = (ImageView) findViewById(R.id.road);
        startanim();
    }

    public void startanim() {
        ImageView comp = (ImageView) findViewById(R.id.namma);
        TextView name = (TextView) findViewById(R.id.logoname);
        car.setVisibility(View.GONE);
        animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide);
        houseanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        cloudanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.floating);
        sunanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sunanim);
        roadanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roadanim);
        Animation logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

        name.startAnimation(logo);
        comp.startAnimation(logo);
        sun.startAnimation(sunanimation);
        cloud.startAnimation(cloudanimation);
        cloud1.startAnimation(cloudanimation);
        house.startAnimation(houseanimation);

        houseanimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                car.setVisibility(View.VISIBLE);
                car.startAnimation(animSlide);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        checkConnection();
                    }
                }, 1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {
        if (isConnected) {
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class); startActivity(myIntent);

        } else {
            Intent myIntent = new Intent(getBaseContext(), NoInternet.class); startActivity(myIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        ListenConnection.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
