package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 16/12/16.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlertDFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.rate)
                .setTitle("Rate Us")
                .setMessage("We just helped you finding your traffic fine list. We hope that helped. Would you like to rate us?")
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.yogeshojha.nammakarnatakatraffic")));
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String current_date = dateFormat.format(new Date());
                        RateClass newval = new RateClass();
                        newval._id = 1;
                        newval._lastshown = current_date;
                        newval._clickedon = "sure";
                        db.updateRate(newval);
                    }
                })
                .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String current_date = dateFormat.format(new Date());
                        RateClass newval = new RateClass();
                        newval._id = 1;
                        newval._lastshown = current_date;
                        newval._clickedon = "no";
                        db.updateRate(newval);
                    }
                }).create();
    }
}