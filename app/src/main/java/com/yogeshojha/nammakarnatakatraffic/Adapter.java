package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by yogeshojha on 26/06/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Adapter extends ArrayAdapter<String> {

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
                Uri uri = Uri.parse(url_open); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
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
