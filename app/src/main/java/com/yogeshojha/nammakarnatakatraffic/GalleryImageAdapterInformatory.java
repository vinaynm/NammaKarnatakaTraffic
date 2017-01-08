package com.yogeshojha.nammakarnatakatraffic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by y0g3sh on 8/1/17.
 */

public class GalleryImageAdapterInformatory extends BaseAdapter{
    private Context mContext;


    public GalleryImageAdapterInformatory(Context context)
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
//        int length,width;
//        Float density = mContext.getResources().getDisplayMetrics().density;
//        System.out.println(density);
//        if(density >=0 && density <= 0.75)
//        {
//            length = width = 75;
//        }
//        else if(density > 0.75 && density <= 1.0)
//        {
//            length = width = 100;
//        }
//        else if(density > 1.0 && density <= 1.5)
//        {
//            length = width = 130;
//        }
//        else if(density > 1.5 && density <= 2.0)
//        {
//            length = width = 140;
//        }
//        else if(density > 2.0 && density <= 3.0)
//        {
//            length = width = 200;
//        }
//        else if(density > 3.0 && density <= 4.0)
//        {
//            length = width = 200;
//        }
//        else
//        {
//            length = width = 100;
//
//        }
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);
        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }

    public Integer[] mImageIds = {
            R.drawable.is1,
            R.drawable.is2,
            R.drawable.is3,
            R.drawable.is4,
            R.drawable.is5,
            R.drawable.is6,
            R.drawable.is7,
            R.drawable.is8,
            R.drawable.is9,
            R.drawable.is10,
            R.drawable.is11,
            R.drawable.is12,
            R.drawable.is13,
            R.drawable.is14,
            R.drawable.is15,
            R.drawable.is16,
            R.drawable.is17,
            R.drawable.is18,
            R.drawable.is19,
            R.drawable.is20,
            R.drawable.is21,
            R.drawable.is22
    };
}
