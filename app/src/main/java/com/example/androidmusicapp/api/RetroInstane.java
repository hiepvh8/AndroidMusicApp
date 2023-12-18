package com.example.androidmusicapp.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstane {
    public static String Base_url = "http://192.168.1.22:8081/";
    private static Retrofit retrofit;
    public static Retrofit getRetroClient(){
        if(retrofit == null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30 , TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(loggingInterceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
