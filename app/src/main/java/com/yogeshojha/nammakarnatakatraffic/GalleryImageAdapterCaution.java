package com.yogeshojha.nammakarnatakatraffic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by y0g3sh on 8/1/17.
 */

public class GalleryImageAdapterCaution extends BaseAdapter{
    private Context mContext;



    public GalleryImageAdapterCaution(Context context)
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
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(120, 120));

        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }

    public Integer[] mImageIds = {
            R.drawable.cs1,
            R.drawable.cs2,
            R.drawable.cs3,
            R.drawable.cs4,
            R.drawable.cs5,
            R.drawable.cs6,
            R.drawable.cs7,
            R.drawable.cs8,
            R.drawable.cs9,
            R.drawable.cs10,
            R.drawable.cs11,
            R.drawable.cs12,
            R.drawable.cs13,
            R.drawable.cs14,
            R.drawable.cs15,
            R.drawable.cs16,
            R.drawable.cs17,
            R.drawable.cs18,
            R.drawable.cs19,
            R.drawable.cs20,
            R.drawable.cs21,
            R.drawable.cs22,
            R.drawable.cs23,
            R.drawable.cs24,
            R.drawable.cs25,
            R.drawable.cs26,
            R.drawable.cs27,
            R.drawable.cs28,
            R.drawable.cs29,
            R.drawable.cs30,
            R.drawable.cs31,
            R.drawable.cs32,
            R.drawable.cs33,
            R.drawable.cs34,
            R.drawable.cs35,
            R.drawable.cs37,
            R.drawable.cs38,
            R.drawable.cs39,
            R.drawable.cs40,
            R.drawable.cs41,
            R.drawable.cs42
    };
}
