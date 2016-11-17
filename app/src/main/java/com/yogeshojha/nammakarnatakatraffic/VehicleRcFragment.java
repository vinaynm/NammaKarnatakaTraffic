package com.yogeshojha.nammakarnatakatraffic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRcFragment extends Fragment {

    private AdView mAdView_rc;
    public AdRequest adRequest_rc;

    public VehicleRcFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_vehicle_rc, container, false);
        mAdView_rc = new AdView(getActivity());
        mAdView_rc.setAdUnitId("ca-app-pub-9002284518905519/5700240988");
        mAdView_rc.setAdSize(AdSize.BANNER);
        LinearLayout layoutrc = (LinearLayout) v.findViewById(R.id.layout_admob_rc);
        layoutrc.addView(mAdView_rc);
        AdRequest adRequest_rc = new AdRequest.Builder().build();
        mAdView_rc.loadAd(adRequest_rc);
        return v;
    }

}
