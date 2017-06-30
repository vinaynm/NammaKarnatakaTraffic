package com.yogeshojha.nammakarnatakatraffic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String PREFS_NAME;
    NavigationView navigationView = null;
    private AdView mAdView;
    AdRequest rcadRequest;
    private FirebaseAnalytics mFirebaseAnalytics;
    public InterstitialAd rcinterstitial;
    private PackageInfo pInfo;
    private Locale locale;
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
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle(getString(R.string.whatisnew_title));
            alertDialog.setMessage(getString(R.string.whatisnew_text));
            alertDialog.setPositiveButton(getString(R.string.Okay_button), new DialogInterface.OnClickListener() {
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
                .setTitle(getString(R.string.exit_title))
                .setMessage(getString(R.string.exit_text))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(R.string.no, null)
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
        setTitle(getString(R.string.app_name));
        //interstial ads load on any items selected
        rcinterstitial.loadAd(rcadRequest);
        rcinterstitial.show();
        if (id == R.id.nav_new_traffic) {
                    NewVehicleFragment fragment = new NewVehicleFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
                setTitle(getString(R.string.new_reg));
        } else if(id == R.id.nav_puc_check)
        {
            PUCFragment fragment = new PUCFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle(getString(R.string.puc_check));

        }
        else if (id == R.id.nav_licence_search)
        {
            DlCheckFragment fragment = new DlCheckFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle(getString(R.string.driving_lsc));

        }
        else if (id == R.id.nav_rc_check)
        {
            VehicleRcFragment fragment = new VehicleRcFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle(getString(R.string.rc_status));
        }
        else if (id == R.id.nav_top_500)
        {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle(getString(R.string.top_500_title));
            alertDialog.setMessage(getString(R.string.top_500_text));
            alertDialog.setPositiveButton(getString(R.string.download_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yogeshojha.com/nammakarnataka/download_top_500.php")));
                }
            });
            alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
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
            setTitle(getString(R.string.cautionary_sign));
        }
        else if(id == R.id.mandatory_sign)
        {
            mandatory fragment = new mandatory();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle(getString(R.string.mandatory_sign));
        }
        else if(id == R.id.informatory_sign)
        {
            informatory fragment = new informatory();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle(getString(R.string.informatory_sign));
        }
        else if(id == R.id.nav_ll_test)
        {
            TestFragment fragment = new TestFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
            setTitle(getString(R.string.learner_lsc));
        }
        else if(id == R.id.nav_about_us)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle(getString(R.string.about_title));
            alertDialog.setMessage(getString(R.string.about_text));
            alertDialog.setPositiveButton(getString(R.string.visit_website), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://yogeshojha.com")));
                }
            })
            .setNeutralButton(getString(R.string.email_dev), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"yogesh.ojha11@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Namma Karnakata Traffic App");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(MainActivity.this, getString(R.string.no_email_client), Toast.LENGTH_SHORT).show();
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
            setTitle(getString(R.string.blr_city_news));
        }
        else if(id == R.id.settings)
        {
            showChangeLangDialog();
        }
        else if(id == R.id.nav_support)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.yogeshojha.nammakarnatakatraffic")));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        dialogBuilder.setTitle(getString(R.string.language_setting));
        dialogBuilder.setMessage(getString(R.string.change_msg));
        dialogBuilder.setPositiveButton(getString(R.string.change), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                switch(langpos) {
                    case 0: //English
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                        setLangRecreate("en");
                        return;
                    case 1: //Kannada
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "kn").commit();
                        setLangRecreate("kn");
                        return;
                    default: //By default set to english
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                        setLangRecreate("en");
                        return;
                }
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

}
