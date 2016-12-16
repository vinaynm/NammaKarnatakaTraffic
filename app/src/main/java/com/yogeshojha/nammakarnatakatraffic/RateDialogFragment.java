package com.yogeshojha.nammakarnatakatraffic;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by y0g3sh on 16/12/16.
 */

public class RateDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rateus_layout, container, false);
        getDialog().setTitle("Simple Dialog");
        return rootView;
    }
}
