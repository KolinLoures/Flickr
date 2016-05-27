package com.example.kolin.flick;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by n.kirilov on 25.05.2016.
 */
public class RetrofitSingleton {

    private static final String URL = "https://api.flickr.com/services/rest/";

    private static OkHttpClient client;
    private static Retrofit ourInstance = null;
    private static MyApiEndpointInterface myApi;


    public static Retrofit getInstance() {
        if (ourInstance == null) {
            synchronized (RetrofitSingleton.class) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                client = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build();
                ourInstance = new Retrofit.Builder()
                        .baseUrl(URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return ourInstance;
    }

    public static void initApi(){
        if (myApi == null){
            synchronized (RetrofitSingleton.class) {
                myApi = getInstance().create(MyApiEndpointInterface.class);
            }
        }
    }

    public static MyApiEndpointInterface getMyApi(){
        initApi();
        return myApi;
    }



    private RetrofitSingleton() {
    }
}
