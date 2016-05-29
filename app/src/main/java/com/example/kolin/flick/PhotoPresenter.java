package com.example.kolin.flick;

import com.example.kolin.flick.data.MyApiEndpointInterface;
import com.example.kolin.flick.data.Photo;
import com.example.kolin.flick.data.Photo_;
import com.example.kolin.flick.data.Photos;
import com.example.kolin.flick.data.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kolin on 29.05.2016.
 */
public class PhotoPresenter implements PhotoContract.UserActionListener {

    private static final String API_KEY = "a09ef8d2480f136858052df0d219376b";

    private PhotoContract.View photoView;

    public PhotoPresenter(PhotoContract.View  view) {
        photoView  = view;
    }

    @Override
    public void loadPhotos() {
        RetrofitSingleton.getInstance();
        MyApiEndpointInterface myApiEndpointInterface = RetrofitSingleton.getMyApi();
        Call<Photo> call = myApiEndpointInterface.getRecent(API_KEY, "json", 1, "url_s");
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                Photos photos = response.body().getPhotos();
                List<Photo_> list = photos.getPhoto();
                photoView.showPhotos(list);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }

        });
    }

    @Override
    public void openLookPhoto(List<Photo_> p, int pos) {
        photoView.showLookPhotos(p, pos);
    }

    @Override
    public void openLookPhotoDetail(Photo_ p) {
        photoView.showPhotoDetail(p);
    }


}
