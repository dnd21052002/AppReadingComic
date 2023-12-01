package com.example.appreadingcomic.retrofit;

import android.database.Observable;

import com.example.appreadingcomic.object.Comic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServer {
    @GET("comic")
    Call<List<Comic>> getComic();
    @POST("comic")
    Call<Void> addComic(@Body Comic comic);
}
