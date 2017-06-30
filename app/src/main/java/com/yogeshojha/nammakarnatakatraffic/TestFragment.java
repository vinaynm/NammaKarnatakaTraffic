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

public class TestFragment extends Fragment {
    private WebView mWebview;
    private ProgressBar mPbar = null;
    private AdView mAdView_test;
    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_test, container, false);

        mWebview =  (WebView)v.findViewById(R.id.webview_test);
        mPbar = (ProgressBar) v.findViewById(R.id.web_view_progress_test);
        mWebview.setVisibility(View.VISIBLE);
        mWebview.loadUrl("https://parivahan.gov.in/sarathiservice/stalllogin.do");
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new testWebViewClient());
        mAdView_test = new AdView(getActivity());
        mAdView_test.setAdUnitId(getResources().getString(R.string.quiz_banner));
        mAdView_test.setAdSize(AdSize.BANNER);
        LinearLayout layoutrc = (LinearLayout) v.findViewById(R.id.layout_admob_test);
        layoutrc.addView(mAdView_test);
        AdRequest adRequest_rc = new AdRequest.Builder().build();
        mAdView_test.loadAd(adRequest_rc);
        return v;
    }
    private class testWebViewClient extends WebViewClient
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
