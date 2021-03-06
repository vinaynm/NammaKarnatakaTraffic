package com.yogeshojha.nammakarnatakatraffic;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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


public class NewVehicleFragment extends Fragment implements View.OnClickListener {
    public RecyclerView mRecyclerView;
    public InterstitialAd interstitial, video_ad;
    public String VehicleNo;
    public EditText inputstate;
    public EditText inputdstate;
    public EditText inputtdstate;
    public EditText inputdtdstate;
    private RecyclerView.Adapter mAdapter;
    public TextView inputVehicleTextView;
    AdRequest adRequest, adRequest_video;
    private String URL;
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
    private AdView mAdView_traffic_fine;
    View v;
    public FragmentManager fm;
    private int total;
    private boolean flag = true;
    public static final String RupeeSymbol = "\u20B9";
    public static String VIOLATED;
    public static String No_FINE;
    public final ArrayList<String> finearray = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VIOLATED = getString(R.string.total_violation);
        No_FINE = getString(R.string.no_violation);
        fm = getActivity().getSupportFragmentManager();
        interstitial = new InterstitialAd(getActivity());
        interstitial.setAdUnitId(getString(R.string.submit_onclick_new_frag));

        //only video ads
        video_ad = new InterstitialAd(getActivity());
        video_ad.setAdUnitId(getString(R.string.video_ad_new_fragment));

        //request interstitial ads
        adRequest = new AdRequest.Builder().build();

        //request video ads

        v = inflater.inflate(R.layout.fragment_new_vehicle, container, false);
        //ads
        //banner ads
        mAdView_traffic_fine = new AdView(getActivity());
        mAdView_traffic_fine.setAdUnitId(getResources().getString(R.string.traffic_fine));
        mAdView_traffic_fine.setAdSize(AdSize.BANNER);
        LinearLayout layoutnew = (LinearLayout) v.findViewById(R.id.layout_admob_traffic_fine);
        layoutnew.addView(mAdView_traffic_fine);
        //
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
                if(inputstate.getText().toString().length()==2)
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
                if(inputdstate.getText().toString().length()==2)
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
                if(inputtdstate.getText().toString().length()==2)
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
                if(inputdtdstate.getText().toString().length()==4)
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
                //load ads
                AdRequest adRequest_traffic = new AdRequest.Builder().build();
                mAdView_traffic_fine.loadAd(adRequest_traffic);
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
                                inputdtdstate.getText().toString().equals("")
                        ) {
                    inputVehicleTextView.setVisibility(View.VISIBLE);
                    ets = getString(R.string.vehicle_empty);
                    inputVehicleTextView.setTextColor(Color.parseColor("#F44336"));
                    inputVehicleTextView.setText(ets);
                    ButtonPayFine.setVisibility(View.GONE);
                    finecards.setVisibility(View.GONE);
                    violatedtext.setVisibility(View.GONE);
                    fineslistshow.setVisibility(View.GONE);

                } else {
                    inputVehicleTextView.setVisibility(View.VISIBLE);
                    inputVehicleTextView.setTextColor(Color.parseColor("#757575"));
                    ets = getString(R.string.vehicle_no) + VehicleNo;
                    inputVehicleTextView.setText(ets);
                    URL = "http://yogeshojha.com/nammakarnataka/violations.php/?1=" + inputstate.getText().toString()
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
    private class FetchWebsiteData extends AsyncTask<Void, Void, Void>{
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading)+"\n"+VehicleNo+"...");
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
                            finedetails = getString(R.string.offence_no) + count + "\n" +
                                    getString(R.string.date) + tds.get(2).text()+"\n"+
                                    getString(R.string.time) + tds.get(3).text().substring(0,2)+":"+tds.get(3).text().substring(2,4)+"\n"+
                                    getString(R.string.offence) + tds.get(4).text() +"\n" +
                                    getString(R.string.amount)+tds.get(5).text()+"\n" +
                                    getString(R.string.notice_no)+tds.get(1).text()+"\n"
                            ;
                            finearray.add(finedetails);
                            total += Integer.parseInt(tds.get(5).text());
                        }
                        count++;
                    }
                }
                flag = true;
            } catch (IOException e) {
                flag = false;
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
        //load video ads on every click
        adRequest_video = new AdRequest.Builder().build();
        video_ad.loadAd(adRequest_video);
        video_ad.show();
        if(!flag)
        {
            Toast.makeText(v.getContext(), getString(R.string.error_server),
                    Toast.LENGTH_LONG).show();
        }
        else {
            rateus_dialog();
            ButtonRateUs.setVisibility(View.VISIBLE);
            ButtonPayFine.setVisibility(View.GONE);
            violatedtext.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            inputVehicleTextView.setVisibility(View.VISIBLE);
            fineslistshow.setVisibility(View.VISIBLE);
            fineslistshow.setTextColor(Color.parseColor("#3F51B5"));
            fineslistshow.setText(response);

        }
    }
    public void fineexist(ArrayList<String> arrayoffine, int TotalAmt)
    {
        //load video ads on every click
        adRequest_video = new AdRequest.Builder().build();
        video_ad.loadAd(adRequest_video);
        video_ad.show();
        rateus_dialog();
        ButtonPayFine.setVisibility(View.VISIBLE);
        ButtonRateUs.setVisibility(View.VISIBLE);
        inputVehicleTextView.setVisibility(View.VISIBLE);
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
        if (counnt_db == 0)
        {
            db.addRate(new RateClass(current_date,"no"));
            AlertDFragment alertdFragment = new AlertDFragment();
            alertdFragment.show(fm, getString(R.string.rateus_title));
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
                alertdFragment.show(fm, getString(R.string.rateus_title));
            }
        }

    }
}