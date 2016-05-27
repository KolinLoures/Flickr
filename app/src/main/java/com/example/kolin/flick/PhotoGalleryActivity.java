package com.example.kolin.flick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGalleryActivity extends AppCompatActivity implements
        PhotoGalleryFragment.OnSelectedListener,
        TileFragment.OnClickTileItem {


    private static final String API_KEY = "a09ef8d2480f136858052df0d219376b";

    private DrawerLayout drawerLayout;


    private List<Photo_> list;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        list = new ArrayList<>();
        loadPhotos();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewP);




        FloatingActionButton floatingActionButton = (FloatingActionButton)
                findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Holla!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true;
        } else if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager(ViewPager viewPager){
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PhotoGalleryFragment(), "List");
        adapter.addFragment(TileFragment.newInstance(list),"Tile");
        adapter.addFragment(CardFragment.newInstace(list), "Card");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSelected(List<Photo_> list, int position) {
        Intent intent = new Intent(getApplicationContext(), LookPhotoActivity.class);
        intent.putParcelableArrayListExtra("Photos", (ArrayList<? extends Parcelable>) list);
        intent.putExtra("pos", position);
        startActivity(intent);
    }

    @Override
    public void onSelectedTileItem(Photo_ photo) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("photoObj", photo);
        startActivity(intent);
    }

    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    public void loadPhotos(){
        RetrofitSingleton.getInstance();
        MyApiEndpointInterface myApiEndpointInterface = RetrofitSingleton.getMyApi();
        Call<Photo> call = myApiEndpointInterface.getRecent(API_KEY, "json", 1, "url_s");
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                Photos photos = response.body().getPhotos();
                list.addAll(photos.getPhoto());
                setupViewPager(viewPager);
                TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
                tabs.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
