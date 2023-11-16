package com.example.androidmusicapp.api;



import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.model.entity.User;


import com.example.androidmusicapp.model.entity.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            //ip máy
            .baseUrl("http://192.168.39.220:8081/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    //     .baseUrl("http://192.168.1.5:8081/")
    //192.168.1.5
    //192.168.39.220
    //192.168.1.13

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();


    @POST("auth/signin")
    Call<User> signIn(@Body User user);
    @POST("auth/signup")
    Call<ResponseBody> signUp(@Body User user);
    //test

    @GET("song")
    Call<ArrayList<Song>> getSongs();
}

