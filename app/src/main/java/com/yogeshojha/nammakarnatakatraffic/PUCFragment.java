package com.yogeshojha.nammakarnatakatraffic;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PUCFragment extends Fragment implements View.OnClickListener {
    private EditText puc_inputstate;
    private EditText puc_inputdstate;
    private EditText puc_inputtdstate;
    public EditText puc_inputdtdstate;
    private Handler puc_handler;
    private Spinner puc_veh_type_spinner;
    private String VehicleNo;
    private TextView inputVehicleTextView;
    private String URL;
    private String veh_type_string;
    private AdView mAdView_puc;
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
        puc_inputdtdstate.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(puc_inputdtdstate.getText().toString().length()==4)
                {
                    puc_veh_type_spinner.performClick();
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
        puc_inputdtdstate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    puc_veh_type_spinner.performClick();
                    return true;
                }
                return false;
            }
        });
        return v;
    }
    public void onClick(View v) {
        inputVehicleTextView = (TextView) getActivity().findViewById(R.id.puc_inputVehicle);
        switch (v.getId()) {
            case R.id.puc_submit:
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
                    inputVehicleTextView.setTextColor(Color.parseColor("#757575"));
                    ets = "Vehicle No. : " + VehicleNo;
                    inputVehicleTextView.setText(ets);
                    URL = "http://yogeshojha.com/nammakarnataka/puc.php/?1=" + puc_inputstate.getText().toString()
                            + "&2=" + puc_inputdstate.getText().toString()
                            + "&3=" + puc_inputtdstate.getText().toString() + "&4=" + puc_inputdtdstate.getText().toString();
                }
                    break;
        }
    }
}
