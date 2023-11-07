package com.example.androidmusicapp.api;


import com.example.androidmusicapp.model.entity.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    //http://localhost:8081/auth/signin
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            //ip máy
            .baseUrl("http://192.168.1.5:8081/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @POST("auth/signin")
    Call<User> sendUser(@Body User user);
}


