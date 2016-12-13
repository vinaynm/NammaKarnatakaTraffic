package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 1/12/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView car = (ImageView) findViewById(R.id.car);
        ImageView house = (ImageView) findViewById(R.id.hotel);
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide);
        Animation houseanimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        house.startAnimation(houseanimation);
        car.startAnimation(animSlide);
    }
}