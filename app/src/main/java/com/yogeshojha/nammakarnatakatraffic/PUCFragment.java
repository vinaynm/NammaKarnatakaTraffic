package com.yogeshojha.nammakarnatakatraffic;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import java.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PUCFragment extends Fragment implements View.OnClickListener {
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText puc_inputstate;
    private EditText puc_inputdstate;
    private EditText puc_inputtdstate;
    public EditText puc_inputdtdstate;
    private Handler puc_handler;
    public TextView puclistshow;
    public LinearLayout pucCards;
    private Spinner puc_veh_type_spinner;
    private String VehicleNo;
    private TextView inputVehicleTextView;
    private String URL;
    public String veh_type_string;
    public String pucdetails = "";
    private AdView mAdView_puc;
    public LinearLayout finecards;
    ProgressDialog pucProgressDialog;
    private Button downloadcert;
    private String pid;
    public final ArrayList<String> pucarray = new ArrayList<String>();
    private Map<String, String> date_pid = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_puc, container, false);
        //spinner
        puc_handler = new Handler();
        puc_veh_type_spinner = (Spinner) v.findViewById(R.id.puc_spinner_layout_veh_type);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.veh_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        puc_veh_type_spinner.setAdapter(adapter);
        //banner ads
        mAdView_puc = new AdView(getActivity());
        mAdView_puc.setAdUnitId(getResources().getString(R.string.puc_banner));
        mAdView_puc.setAdSize(AdSize.BANNER);
        LinearLayout layoutrc = (LinearLayout) v.findViewById(R.id.layout_admob_rc);
        layoutrc.addView(mAdView_puc);
        AdRequest adRequest_rc = new AdRequest.Builder().build();
        mAdView_puc.loadAd(adRequest_rc);
        //
        final Button puc_submit_btn = (Button) v.findViewById(R.id.puc_submit);
        puc_submit_btn.setOnClickListener(this);
        downloadcert = (Button) v.findViewById(R.id.Buttondownloadpuc);
        downloadcert.setOnClickListener(this);
        puc_inputstate = (EditText) v.findViewById(R.id.puc_stateka);
        puc_inputdstate = (EditText) v.findViewById(R.id.puc_asd);
        puc_inputtdstate = (EditText) v.findViewById(R.id.puc_asdt);
        puc_inputdtdstate = (EditText) v.findViewById(R.id.puc_asdtn);
        puc_inputstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(puc_inputstate.getText().toString().length()==2)
                {
                    puc_inputdstate.requestFocus();
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
        puc_inputdstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(puc_inputdstate.getText().toString().length()==2)
                {
                    puc_inputtdstate.requestFocus();
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
        puc_inputtdstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(puc_inputtdstate.getText().toString().length()==2)
                {
                    puc_inputdtdstate.requestFocus();
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
    public void onClick(View v) {
        puclistshow = (TextView) getActivity().findViewById(R.id.nopuc);
        //set cards
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.puc_rec);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        finecards = (LinearLayout) getActivity().findViewById(R.id.pucdetailslist);
        //
        inputVehicleTextView = (TextView) getActivity().findViewById(R.id.puc_inputVehicle);
        inputVehicleTextView.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.puc_submit:
                veh_type_string = puc_veh_type_spinner.getSelectedItem().toString();
                String ets;
                VehicleNo = puc_inputstate.getText().toString().toUpperCase() + "-" +
                        puc_inputdstate.getText().toString().toUpperCase() +
                        "-" +
                        puc_inputtdstate.getText().toString().toUpperCase() +
                        "-" + puc_inputdtdstate.getText().toString().toUpperCase();
                if (
                        puc_inputstate.getText().toString().equals("")
                                ||
                                puc_inputdstate.getText().toString().equals("")
                                ||
                                puc_inputtdstate.getText().toString().equals("")
                                ||
                                puc_inputdtdstate.getText().toString().equals("")
                        ) {
                    inputVehicleTextView.setVisibility(View.VISIBLE);
                    ets = "Sorry, Vehicle Name can not be empty";
                    inputVehicleTextView.setTextColor(Color.parseColor("#F44336"));
                    inputVehicleTextView.setText(ets);

                } else {
                    inputVehicleTextView.setVisibility(View.VISIBLE);
                    inputVehicleTextView.setTextColor(Color.parseColor("#757575"));
                    ets = "Vehicle No. : " + VehicleNo;
                    inputVehicleTextView.setText(ets);
                    URL = "http://yogeshojha.com/nammakarnataka/puc.php/?1=" + puc_inputstate.getText().toString()
                            + "&2=" + puc_inputdstate.getText().toString()
                            + "&3=" + puc_inputtdstate.getText().toString() + "&4=" + puc_inputdtdstate.getText().toString()
                            +"&type="+veh_type_string.charAt(0);
                    System.out.println(URL);
                    new FetchPucDetails().execute();
                }
                    break;
            case R.id.Buttondownloadpuc:
                //System.out.println(date_pid);
                String[] keys = new String[date_pid.size()];
                String[] values = new String[date_pid.size()];
                int index = 0;
                for (Map.Entry<String, String> mapEntry : date_pid.entrySet()) {
                    keys[index] = mapEntry.getKey();
                    values[index] = mapEntry.getValue();
                    index++;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                String dateInString;
                String tempdate;
                Date[] datearray = new Date[keys.length];
                for(int j = 0; j < keys.length; j++) {
                    tempdate = keys[j];
                    dateInString = tempdate.replaceAll("\\s+", "-");
                    try {
                        Date date = formatter.parse(dateInString);
                        datearray[j] = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(datearray);
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
//                        ("http://www.karnatakapuc.in/"+pid.charAt(0)+"_test.aspx?Spuc="+pid+"&flag=1")));

                break;
        }
    }
    private class FetchPucDetails extends AsyncTask<Void, Void, Void> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            pucProgressDialog = new ProgressDialog(getActivity());
            pucProgressDialog.setMessage("Please Wait...\nLoading details for "+VehicleNo+"...");
            pucProgressDialog.setIndeterminate(false);
            pucProgressDialog.show();
        }
        @Override
        public Void doInBackground(Void... params) {
            try {
                // Connect to website
                Document document = Jsoup.connect(URL)
                        .timeout(15000).get();
                pucarray.clear();
                if (veh_type_string.equals("Petrol Vehicle"))
                {
                    date_pid.clear();
                    for (Element table : document.select("table.petrol")) {
                        for (Element row : table.select("tr"))
                        {
                            Elements tds = row.select("td");
                            if (tds.size() > 0 ) {
                                pucdetails = "Pucc No: " + tds.get(0).text() + "\n" +
                                        "Center Name: " + tds.get(1).text() + "\n" +
                                        "Vehicle Make: " + tds.get(3).text() + "\n" +
                                        "Model: " + tds.get(4).text() + "\n" +
                                        "Category: " + tds.get(5).text() + "\n" +
                                        "Engine Type: " + tds.get(6).text() + "\n" +
                                        "Fuel: " + tds.get(9).text() + "\n" +
                                        "Test Done On: " + tds.get(10).text() + " " + tds.get(11).text()+ "\n" +
                                        "Valid Date: " + tds.get(12).text() + "\n" +
                                        "P_CO: " + tds.get(13).text() + " P_COM: " + tds.get(14).text()
                                        + " P_HC: " + tds.get(15).text()
                                        + " P_HCM: " + tds.get(16).text() +
                                        " G_CO: " + tds.get(17).text() + " G_HCM: " + tds.get(18).text() + "\n" +
                                        "Result: " + tds.get(19).text()+"ed" + "\n"  ;
                                pucarray.add(pucdetails);
                                date_pid.put(tds.get(12).text(),tds.get(0).text());
                            }
                        }

                    }
                }
                else
                {
                    date_pid.clear();
                    for (Element table : document.select("table.diesel")) {
                        for (Element row : table.select("tr"))
                        {
                            Elements tds = row.select("td");
                            if (tds.size() > 0 ) {
                                pucdetails = "Pucc No: " + tds.get(0).text() + "\n" +
                                        "Make: " + tds.get(2).text() + "\n"+
                                        "Category: " + tds.get(3).text() + "\n"+
                                        "Tested on: " + tds.get(5).text() + " " + tds.get(6).text() + "\n"+
                                        "Validity: " + tds.get(7).text() + "\n"+
                                        "HSU Mean: " + tds.get(8).text() + "\n"+
                                        "K Mean: " + tds.get(9).text() + "\n"+
                                        "Oil Temp Mean: " + tds.get(10).text() + "\n"+
                                        "RPM Max Mean: " + tds.get(11).text() + "\n"+
                                        "RPM Min Mean: " + tds.get(12).text() + "\n"+
                                        "Result: " + tds.get(13).text()+"ed" + "\n"
                                ;
                                pucarray.add(pucdetails);
                                date_pid.put(tds.get(7).text(),tds.get(0).text());
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed");
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onPostExecute(Void result) {
            pucProgressDialog.dismiss();
            if (pucarray.size() != 0)
            {
                pucfound(pucarray);
            }
            else
            {
                pucnotfound();
            }
        }

    }
    public void pucnotfound()
    {
        downloadcert.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        puclistshow.setVisibility(View.VISIBLE);
        puclistshow.setTextColor(Color.parseColor("#3F51B5"));
        puclistshow.setText("Sorry No records found, please check your vehicle number or Vehicle Type. Ensure either Petrol Vehicle or Diesel Vehicle is checked.");
    }
    public void pucfound(ArrayList<String> arrayofpuc)
    {
        downloadcert.setVisibility(View.VISIBLE);
        puclistshow.setVisibility(View.GONE);
        finecards.setVisibility(View.VISIBLE);
        mAdapter = new MyRecyclerViewPuc(getDataSet(arrayofpuc));
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(mAdapter);
    }
    private ArrayList<DataObject> getDataSet(ArrayList<String> array_of_puc) {
        ArrayList results = new ArrayList<>();
        for (int index = 0; index < array_of_puc.size(); index++) {

            DataObject obj = new DataObject(array_of_puc.get(index));
            results.add(index, obj);
        }
        return results;
    }
}
