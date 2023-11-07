package com.example.androidmusicapp.api;

import com.example.androidmusicapp.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    //http://localhost:8081/swagger-ui/index.html#/
//    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//    ApiService apiService = new Retrofit.Builder().baseUrl("http://192.168.1.16:8081/")
//            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);
//
//    @GET("song")
//    Call<Song> displaySong();

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
//>>>>>>> 7e7a4eb3c0d0d93a5610a7677e0d4e1c595a8db4
}
