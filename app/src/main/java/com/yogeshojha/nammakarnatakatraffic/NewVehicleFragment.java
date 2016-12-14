package com.yogeshojha.nammakarnatakatraffic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewVehicleFragment extends Fragment {
    private AdView mAdView;
    public EditText inputstate;
    public EditText inputdstate;
    public EditText inputtdstate;
    public EditText inputdtdstate;
    public String VehicleNo;

    public NewVehicleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //frags
        inputstate = (EditText) getActivity().findViewById(R.id.stateka);
        inputdstate = (EditText) getActivity().findViewById(R.id.asd);
        inputtdstate = (EditText) getActivity().findViewById(R.id.asdt);
        inputdtdstate = (EditText) getActivity().findViewById(R.id.asdtn);
        Button submitbtn = (Button) getActivity().findViewById(R.id.submit_new);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    System.out.println("Empty");
                }
            }
        });
        //
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_vehicle, container, false);
    }

}
