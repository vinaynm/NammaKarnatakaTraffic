package com.yogeshojha.nammakarnatakatraffic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

public class cautionary extends Fragment {

    ImageView selectedImage;
    public cautionary() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mandatory, container, false);
        Gallery gallery = (Gallery) v.findViewById(R.id.gallery);
        selectedImage=(ImageView) v.findViewById(R.id.imageView);
        gallery.setSpacing(1);
        final GalleryImageAdapterCaution GalleryImageAdapterCaution= new GalleryImageAdapterCaution(getActivity());
        gallery.setAdapter(GalleryImageAdapterCaution);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedImage.setImageResource(GalleryImageAdapterCaution.mImageIds[position]);
            }
        });
        return v;
    }

}
