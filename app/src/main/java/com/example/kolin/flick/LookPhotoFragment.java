package com.example.kolin.flick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class LookPhotoFragment extends Fragment {

    private ViewPager viewPager;
    private List<GalleryItem> items;

    public LookPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_photo, container, false);

        viewPager = (ViewPager) container.findViewById(R.id.view_pager);

        PagerAdapter pagerAdapter = new PageAdapter(getFragmentManager(),items);
        viewPager.setAdapter(pagerAdapter);

        return view;
    }

    public static class PageAdapter extends FragmentStatePagerAdapter{

        private List<GalleryItem> list;

        public PageAdapter(FragmentManager fm, List<GalleryItem> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }


        @Override
        public Fragment getItem(int position) {
            GalleryItem galleryItem = list.get(position);
            return LookPhotoFragment.newInstance(galleryItem.getmUrl());
        }


    }

    public static LookPhotoFragment newInstance(String url){
        return null;
    }


}
