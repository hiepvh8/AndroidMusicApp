package com.example.androidmusicapp.api;


import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.model.entity.User;

import java.util.ArrayList;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/signin")
    Call<User> signIn(@Body User user);
    @POST("auth/signup")
    Call<User> signUp(@Body User user);
    @GET("song")
    Call<ArrayList<Song>> getSongs();
}
