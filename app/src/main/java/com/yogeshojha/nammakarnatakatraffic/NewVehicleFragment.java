package com.yogeshojha.nammakarnatakatraffic;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NewVehicleFragment extends Fragment implements View.OnClickListener {
    public RecyclerView mRecyclerView;
    public InterstitialAd interstitial;
    public String VehicleNo;
    public EditText inputstate;
    public EditText inputdstate;
    public EditText inputtdstate;
    public EditText inputdtdstate;
    private RecyclerView.Adapter mAdapter;
    public TextView inputVehicleTextView;
    AdRequest adRequest;
    private String URL;
    int count = 0;
    public TextView violatedtext;
    ProgressDialog mProgressDialog;
    public String finedetails = "";
    private RecyclerView.LayoutManager mLayoutManager;
    public TextView fineslistshow;
    public LinearLayout finecards;
    public TextView totalAmount;
    public LinearLayout amountLayout;
    public Button ButtonPayFine;
    public Button ButtonRateUs;
    View v;
    public FragmentManager fm;
    private int total;
    public static final String RupeeSymbol = "\u20B9";
    public static final String VIOLATED = "Total No of. Violations: ";
    public static final String No_FINE = "No Traffic Violations has been recorded for this vehicle number.\nHave a Happy and Safe ride";
    public final ArrayList<String> finearray = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fm = getActivity().getSupportFragmentManager();
        interstitial = new InterstitialAd(getActivity());
        interstitial.setAdUnitId(getString(R.string.interstitial_full_screen));
        v = inflater.inflate(R.layout.fragment_new_vehicle, container, false);
        adRequest = new AdRequest.Builder().build();
        final Button new_submit_btn = (Button) v.findViewById(R.id.submit_new);
        ButtonPayFine = (Button) v.findViewById(R.id.ButtonPayFine);
        ButtonRateUs = (Button) v.findViewById(R.id.ButtonRateUs);
        ButtonPayFine.setVisibility(View.GONE);
        ButtonRateUs.setVisibility(View.GONE);
        inputstate = (EditText) v.findViewById(R.id.stateka);
        inputdstate = (EditText) v.findViewById(R.id.asd);
        inputtdstate = (EditText) v.findViewById(R.id.asdt);
        inputdtdstate = (EditText) v.findViewById(R.id.asdtn);
        new_submit_btn.setOnClickListener(this);
        ButtonPayFine.setOnClickListener(this);
        ButtonRateUs.setOnClickListener(this);
        inputstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(inputstate.getText().toString().length()==2)     //size as per your requirement
                {
                    inputdstate.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        inputdstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(inputdstate.getText().toString().length()==2)     //size as per your requirement
                {
                    inputtdstate.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        inputtdstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(inputtdstate.getText().toString().length()==2)     //size as per your requirement
                {
                    inputdtdstate.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        inputdtdstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(inputdtdstate.getText().toString().length()==4)     //size as per your requirement
                {
                    new_submit_btn.performClick();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        inputdtdstate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    new_submit_btn.performClick();
                    return true;
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        //interstial ads
        interstitial.loadAd(adRequest);
        interstitial.show();
        //set cards
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.new_fine);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        finecards = (LinearLayout) getActivity().findViewById(R.id.finedetailslist);
        inputVehicleTextView = (TextView) getActivity().findViewById(R.id.inputVehicle);
        fineslistshow = (TextView) getActivity().findViewById(R.id.nofine);
        violatedtext = (TextView) getActivity().findViewById(R.id.totalviolations);
        amountLayout = (LinearLayout) getActivity().findViewById(R.id.TotalAmountLayout);
        amountLayout.setVisibility(View.GONE);
        totalAmount = (TextView) getActivity().findViewById(R.id.amount);
        total = 0;
        switch (v.getId()) {
            case R.id.submit_new:
                String ets;
                VehicleNo = inputstate.getText().toString().toUpperCase() + "-" +
                        inputdstate.getText().toString().toUpperCase() +
                        "-" +
                        inputtdstate.getText().toString().toUpperCase() +
                        "-" + inputdtdstate.getText().toString().toUpperCase();
                if (
                        inputstate.getText().toString().equals("")
                                ||
                                inputdstate.getText().toString().equals("")
                                ||
                                inputtdstate.getText().toString().equals("")
                                ||
                                inputdtdstate.getText().toString().equals("")
                        ) {
                    inputVehicleTextView.setVisibility(View.VISIBLE);
                    ets = "Sorry, Vehicle Name can not be empty";
                    inputVehicleTextView.setTextColor(Color.parseColor("#F44336"));
                    inputVehicleTextView.setText(ets);

                } else {
                    inputVehicleTextView.setTextColor(Color.parseColor("#757575"));
                    ets = "Vehicle No. : " + VehicleNo;
                    inputVehicleTextView.setText(ets);
                    URL = "http://yogeshojha.com/karnatakatraffic/?1=" + inputstate.getText().toString()
                            + "&2=" + inputdstate.getText().toString()
                            + "&3=" + inputtdstate.getText().toString() + "&4=" + inputdtdstate.getText().toString();
                    new FetchWebsiteData().execute();
                }
                break;
            case R.id.ButtonPayFine:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bangaloreone.gov.in/login.aspx")));
                break;
            case R.id.ButtonRateUs:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.yogeshojha.nammakarnatakatraffic")));
                break;
        }
    }
    public class FetchWebsiteData extends AsyncTask<Void, Void, Void>{
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading details for "+VehicleNo+"...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        public Void doInBackground(Void... params) {
            try {
                // Connect to website
                Document document = Jsoup.connect(URL)
                        .timeout(15000).get();
                finearray.clear();
                int count = 0;
                for (Element table : document.select("table.fines")) {
                    for (Element row : table.select("tr"))
                    {
                        Elements tds = row.select("td");
                        if (tds.size() == 6 ) {
                            finedetails = "Offence No: " + count + "\n" +
                                    "Date: " + tds.get(2).text()+"\n"+
                                    "Offence: " + tds.get(4).text() +"\n" +
                                    "Amount: ₹"+tds.get(5).text()+"\n" +
                                    "Notice Number: "+tds.get(1).text()+"\n"
                            ;
                            finearray.add(finedetails);
                            total += Integer.parseInt(tds.get(5).text());
                        }
                        count++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            if(finearray.size() == 0)
            {
                nofines(No_FINE);
            }
            else {
                fineexist(finearray, total);
            }
        }

    }
    public void nofines(String response)
    {
        rateus_dialog();
        ButtonRateUs.setVisibility(View.VISIBLE);
        violatedtext.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        inputVehicleTextView.setVisibility(View.VISIBLE);
        fineslistshow.setVisibility(View.VISIBLE);
        fineslistshow.setTextColor(Color.parseColor("#3F51B5"));
        fineslistshow.setText(response);
    }
    public void fineexist(ArrayList<String> arrayoffine, int TotalAmt)
    {
        rateus_dialog();
        ButtonPayFine.setVisibility(View.VISIBLE);
        ButtonRateUs.setVisibility(View.VISIBLE);
        inputVehicleTextView.setVisibility(View.VISIBLE);
        count++;
        finecards.setVisibility(View.VISIBLE);
        fineslistshow.setVisibility(View.GONE);
        mAdapter = new MyRecyclerViewAdapter(getDataSet(finearray));
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(mAdapter);
        violatedtext.setVisibility(View.VISIBLE);
        violatedtext.setText(VIOLATED + arrayoffine.size());
        amountLayout.setVisibility(View.VISIBLE);
        totalAmount.setText(RupeeSymbol+total);
    }
    private ArrayList<DataObject> getDataSet(ArrayList<String> array_of_fine) {
        ArrayList results = new ArrayList<>();
        for (int index = 0; index < array_of_fine.size(); index++) {

            DataObject obj = new DataObject(array_of_fine.get(index));
            results.add(index, obj);
        }
        return results;
    }
    public void rateus_dialog()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = dateFormat.format(new Date());
        DatabaseHandler db = new DatabaseHandler(getContext());
        int counnt_db = db.getRateCount();
        System.out.println(counnt_db);
        if (counnt_db == 0)
        {
            db.addRate(new RateClass(current_date,"no"));
            AlertDFragment alertdFragment = new AlertDFragment();
            alertdFragment.show(fm, "Rate Us");
        }
        else
        {
            RateClass result = db.getRate(1);
            String daydb = result.get_lastshown().substring(8,10);
            String currentday = current_date.substring(8,10);
            Boolean bool;
            bool = daydb.equals(currentday);
            if(!bool && (result.get_clickedon().equals("no")))
            {
                AlertDFragment alertdFragment = new AlertDFragment();
                alertdFragment.show(fm, "Rate Us");
            }
        }

    }
}