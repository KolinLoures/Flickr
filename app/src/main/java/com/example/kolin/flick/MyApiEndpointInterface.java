package com.example.kolin.flick;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by n.kirilov on 23.05.2016.
 */
public interface MyApiEndpointInterface {

    @GET("?method=flickr.photos.getRecent")
    Call<Photo> getRecent(@Query("api_key") String apiKey,
                                      @Query("format") String format,
                                      @Query("nojsoncallback") Integer nojsoncallback,
                                      @Query("extras") String extras);

}
