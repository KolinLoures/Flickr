package com.example.kolin.flick;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kolin on 20.05.2016.
 */
public class GalleryItem implements Parcelable {
    private String mCaption;
    private String id;
    private String urlS;


    protected GalleryItem(Parcel in) {
        mCaption = in.readString();
        id = in.readString();
        urlS = in.readString();
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel(Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };

    public String getmCaption() {
        return mCaption;
    }

    public void setmCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmUrl() {
        return urlS;
    }

    public void setmUrl(String mUrl) {
        this.urlS = mUrl;
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                "mCaption='" + mCaption + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCaption);
        dest.writeString(id);
        dest.writeString(urlS);

    }
}
