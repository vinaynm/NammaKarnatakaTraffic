package com.yogeshojha.nammakarnatakatraffic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String PREFS_NAME;
    NavigationView navigationView = null;
    private AdView mAdView;
    AdRequest rcadRequest;
    private FirebaseAnalytics mFirebaseAnalytics;
    public InterstitialAd rcinterstitial;
    private PackageInfo pInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
             pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (Exception e)
        {

        }
        int verCode = pInfo.versionCode;
        PREFS_NAME = "FirstRunPref_Namma";
        PREFS_NAME = PREFS_NAME + verCode;
        //first run shared preference
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);//no flags set
        boolean firstRun = settings.getBoolean("firstRun",true);

        setContentView(R.layout.activity_main);
            //set the ads
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdView.loadAd(adRequest);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        NewVehicleFragment fragment = new NewVehicleFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //ads
        rcinterstitial = new InterstitialAd(MainActivity.this);
        rcinterstitial.setAdUnitId(getString(R.string.rc_nav_onclick));
        rcadRequest = new AdRequest.Builder().build();
        if(firstRun) {
            String verNumber = pInfo.versionName;
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("What is New???");
            alertDialog.setMessage("App Version: " + verNumber +"\nNew Updates\n***News Feature: Now you can read the news of Bangalore City Police right inside your App.***" +
                    "\n-Minor Changes: Vehicle Name with no third alphabet now supported\n" +
                    "-App Sized Decreased" +
                    "\n-App Crash On Menu Events Fixed" +
                    "-Minor Bug Fixes");
            alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", false);
            editor.commit();

        }
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exiting App")
                .setMessage("Are you sure you want exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setTitle("Namma Karnataka Traffic");
        //interstial ads load on any items selected
        rcinterstitial.loadAd(rcadRequest);
        rcinterstitial.show();
        if (id == R.id.nav_new_traffic) {
                    NewVehicleFragment fragment = new NewVehicleFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
                setTitle("New Registration Traffic Violation");
        } else if(id == R.id.nav_puc_check)
        {
            PUCFragment fragment = new PUCFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("PUC Check");

        }
        else if (id == R.id.nav_licence_search)
        {
            DlCheckFragment fragment = new DlCheckFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Driving License Status");

        }
        else if (id == R.id.nav_rc_check)
        {
            VehicleRcFragment fragment = new VehicleRcFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Vehicle RC Status");
        }
        else if (id == R.id.nav_top_500)
        {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Download Top 500 Traffic Violators");
            alertDialog.setMessage("You are aboot to download list of top 500 Traffic Violators. " +
                    "\nYou will be redirected to chrome to download the list of Top 500 vehicles with Multiple Traffic Violations. \n" +
                    "List is provided by Bangalore Traffic Police.");
            alertDialog.setPositiveButton("Download now", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yogeshojha.com/nammakarnataka/download_top.php")));
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
        else if(id == R.id.cautionary_sign)
        {
            cautionary fragment = new cautionary();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Cautionary Signs");
        }
        else if(id == R.id.mandatory_sign)
        {
            mandatory fragment = new mandatory();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Mandatory Signs");
        }
        else if(id == R.id.informatory_sign)
        {
            informatory fragment = new informatory();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Mandatory Signs");
        }
        else if(id == R.id.nav_ll_test)
        {
            TestFragment fragment = new TestFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Learner's Licence Practice Test");
        }
        else if(id == R.id.nav_about_us)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("About");
            alertDialog.setMessage("Namma Karnataka Traffic is not an official app of Karnataka Traffic Police\n" +
                    "Namma Karnataka Traffic helps you check traffic violations, view RTO details, check driving license status, check PUC status and many more." +
                    "\nYou can now practice LL test too!!!\n" +
                    "In a nutshell Namma Karnataka Traffic is a RTO in your Pocket\n" +
                    "Developer: Yogesh Ojha\nEmail: yogesh@linux.com\nWebsite:http://yogeshojha.com\nImages credits: Freepik, Unsplash\n\nPlease feel free to write me an email regarding any new features that you want to me implement, or any bugs or just to say a hello. \nI will be happy to reply your email.");
            alertDialog.setPositiveButton("Visit Website", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://yogeshojha.com")));
                }
            });
            alertDialog.setNeutralButton("Email Developer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"yogesh.ojha11@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Namma Karnakata Traffic App");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.show();

        }
        else if(id == R.id.nav_news)
        {
            NewsFragment fragment = new NewsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle("Bangalore City Police News");
        }
        else if(id == R.id.nav_support)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.yogeshojha.nammakarnatakatraffic")));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
