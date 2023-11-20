package com.example.androidmusicapp.api;


import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.model.entity.User;
import com.example.androidmusicapp.model.entity.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    //http://localhost:8081/auth/signin
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30 , TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor);
    ApiService apiService = new Retrofit.Builder()
            //ip máy
            .baseUrl("http://192.168.191.220:8081/")
            //192.168.191.220
            //192.168.1.3
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("auth/signin")
    Call<User> signIn(@Body User user);
    @POST("auth/signup")
    Call<ResponseBody> signUp(@Body User user);
    @GET("song")
    Call<ArrayList<Song>> getSongs();
}
