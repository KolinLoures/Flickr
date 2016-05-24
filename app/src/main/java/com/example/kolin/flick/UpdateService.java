package com.example.kolin.flick;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by n.kirilov on 24.05.2016.
 */
public class UpdateService extends IntentService {

    private static final String URL = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "a09ef8d2480f136858052df0d219376b";

    public static final String ACTION = "com.example.kolin.flick.UpdateService";

    public UpdateService() {
        super("UpdateService");
    }

    public static Intent newIntent(Context context){
        return new Intent(context, UpdateService.class);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Response<Photo> response;
        List<Photo_> list = new ArrayList<>();

        if (!isNetworkAvailableAndConnected()){
            return;
        }
        String val = intent.getStringExtra("receiver");
        Intent in = new Intent(ACTION);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiEndpointInterface myApiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);

        Call<Photo> call = myApiEndpointInterface.getRecent(API_KEY, "json", 1, "url_s");
        try {
            response = call.execute();
            list = response.body().getPhotos().getPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }
        in.putExtra("result_code", Activity.RESULT_OK);
        in.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
        LocalBroadcastManager.getInstance(this).sendBroadcast(in);
        Log.i("UpdateService", "Received an intent: " + intent);
    }

    private boolean isNetworkAvailableAndConnected(){
        ConnectivityManager cm = (ConnectivityManager)  getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }


}
