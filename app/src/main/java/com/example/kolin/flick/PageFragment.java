package com.example.kolin.flick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class PageFragment extends Fragment {


    private String url;


    public static PageFragment newInstance(GalleryItem galleryItem) {

        Bundle args = new Bundle();
        args.putString("urlCap", galleryItem.getmUrl());
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("urlCap");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.page_image_view);

        Picasso.with(getActivity()).load(url)
                .placeholder(R.drawable.icc_plus)
                .into(imageView);

        return view;
    }


}
