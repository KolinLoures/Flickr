package com.example.kolin.flick;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by kolin on 20.05.2016.
 */
public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";
    private static final String API_KEY = "a09ef8d2480f136858052df0d219376b";


    private SwipeRefreshLayout swipeContainer;

    private OnSelectedListener listener;

    private List<Photo_> mItems = new ArrayList<>();
    public RecyclerView mPhotoRecyclerView;
    private PhotoAdapter adapter;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    public interface OnSelectedListener {
        void onSelected(List<Photo_> list, int position);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i(TAG, "Background thread started");
        adapter = new PhotoAdapter();
        UpdateService.setServiceAlarm(getActivity(), true);
        load();
    }




    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(UpdateService.ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(testReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.clear();
            List<Photo_> list = intent.getParcelableArrayListExtra("list");
            adapter.add(list);
        }
    };

    public void load(){
        Retrofit retrofit = RetrofitSingleton.getInstance();
        MyApiEndpointInterface myApiEndpointInterface = RetrofitSingleton.getMyApi();
        Call<Photo> call = myApiEndpointInterface.getRecent(API_KEY, "json", 1, "url_s");
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                adapter.clear();
                Photos photos = response.body().getPhotos();
                List<Photo_> list = photos.getPhoto();
                adapter.add(list);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);

        try{
            listener = (OnSelectedListener) a;
        } catch (ClassCastException e){
            throw new ClassCastException(a.toString() + "must implement listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);


        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPhotoRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(testReceiver);
        Log.i(TAG, "Background thread destroyed");
    }


    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mItemImageView = (ImageView) itemView.findViewById(R.id.fragment_photo_gallery_image_view);
            mItemImageView.setOnClickListener(this);
        }

        public void bindDrawable(Drawable drawable){
            mItemImageView.setImageDrawable(drawable);
        }


        @Override
        public void onClick(View v) {
            listener.onSelected(mItems, getLayoutPosition());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{

        private List<Photo_> mGalleryItems;

        public PhotoAdapter() {
            mGalleryItems = new ArrayList<>();
        }


        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            Photo_ galleryItem = mGalleryItems.get(position);
            Drawable placeHolder = getResources().getDrawable(R.drawable.icc_plus);
            holder.bindDrawable(placeHolder);

            Picasso.with(getActivity()).load(galleryItem.getUrlS())
                    .placeholder(R.drawable.icc_plus)
                    .into(holder.mItemImageView);
        }


        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }



        public void add(List<Photo_> galleryItems) {
            mGalleryItems.addAll(galleryItems);
            mItems.addAll(mGalleryItems);
            notifyDataSetChanged();
        }

        public void clear(){
            mGalleryItems.clear();
            notifyDataSetChanged();
        }
    }
}
