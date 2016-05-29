package com.example.kolin.flick;

import com.example.kolin.flick.data.Photo_;

import java.util.List;

/**
 * Created by kolin on 29.05.2016.
 */
public interface PhotoContract {

    interface View{

        void showPhotos(List<Photo_>list);

        void showPhotoDetail(Photo_ p);

        void showLookPhotos(List<Photo_> list, int pos);
    }

    interface UserActionListener{

        void loadPhotos();

        void openLookPhoto(List<Photo_> list, int pos);

        void openLookPhotoDetail(Photo_ p);

    }
}
