package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by yogeshojha on 26/06/17.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Adapter extends ArrayAdapter<String> {
    private InterstitialAd interstitial;
    private AdRequest adRequest;
    Context context;
    ViewHolder viewHolder;
    ArrayList<String> al_newslist=new ArrayList<>();
    private String substring;

    public Adapter(Context context,   ArrayList<String> al_newslist) {
        super(context, R.layout.adapter_layout, al_newslist);
        this.al_newslist=al_newslist;
        this.context=context;



    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE",al_newslist.size()+"");
        return al_newslist.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_newslist.size() > 0) {
            return al_newslist.size();
        } else {
            return 1;
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        interstitial = new InterstitialAd(getContext());
        interstitial.setAdUnitId("ca-app-pub-9002284518905519/1050536181");
        adRequest = new AdRequest.Builder().build();
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout, parent, false);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_text);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String str = al_newslist.get(position);
        final String url_open = returnurl(str);
        str = removeUrl(str);
        viewHolder.tv_name.setText(str);
        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //load ads on click
                interstitial.loadAd(adRequest);
                interstitial.show();
                if(url_open.length()>0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Please Wait News is Loading...");
                    WebView wv = new WebView(getContext());
                    wv.getSettings().setJavaScriptEnabled(true);
                    //wv.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
                    wv.getSettings().setDomStorageEnabled(true);
                    wv.loadUrl(url_open);
                    wv.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);

                            return true;
                        }
                    });
                    alert.setView(wv);
                    alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }
        });
        return convertView;

    }

    private static class ViewHolder {
        TextView tv_name;



    }
    private String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }
    private String returnurl(String text)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        while (m.find()) {
            int matchStart = m.start(1);
            int matchEnd = m.end();
            substring = text.substring(matchStart,matchEnd);
        }
        return substring;
    }

}
