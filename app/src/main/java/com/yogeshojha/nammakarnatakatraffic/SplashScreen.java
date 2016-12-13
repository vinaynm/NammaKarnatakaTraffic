package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 1/12/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {
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
        car.setVisibility(View.GONE);
        animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide);
        houseanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        cloudanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.floating);
        sunanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sunanim);
        roadanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roadanim);

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
                        Intent myIntent = new Intent(getBaseContext(), MainActivity.class); startActivity(myIntent);

                    }
                }, 1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}