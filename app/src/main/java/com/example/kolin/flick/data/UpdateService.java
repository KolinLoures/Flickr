package com.example.kolin.flick.data;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.kolin.flick.RetrofitSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by n.kirilov on 24.05.2016.
 */
public class UpdateService extends IntentService {

    private static final String URL = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "a09ef8d2480f136858052df0d219376b";

    public static final String ACTION = "com.example.kolin.flick.data.UpdateService";


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
        Intent in = new Intent(ACTION);
        RetrofitSingleton.getInstance();
        MyApiEndpointInterface myApiEndpointInterface = RetrofitSingleton.getMyApi();
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

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = UpdateService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn)
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 6000,
                    pendingIntent);
        else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }




}
