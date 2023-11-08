package com.example.androidmusicapp.api;


import com.example.androidmusicapp.model.entity.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    //http://localhost:8081/auth/signin
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            //ip máy
            .baseUrl("http://192.168.1.5:8081/")
            //192.168.1.5
            //192.168.39.220
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @POST("auth/signin")
    Call<User> signIn(@Body User user);
    @POST("auth/signup")
    Call<ResponseBody> signUp(@Body User user);
}