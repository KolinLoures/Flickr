package com.example.kolin.flick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class LookPhotoFragment extends Fragment {

    private ViewPager viewPager;
    private List<GalleryItem> items;
    private int pos;

    public LookPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        items = new ArrayList<>();
        if (bundle != null){
            items = bundle.getParcelableArrayList("LIST");
            pos = bundle.getInt("pos");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_photo, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(), items);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pos);
        return view;
    }

    public static class PagerAdapter extends FragmentPagerAdapter {

        private List<GalleryItem> list;

        public PagerAdapter(FragmentManager fm, List<GalleryItem> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            GalleryItem galleryItem = list.get(position);
            return PageFragment.newInstance(galleryItem);
        }
    }
}
