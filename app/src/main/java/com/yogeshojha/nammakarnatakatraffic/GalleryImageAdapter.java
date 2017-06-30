package com.yogeshojha.nammakarnatakatraffic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
public class GalleryImageAdapter extends BaseAdapter
{
    private Context mContext;



    public GalleryImageAdapter(Context context)
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
            R.drawable.mandatory1,
            R.drawable.mandatory2,
            R.drawable.mandatory3,
            R.drawable.mandatory4,
            R.drawable.mandatory5,
            R.drawable.mandatory6,
            R.drawable.mandatory7,
            R.drawable.mandatory8,
            R.drawable.mandatory9,
            R.drawable.mandatory20,
            R.drawable.mandatory11,
            R.drawable.mandatory12,
            R.drawable.mandatory13,
            R.drawable.mandatory14,
            R.drawable.mandatory15,
            R.drawable.mandatory16,
            R.drawable.mandatory17,
            R.drawable.mandatory18,
            R.drawable.mandatory19,
            R.drawable.mandatory20,
            R.drawable.mandatory21,
            R.drawable.mandatory22,
            R.drawable.mandatory23,
            R.drawable.mandatory24,
            R.drawable.mandatory25,
            R.drawable.mandatory26,
            R.drawable.mandatory27,
            R.drawable.mandatory28,
            R.drawable.mandatory29,
            R.drawable.mandatory30,
            R.drawable.mandatory31,
};

}