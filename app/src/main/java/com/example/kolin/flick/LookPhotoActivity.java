package com.example.kolin.flick;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LookPhotoActivity extends AppCompatActivity {


    private List<Photo_> items;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_photo);

        items = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            items = bundle.getParcelableArrayList("Photos");
            position = bundle.getInt("pos");
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerToLookPhoto);
        AdapterPhoto adapter = new AdapterPhoto(getSupportFragmentManager(), items);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    static class AdapterPhoto extends FragmentPagerAdapter{

        private List<Photo_> list;

        public AdapterPhoto(FragmentManager fm, List<Photo_> list) {
            super(fm);
            this.list = list;
        }


        @Override
        public Fragment getItem(int position) {
            Photo_ item = list.get(position);
            return PageFragment.newInstance(item);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
