package com.yogeshojha.nammakarnatakatraffic;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;



/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRcFragment extends Fragment {

    private WebView mWebview;
    private ProgressBar mPbar = null;
    private AdView mAdView_rc;
    public VehicleRcFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_vehicle_rc, container, false);

        mWebview =  (WebView)v.findViewById(R.id.webview_rc);
        mPbar = (ProgressBar) v.findViewById(R.id.web_view_progress);
        mWebview.setVisibility(View.VISIBLE);
        mWebview.loadUrl("http://parivahan.gov.in/rcdlstatus");
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new rcWebViewClient());
        mAdView_rc = new AdView(getActivity());
        mAdView_rc.setAdUnitId(getResources().getString(R.string.rc_top_banner));
        mAdView_rc.setAdSize(AdSize.BANNER);
        LinearLayout layoutrc = (LinearLayout) v.findViewById(R.id.layout_admob_rc);
        layoutrc.addView(mAdView_rc);
        AdRequest adRequest_rc = new AdRequest.Builder().build();
        mAdView_rc.loadAd(adRequest_rc);
        return v;

    }
    private class rcWebViewClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mPbar.setVisibility(View.VISIBLE);
        }
        public void onPageFinished(WebView view, String url) {
            mPbar.setVisibility(View.GONE);
        }
    }

}
