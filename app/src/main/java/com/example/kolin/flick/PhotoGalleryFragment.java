package com.example.kolin.flick;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolin on 20.05.2016.
 */
public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private static final String URL = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "a09ef8d2480f136858052df0d219376b";

    private SwipeRefreshLayout swipeContainer;

    private OkHttpClient client;

    private List<Photo_> mItems = new ArrayList<>();
    public RecyclerView mPhotoRecyclerView;
//    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;
    private PhotoAdapter adapter;



    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i(TAG, "Background thread started");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        adapter = new PhotoAdapter();
        load();
    }

    public void load(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiEndpointInterface myApiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);

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



//        new FetchItemsTask().execute();

//        Handler responseHandler = new Handler();
//        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
//        mThumbnailDownloader.setThumbnailDownloadListener(new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
//            @Override
//            public void onThumbnailDownloader(PhotoHolder target, Bitmap thumbnail) {
//                Drawable drawable = new BitmapDrawable(getResources(), thumbnail);
//                target.bindDrawable(drawable);
//            }
//        });
//        mThumbnailDownloader.start();
//        mThumbnailDownloader.getLooper();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    private void setupAdapter() {
            mPhotoRecyclerView.setAdapter(adapter);
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
            LookPhotoFragment lookPhotoFragment = new LookPhotoFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("LIST", (ArrayList<? extends Parcelable>) mItems);
            int position = getLayoutPosition();
            bundle.putInt("pos", position);
            lookPhotoFragment.setArguments(bundle);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, lookPhotoFragment);
            ft.commit();
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
//            mThumbnailDownloader.queueThumbnail(holder, galleryItem.getUrl_s());

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
            //notifyItemRangeInserted(mGalleryItems.size()+galleryItems.size(), galleryItems.size());

        }

        public void clear(){
            mGalleryItems.clear();
            notifyDataSetChanged();
        }



    }

//    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>>{
//
//        @Override
//        protected List<GalleryItem> doInBackground(Void... params) {
//            return new FlickrFetchr().fetchItems();
//        }
//
//        @Override
//        protected void onPostExecute(List<GalleryItem> galleryItems) {
//            mItems = galleryItems;
//            adapter.add(galleryItems);
//
//            setupAdapter();
//        }
//    }

}
