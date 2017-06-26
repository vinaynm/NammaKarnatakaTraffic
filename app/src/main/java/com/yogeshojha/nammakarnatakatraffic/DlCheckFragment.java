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
public class DlCheckFragment extends Fragment {

    private WebView mWebview;
    private ProgressBar mPbar = null;
    private AdView mAdView_dl;
    public DlCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_dl_check, container, false);

        mWebview =  (WebView)v.findViewById(R.id.webview_dl);
        mPbar = (ProgressBar) v.findViewById(R.id.web_view_progress_dl);
        mWebview.setVisibility(View.VISIBLE);
        mWebview.loadUrl("https://parivahan.gov.in/rcdlstatus/?pur_cd=101");
        // Enable Javascript
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new dlWebViewClient());
        mAdView_dl = new AdView(getActivity());
        mAdView_dl.setAdUnitId(getResources().getString(R.string.dl_banner));
        mAdView_dl.setAdSize(AdSize.BANNER);
        LinearLayout layoutrc = (LinearLayout) v.findViewById(R.id.layout_admob_dl);
        layoutrc.addView(mAdView_dl);
        AdRequest adRequest_rc = new AdRequest.Builder().build();
        mAdView_dl.loadAd(adRequest_rc);
        return v;
    }
    private class dlWebViewClient extends WebViewClient
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
